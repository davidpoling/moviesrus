package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.ObjectTableModel;
import ui.MainFrame;

public class AppUtils {
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	
	public static String hashPassword(byte[] password){
		StringBuilder stringBuilder;
	    String hashedPassword = null;
	    try {
	        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	        messageDigest.update(password);
	        byte[] bytes = messageDigest.digest();
	        stringBuilder = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++)
	        {
	            stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        hashedPassword = stringBuilder.toString();
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    System.out.println(hashedPassword);
		return hashedPassword;
	}
	
	public static boolean createSingleTableResults(MainFrame mainFrame, String query){
		boolean success;
		ResultSet resultSet;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		logger.info("Create table with: " + query);
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			
			if(!resultSet.next()){
				JOptionPane.showMessageDialog(null,"No records found!");
				mainFrame.getTablePanel().setResultTable(new JTable());
				success = false;
			}else{
				ObjectTableModel objectTableModel = new ObjectTableModel(resultSet);
				mainFrame.getTablePanel().setResultTable(objectTableModel);
				success = true;
			}
			statement.close();
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean updateTableResults(MainFrame mainFrame, String query){
		int resultSet;
		boolean success;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		logger.info("Update table with: " + query);
		try {
			statement = conn.createStatement();
			resultSet = statement.executeUpdate(query);
			success = true;
			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(mainFrame, "Error processing request.", "Error", JOptionPane.ERROR_MESSAGE);
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean currentlyRented(MainFrame mainFrame, String query){
		boolean rented;
		ResultSet resultSet;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		
		logger.info("Currently rented: " + query);
		rented = true;
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				rented = true;
			}else{
				rented = false;
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			rented = true;
		}
		return rented;
	}
	
	public static boolean isAvailable(MainFrame mainFrame, String query){
		boolean available;
		int inStock;
		ResultSet resultSet;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		
		logger.info("Currently rented: " + query);
		available = false;
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				inStock = resultSet.getInt(1);
				if(inStock > 0){
					available = true;
				}
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			available = false;
		}
		return available;
	}
	
	public static Vector<Integer> getIdForDelete(MainFrame mainFrame, String query){
		Vector<Integer> ids;
		ResultSet resultSet;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		
		logger.info("Fetch id for delete with: " + query);
		ids = new Vector<Integer>();
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				ids.add(resultSet.getInt(1));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public static Date getToday(MainFrame mainFrame, String query){
		Date now;
		ResultSet resultSet;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		logger.info("Get today with: " + query);
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				now = resultSet.getDate(1);
			}else{
				now = new Date(year, month, day);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			now = new Date(year, month, day);
		}
		return now;		
	}
}
