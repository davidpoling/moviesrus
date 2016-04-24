package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

public class ObjectTableModel extends AbstractTableModel {
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private Vector<Rental> titles;
	private Vector<Member> members;
	private Vector<String> columnNames;
	private int columnCount;
	
	public ObjectTableModel(ResultSet results) {
		super();
		columnNames = new Vector<String>();
		titles = new Vector<Rental>();
		members = new Vector<Member>();
		try {
			columnCount = results.getMetaData().getColumnCount();
			ResultSetMetaData metaData = results.getMetaData();
			logger.info("Object Column Count = " + metaData.getColumnCount());
			for(int i = 1; i <= metaData.getColumnCount(); ++i){
				columnNames.addElement(metaData.getColumnLabel(i));
			}
			createResults(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Rental> getTitles() {
		return titles;
	}

	public Vector<Member> getMembers() {
		return members;
	}

	private void createResults(ResultSet results) throws SQLException{
		int i;
		int currentId;
		Rental currentRental;
		Media currentMedia;
		Member currentMember;
		
		do {
			i = 0;
			currentMember = new Member();
			if(columnNames.size() != 13){
				currentRental = new Rental();
				currentMedia = new Media();
				currentId = results.getInt(columnNames.get(i++));
				currentMedia.setId(currentId);
				currentMedia.setTitle(results.getString(columnNames.get(i++)));
				currentMedia.getPlatform().setConsole(results.getString(columnNames.get(i++)));
				currentMedia.getPlatform().setVersion(results.getString(columnNames.get(i++)));
				currentMedia.setInStock(Integer.parseInt(results.getString(columnNames.get(i++))));
				currentMedia.setGenre(results.getString(columnNames.get(i++)));
				if(columnNames.size() == 6){
					currentRental.setMedia(currentMedia);
					currentRental.setMember(currentMember);
					titles.addElement(currentRental);
				}else if(columnNames.size() == 9){
					addCastDetails(results, currentRental, currentMedia, currentMember, currentId, i);
				}else if(columnNames.size() == 10){
					addRentalDetails(results, currentRental, currentMedia, currentMember, i);
				}else if(columnNames.contains("Street")){
					addShippingInfo(results, currentRental, currentMedia, currentMember, i);
				}else if(columnNames.contains("Honor")){
					addAwardDetails(results, currentRental, currentMedia, currentMember, currentId, i);
				}else if(columnNames.contains("Sequel Title")){
					currentRental.setMedia(currentMedia);
					currentRental.setMember(currentMember);
					String title = results.getString(columnNames.get(i++));
					int stock = results.getInt(columnNames.get(i++));
					Media sequel = new Media(title, stock);
					currentRental.getMedia().setSequel(sequel);
					titles.addElement(currentRental);
				}
			}else{
				addMember(results, currentMember, i);
				logger.info("Adding Member: " + currentMember.toString());
			}
				
//			logger.info(currentRental.toString());
		}while(results.next());			
	}
	
	private void addRentalDetails(ResultSet results, Rental rental, Media media, Member member, int i) throws SQLException{
		rental.setCheckedOut(results.getDate(columnNames.get(i++)));
		rental.setShipped(results.getDate(columnNames.get(i++)));
		rental.setReturned(results.getDate(columnNames.get(i++)));
		rental.setOverdue(results.getBoolean(columnNames.get(i++)));
		rental.setMedia(media);
		rental.setMember(member);
		titles.addElement(rental);
	}
	
	private void addCastDetails(ResultSet results, Rental rental, Media media, Member member, int id, int i) throws SQLException{
		boolean exists = false;
		for(Rental title: titles){
			if(title.getMedia().getId() == id){
				exists = true;
				rental = title;
				media = rental.getMedia();
			}
		}
		media.setYear(results.getInt(columnNames.get(i++)));
		String firstName = results.getString(columnNames.get(i++));
		String surname = results.getString(columnNames.get(i++));
		Actor actor = new Actor(firstName, surname);
		media.addActor(actor);
		if(!exists){
			rental.setMedia(media);
			rental.setMember(member);
			titles.addElement(rental);
		}
	}
	
	private void addShippingInfo(ResultSet results, Rental rental, Media media, Member member, int i) throws SQLException{
		member.setId(results.getInt(columnNames.get(i++)));
		member.setFirstName(results.getString(columnNames.get(i++)));
		member.setSurName(results.getString(columnNames.get(i++)));
		member.getAddress().setStreet(results.getString(columnNames.get(i++)));
		member.getAddress().setApartment(results.getString(columnNames.get(i++)));
		member.getAddress().setCity(results.getString(columnNames.get(i++)));
		member.getAddress().setState(results.getString(columnNames.get(i++)));
		member.getAddress().setPostal(Integer.parseInt(results.getString(columnNames.get(i++))));
		rental.setMedia(media);
		rental.setMember(member);
		titles.addElement(rental);
	}
	
	private void addAwardDetails(ResultSet results, Rental rental, Media media, Member member, int id, int i) throws SQLException{
		boolean exists = false;
		for(Rental title: titles){
			if(title.getMedia().getId() == id){
				exists = true;
				rental = title;
				media = rental.getMedia();
			}
		}
		String honor = results.getString(columnNames.get(i++));
		String category = results.getString(columnNames.get(i++));
		Award currentAward = new Award(honor, category);
		media.getAwards().add(currentAward);
		if(!exists){
			rental.setMedia(media);
			rental.setMember(member);
			titles.addElement(rental);
		}
	}
	
	private void addMember(ResultSet results, Member member, int i) throws SQLException{
		member.setId(results.getInt(columnNames.get(i++)));
		member.setFirstName(results.getString(columnNames.get(i++)));
		member.setSurName(results.getString(columnNames.get(i++)));
		member.getAddress().setStreet(results.getString(columnNames.get(i++)));
		member.getAddress().setApartment(results.getString(columnNames.get(i++)));
		member.getAddress().setCity(results.getString(columnNames.get(i++)));
		member.getAddress().setState(results.getString(columnNames.get(i++)));
		member.getAddress().setPostal(results.getInt(columnNames.get(i++)));
		member.getAddress().setType(results.getString(columnNames.get(i++)));
		member.getMembership().setContent(results.getString(columnNames.get(i++)));
		member.getMembership().setQuantity(results.getInt(columnNames.get(i++)));
		member.getMembership().setExpiration(results.getDate(columnNames.get(i++)));
		member.setEmail(results.getString(columnNames.get(i++)));
		members.addElement(member);
	}
	
	@Override
	public int getRowCount() {
		if(titles.size() != 0){
			return titles.size();
		}else{
			return members.size();
		}
	}

	@Override
	public int getColumnCount() {
		if(columnNames.contains("Honor")){
			return columnCount - 1;
		}else{
			return columnCount;
		}
	}

	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		if(columnCount == 6){
			return getRental(rowIndex, columnIndex);
		}else if(columnNames.contains("Honor")){
			return getAwards(rowIndex, columnIndex);
		}else if(columnNames.contains("Sequel Title")){
			return getSequels(rowIndex, columnIndex);
		}else if(columnCount == 9){
			return getCast(rowIndex, columnIndex);
		}else if(columnCount == 10){
			return getRented(rowIndex, columnIndex);
		}else if(columnCount == 13){
			return getMember(rowIndex, columnIndex);
		}
		return getRental(rowIndex, columnIndex);
	}

	private Object getTitle(int rowIndex, int columnIndex){
		Rental rental = titles.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
        case 0:
            value = rental.getMedia().getId();
            break;
        case 1:
            value = rental.getMedia().getTitle();
            break;
        case 2:
            value = rental.getMedia().getPlatform().getConsole();
            break;
        case 3:
        	value = rental.getMedia().getPlatform().getVersion();
        	break;
        case 4:
        	value = rental.getMedia().getInStock();
        	break;
        case 5:
        	value = rental.getMedia().getGenre();
        	break;
        }
        return value;
	}
        
	private Object getRental(int rowIndex, int columnIndex){
		Rental rental = titles.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        	value = getTitle(rowIndex, columnIndex);
        	break;
        case 6:
        	value = rental.getMember().getId();
        	break;
        case 7:
        	value = rental.getMember().getFirstName();
        	break;
        case 8:
        	value = rental.getMember().getSurName();
        	break;
        case 9:
        	value = rental.getMember().getAddress().getStreet();
        	break;
        case 10:
        	value = rental.getMember().getAddress().getApartment();
        	break;
        case 11:
        	value = rental.getMember().getAddress().getCity();
        	break;
        case 12:
        	value = rental.getMember().getAddress().getState();
        	break;
        case 13:
        	value = rental.getMember().getAddress().getPostal();
        	break;
        }
        return value;
	}
	
	private Object getMember(int rowIndex, int columnIndex){
		Member member = members.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = member.getId();
			break;
		case 1:
			value = member.getFirstName();
			break;
		case 2:
			value = member.getSurName();
			break;
		case 3:
			value = member.getAddress().getStreet();
			break;
		case 4:
			value = member.getAddress().getApartment();
			break;
		case 5:
			value = member.getAddress().getCity();
			break;
		case 6:
			value = member.getAddress().getState();
			break;
		case 7:
			value = member.getAddress().getPostal();
			break;
		case 8:
			value = member.getAddress().getType();
			break;
		case 9:
			value = member.getMembership().getContent();
			break;
		case 10:
			value = member.getMembership().getQuantity();
			break;
		case 11:
			value = member.getMembership().getExpirationDate();
			break;
		case 12:
			value = member.getEmail();
			break;
		}
		return value;
	}
	
	private Object getAwards(int rowIndex, int columnIndex){
		Rental rental = titles.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        	value = getTitle(rowIndex, columnIndex);
        	break;
        case 6:
        	value = rental.getMedia().getAwards();
        	break;
        }
        return value;
	}
	
	private Object getSequels(int rowIndex, int columnIndex){
		Rental rental = titles.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			value = getTitle(rowIndex, columnIndex);
			break;
		case 6:
			value = rental.getMedia().getSequel().getTitle();
			break;
		case 7:
			value = rental.getMedia().getSequel().getInStock();
			break;
		}
		return value;
	}
	
	private Object getRented(int rowIndex, int columnIndex){
		Rental rental = titles.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        	value = getTitle(rowIndex, columnIndex);
        	break;
        case 6:
        	value = rental.getCheckedOut();
        	break;
        case 7:
        	value = rental.getShipped();
        	break;
        case 8:
        	value = rental.getReturned();
        	break;
        case 9:
        	value = rental.isOverdue();
        	break;
        }
        return value;
	}
	
	private Object getCast(int rowIndex, int columnIndex){
		Rental rental = titles.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			value = getTitle(rowIndex, columnIndex);
			break;
		case 6:
			value = rental.getMedia().getYear();
			break;
		case 7:
			if(rental.getMedia().getDirector() != null){
				value = rental.getMedia().getDirector().toString();
			}
			String actors = rental.getMedia().getActors().toString();
			value = actors;
			break;
		case 8:
			value = "";
			break;
		}
		return value;
	}
}
