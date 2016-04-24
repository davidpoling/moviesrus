package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import queryUtils.DBInserts;
import queryUtils.DBQueries;
import queryUtils.DBUpdates;
import queryUtils.SQLConnection;

public class Member extends Human{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private String email;
	private String password;
	private Date passwordDate;
	private Membership membership;
	private Address address;
	private int currentRentals;
	private boolean passwordUpdated;
	
	public Member(){
		super();
		membership = new Membership();
		address = new Address();
	}

	public Member(int id, String email, String password) {
		super();
		setId(id);
		this.email = email;
		setPassword(password);
		membership = new Membership();
		address = new Address();
	}
	
	public Member(int id, String email, String password, Date passwordDate, Address address) {
		super();
		setId(id);
		this.email = email;
		setPassword(password);
		this.passwordDate = passwordDate;
		this.address = address;
	}

	public int getId() {
		return super.getId();
	}
	public void setId(int id) {
		super.setId(id);
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPasswordDate() {
		return passwordDate;
	}
	public void setPasswordDate(Date passwordDate) {
		this.passwordDate = passwordDate;
	}

	public void setPasswordUpdated(boolean passwordUpdated) {
		this.passwordUpdated = passwordUpdated;
	}

	public Membership getMembership() {
		return membership;
	}
	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	
	public void setFirstName(String name){
		super.setFirstName(name);
	}
	public void setSurName(String name){
		super.setSurName(name);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public int getCurrentRentals() {
		return currentRentals;
	}
	public void setCurrentRentals(int currentRentals) {
		this.currentRentals = currentRentals;
	}
	
	public boolean canRent(){
		return currentRentals < membership.getQuantity();
	}

	public void rentedTitle(){
		currentRentals++;
	}
	
	public void createMember(SQLConnection sqlConn){
		int resultCount;
		int humanId;
		int addressId;
		int membershipId;
		ResultSet resultSet;
		Connection conn = sqlConn.getConnection();
		Statement statement;
		
		try {
			statement = conn.createStatement();
			resultCount = statement.executeUpdate(DBInserts.addHuman(this));

		    resultSet = statement.executeQuery(DBInserts.getLastHumanId());
		    if (resultSet.next()) {
		    	humanId = resultSet.getInt(1);
		    	resultCount = statement.executeUpdate(DBInserts.addMember(humanId, this));
		    	resultCount = statement.executeUpdate(DBInserts.addAddress(this));
			    resultSet = statement.executeQuery(DBInserts.getLastAddressId());
			    if (resultSet.next()) {
			    	addressId = resultSet.getInt(1);
			    	resultCount = statement.executeUpdate(DBInserts.addMemberAddress(humanId, addressId, this));
				    resultSet = statement.executeQuery(DBInserts.getMemberShip(this));
				    if (resultSet.next()) {
				    	membershipId = resultSet.getInt(1);
				    	resultCount = statement.executeUpdate(DBInserts.addMemberMembership(humanId, membershipId));
				    }else{		    	
				    	JOptionPane.showMessageDialog(null,"Failed to add new member");
				    }
			    }else{	
			    	JOptionPane.showMessageDialog(null,"Failed to add new member");
			    }
		    }else{
		    	JOptionPane.showMessageDialog(null,"Failed to add new member");
		    }
		    
			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Failed to add new member");
			e.printStackTrace();
		}
	}
	
	public void updateMember(SQLConnection sqlConn){
		int resultCount;
		int membershipId;
		ResultSet resultSet;
		Connection conn = sqlConn.getConnection();
		Statement statement;
		
		try {
			statement = conn.createStatement();
			resultCount = statement.executeUpdate(DBUpdates.updateHuman(getFirstName(), getSurName(), getId()));
			resultCount = statement.executeUpdate(DBUpdates.updateMemberEmail(email, getId()));
			if(passwordUpdated){
				logger.info("Updating Password");
				resultCount = statement.executeUpdate(DBUpdates.updateMemberPassword(password, getId()));
			}
			resultCount = statement.executeUpdate(DBUpdates.updateAddress(address));
			resultCount = statement.executeUpdate(DBUpdates.updateMemberAddressType(address.getType(), getId(), address.getId()));
			resultSet = statement.executeQuery(DBQueries.fetchMembershipTypeId(membership.getContent(), membership.getQuantity()));
		    if (resultSet.next()) {
		    	membershipId = resultSet.getInt(1);
		    }else{
		    	membershipId = membership.getId();
		    }
			resultCount = statement.executeUpdate(DBUpdates.updateMemberMemberShip(getId(), membershipId));
			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Failed to add new member");
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return 	"member id: " + getId() + "\n" + 
				"email: " + email + "\n" +
				"password: " + password + "\n" + 
				"currently rented: " + currentRentals + "\n" + 
				super.toString() +
				address.toString() +
				membership.toString();
	}
}
