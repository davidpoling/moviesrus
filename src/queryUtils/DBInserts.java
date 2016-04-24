package queryUtils;

import java.sql.Date;
import java.util.logging.Logger;

import model.Genre;
import model.Member;
import model.Platform;
import enumsDB.MediaType;

public class DBInserts {
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	
	public static String addHuman(Member member){
		
		String uiQuery = "INSERT INTO human (first_name, surname)"
				+ " VALUES ('" + member.getFirstName() + "', '" + member.getSurName() + "');";
		logger.info("add human: " + uiQuery);
		return uiQuery;
	}
	
	public static String getLastHumanId(){
		return "SELECT LAST_INSERT_ID();";
	}
	
	public static String addMember(int humanId, Member member){
		String uiQuery = " INSERT INTO member (human_id, email, password, expiration)"
					+ " VALUES ("+ humanId + ", '" + member.getEmail() + "', '" + member.getPassword()
					+ "', date_add(CURDATE(),INTERVAL 90 DAY));";
		return uiQuery;
	}
	
	public static String addAddress(Member member){
		String uiQuery = " INSERT INTO address (street, apartment, city, postal, state_name)"
						+ " VALUES ('" + member.getAddress().getStreet() + "', '"
						+ member.getAddress().getApartment() + "', '" + member.getAddress().getCity() + "', '"
						+ member.getAddress().getPostal() + "', '" + member.getAddress().getState() + "');";
		return uiQuery;
	}
	
	public static String getLastAddressId(){
		return "SELECT LAST_INSERT_ID();";
	}
	
	public static String addMemberAddress(int memberId, int addressId, Member member){
		String uiQuery = " INSERT INTO member_address (member_id, address_id, type)"
						+ " VALUES (" + memberId + ", " + addressId + ", '" + member.getAddress().getType() + "');";
		return uiQuery;
	}
	
	public static String getMemberShip(Member member){
		String uiQuery = " SELECT id FROM membership"
				+ " WHERE content = '" + member.getMembership().getContent() + "' AND level = '"
				+ member.getMembership().getQuantity() + "';";
		return uiQuery;
	}
	
	public static String addMemberMembership(int memberId, int membership){
		String uiQuery = " INSERT INTO member_membership (member_id, membership_id, start_date, end_date)"
				+ " VALUES (" + memberId + ", " + membership + ", NOW(), date_add(NOW(),INTERVAL 1 YEAR));";
		return uiQuery;
	}

//CREATING MEDIA CONTENT*****************************************************************	
	public static String addMedia(int year, int stock,MediaType mediaType, String title, Platform platform, Genre genre){
		String uiQuery = "{CALL addMedia(" + year + "," + stock + ",'" + mediaType.getMediaType() 
					   + "','" + title + "','" + platform.getConsole() + "','" + platform.getVersion()
					   + "','" + genre.getTitle() + "', ?)}";
		return uiQuery;
	}
	
	public static String fetchNewMediaId(){
		String uiQuery = "SELECT @newMediaId";
		return uiQuery;
	}
	
	public static String addMediaDirector(String firstName, String surname, int mediaId){
		String uiQuery = "{CALL addDirector('" + firstName + "', '" + surname + "' , " + mediaId + ")}";
		return uiQuery;
	}
	
	public static String addMediaActor(String firstName, String surname, int mediaId, String role){
		String uiQuery = "{CALL addActor('" + firstName + "', '" + surname + "' , " + mediaId 
				+ " ,'" + role + "')}";
		return uiQuery;
	}
	
	public static String addMediaAward(String honor, String category, int mediaId){
		String uiQuery = "{CALL addAward(" + mediaId +  ",'" + category + "' ,'" + honor +"')}";
		return uiQuery;
	}
	
	public static String addMediaSequel(String title, int mediaId){
		String uiQuery = "{CALL addSequel('" + title + "' , " + mediaId + ")}";
		return uiQuery;
	}
	
	public static String rentTitle(int rentalId, int memberId, Date now){
		String uiQuery = "INSERT INTO rented (media_id, member_human_id, rented) VALUES"
				+ " (" + rentalId + ", " + memberId + ", '" + now + "')";
		return uiQuery;
	}
}
