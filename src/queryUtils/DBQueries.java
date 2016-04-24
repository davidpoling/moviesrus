package queryUtils;

import java.util.Map;
import java.util.logging.Logger;

import enumsDB.DBColumn;
import enumsDB.DBTable;

public class DBQueries {
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	String uiQuery;
	
	private final static String MEDIACOLUMNS =
			" titles.media_id as 'Stock #'," +
		    " titles.title AS 'Title'," +
		    " platform.console AS 'Console'," +
		    " platform.version AS 'Version'," +
		    " media.in_stock AS 'Available'," +
		    " genre AS 'Genre'";
	
	private final static String MEMBERCOLUMNS =
			" member_human_id as 'Member #'," +
		    " first_name AS 'First Name'," +
		    " surname AS 'Last Name'," +
		    " street AS 'Street'," +
		    " apartment AS 'Apartment'," +
		    " city AS 'City'," +
			" state_name AS 'State'," +
		    " postal AS 'Postal Code'";
	
	private final static String USERRENTEDCOLUMNS = MEDIACOLUMNS +
			", rented AS 'Checkout Date'," +
			" shipped AS 'Shipped'," + 
			" returned AS 'Returned'," + 
			" overdue AS 'Overdue'";
	
	private static String getTableColumn(DBTable table, DBColumn column){
		return table.getDbTableName() + "." + column.getDbColumnName();
	}
	
	public static String getMediaTable(DBTable media, String alias){
		String queryTable;
		if(media.equals(DBTable.MEDIA)){
			queryTable = new String(" (SELECT * FROM game UNION (SELECT * FROM movie)) as " + alias);
		}else if(media.equals(DBTable.MOVIE)){
			queryTable = new String(" (SELECT * FROM movie) as " + alias);
		}else{// if(table.equals(DBTable.GAME)){
			queryTable = new String(" (SELECT * FROM game) as " + alias);
		}
		return queryTable;
	}

//  Queries to get login information**************************************************
	public static String fetchMember(String email, String password){
		String uiQuery = "SELECT "
				+ "human.id, human.first_name, human.surname, "
				+ "address.id, address.street, address.apartment, "
				+ "address.city, address.state_name, address.postal, member_address.type, "
				+ "member.human_id, member.email, member.password, member.expiration, "
				+ "membership.id, membership.content, membership.level, member_membership.end_date "
				+ "FROM (SELECT * from member WHERE email = '" + email + "' and password = '" + password + "') as member, "
				+ "(SELECT * FROM human) as human, "
				+ "(SELECT * FROM member_membership) as member_membership, "
				+ "(SELECT * FROM membership) as membership, "
				+ "(SELECT * FROM member_address) as member_address, "
				+ "(SELECT * FROM address) as address "
				+ "WHERE "
				+ "member.human_id = human.id "
				+ "AND member_membership.member_id = member.human_id "
				+ "AND member_membership.membership_id = membership.id "
			    + "AND member.human_id = member_address.member_id "
			    + "AND member_address.address_id = address.id ";
		
		logger.info("Fetching member: " + uiQuery);
		return uiQuery;
	}
	
	public static String fetchAdmin(String email, String password){
		String uiQuery = "SELECT "
				+ "administrator.human_id, administrator.email, administrator.password "
				+ "FROM (SELECT * FROM administrator WHERE email = '" + email
				+ "' AND password = '" + password + "') as administrator, "
				+ "(SELECT * FROM human) as human "
				+ "WHERE "
				+ "administrator.human_id = human.id ";
	
		logger.info("Fetching admin : " + uiQuery);
		return uiQuery;
	}
	
//  Queries for the admin********************************************************************
	public static String fetchAllType(DBTable media, String keyword){
		String uiQuery = "SELECT " + MEDIACOLUMNS
		+ "FROM ";
		
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre";

		uiQuery +=  " ON media.id = media_platform.media_id"
				+ " AND media_platform.platform_id = platform.id"
				+ " AND media.id = titles.media_id"
				+ " AND media.id = media_genre.media_id"
				+ " AND media_genre.genre_id = genre.id";
		
		if(keyword != null){
			uiQuery += " WHERE titles.title LIKE '%" + keyword +"%'";
		}
		uiQuery += " ORDER BY titles.title ASC";

		logger.info("Fetching ALL by type : " + uiQuery);
		return uiQuery;
	}
	
	public static String fetchRentedInfo(DBTable media, DBColumn column){
		String uiQuery = "SELECT " + MEDIACOLUMNS + "," + MEMBERCOLUMNS
				+ "FROM ";
	
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " JOIN human JOIN member JOIN member_address JOIN address JOIN media"
				+ " JOIN media_genre JOIN genre JOIN media_platform JOIN platform JOIN rented";
		
		uiQuery +=  " ON media.id = media_genre.media_id"
				+ " AND media_genre.genre_id = genre.id"
				+ " AND media.id = media_platform.media_id"
				+ " AND media_platform.platform_id = platform.id"
				+ " AND media.id = rented.media_id"
				+ " AND media.id = titles.media_id"
				+ "	AND human.id = member.human_id"
				+ " AND member.human_id = member_address.member_id"
				+ " AND member_address.address_id = address.id"
				+ " AND rented.member_human_id = member.human_id"
				+ " WHERE member_address.type <> 'Billing'"
				+ " AND rented." + column.getDbColumnName() + " IS NULL";
		
		logger.info("Fetching Rented info : " + uiQuery);
		return uiQuery;
	}
	
	public static String fetchTopTen(DBTable media){
		String uiQuery = "SELECT " + MEDIACOLUMNS
				+ "FROM ";
		
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " JOIN rented JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre"
				+ " ON media.id = media_platform.media_id"
				+ " AND media.id = rented.media_id"
		        + " AND media_platform.platform_id = platform.id"
		        + " AND media.id = titles.media_id"
		        + " AND media.id = media_genre.media_id"
		        + " AND media_genre.genre_id = genre.id"
		        + " WHERE rented.rented <= (CURRENT_DATE() - INTERVAL 1 MONTH)"
		        + " GROUP BY titles.media_id"
		        + " ORDER BY COUNT(*) DESC"
		        + " LIMIT 10";
		
		logger.info("Fetching Top Ten : " + uiQuery);
		return uiQuery;
	}

	public static String fetchLast24(DBTable media){
		String uiQuery = "SELECT " + MEDIACOLUMNS
				+ "FROM ";
		
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " JOIN rented JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre"
				+ " ON media.id = media_platform.media_id"
				+ " AND media.id = rented.media_id"
		        + " AND media_platform.platform_id = platform.id"
		        + " AND media.id = titles.media_id"
		        + " AND media.id = media_genre.media_id"
		        + " AND media_genre.genre_id = genre.id"
		        + " WHERE rented.rented >= (CURRENT_DATE() - INTERVAL 1 DAY)";
		
		return uiQuery;
	}
	
	public static String fetchUnrented(DBTable media){
		String uiQuery = "SELECT " + MEDIACOLUMNS
				+ ", in_stock as 'Quantity in Stock', rented as 'Checkout Date', shipped as 'Shipped', "
				+ "returned as 'Returned', overdue as 'Overdue' "
				+ "FROM (SELECT * FROM rented) as rental "
				+ "NATURAL JOIN "
				+ "(SELECT member.human_id as 'member_human_id' FROM member ) as member "
				+ "NATURAL JOIN "
				+ "(SELECT * FROM (SELECT * FROM ";
		
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " NATURAL JOIN "
				+ "(SELECT id as media_id, in_stock FROM media) as media1 "
				+ "NATURAL JOIN "
				+ "(SELECT * FROM media_platform) as plat) as media_platform "
				+ "NATURAL JOIN "
				+ "(SELECT * FROM media_genre) as media_genre) as media;";
		
		return uiQuery;
	}
	
	public static String fetchMembers(){
		String uiQuery = "SELECT " + MEMBERCOLUMNS
				+ ", type AS 'Address Type', "
				+ " content AS 'Media Types',"
				+ " level AS 'Quantity',"
				+ " end_date AS 'Expires',"
				+ " email AS 'E-mail'"
				+ " FROM (human JOIN (SELECT human_id AS 'member_human_id', email FROM member) AS member"
				+ " JOIN member_address"
				+ " JOIN membership"
				+ " JOIN member_membership"
				+ " JOIN address"
				+ " ON human.id = member.member_human_id"
				+ " AND member.member_human_id = member_address.member_id"
				+ " AND member_address.address_id = address.id"
				+ " AND membership.id = member_membership.membership_id"
				+ " AND member.member_human_id = member_membership.member_id)"
				+ " ORDER BY member_human_id;";
		
		logger.info("Fetching Members : " + uiQuery);
		return uiQuery;
	}
	
	
//  Queries for the member********************************************************************
	public static String fetchAwards(DBTable media){
		String uiQuery = "SELECT " + MEDIACOLUMNS
				+ " , award.honor as 'Honor', award.category as 'Category' "
				+ "FROM ";

		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre"
				+ " JOIN media_award JOIN award";

		uiQuery +=  " ON media.id = media_platform.media_id"
				+ " AND media_platform.platform_id = platform.id"
				+ " AND media.id = titles.media_id"
				+ " AND media.id = media_genre.media_id"
				+ " AND media_genre.genre_id = genre.id"
				+ " AND media.id = media_award.media_id"
				+ " AND media_award.award_id = award.id";
				
		return uiQuery;
	}
	
	public static String fetchSequels(DBTable media){
		String uiQuery = "SELECT " + MEDIACOLUMNS
				+ " , sequels.title as 'Sequel Title', sequel.in_stock as 'Sequel Stock' "
				+ "FROM ";
		
				uiQuery += getMediaTable(media, "titles");
				
				uiQuery += " JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre"
						+ " JOIN media_sequel JOIN (SELECT * FROM media) AS sequel JOIN";
				
				uiQuery += getMediaTable(media, "sequels");

				uiQuery +=  " ON media.id = media_platform.media_id"
						+ " AND media_platform.platform_id = platform.id"
						+ " AND media.id = titles.media_id"
						+ " AND media.id = media_genre.media_id"
						+ " AND media_genre.genre_id = genre.id"
						+ " AND media.id = media_sequel.parent_id"
						+ " AND media_sequel.sequel_id = sequel.id"
						+ " AND sequels.media_id = sequel.id";
		return uiQuery;
	}

	public static String fetchMemberRentedCount(int memberId) {
		String uiQuery = "SELECT COUNT(*) FROM " + DBTable.RENTED.getDbTableName()
				+ " WHERE " + DBColumn.MEMBER_HUMANFID.getDbColumnName() + " = " + memberId
				+ " AND rented.returned IS NULL;";

				return uiQuery;
	}
	
	public static String fetchInStock(int rentalId){
		String uiQuery = "SELECT in_stock FROM media where media.id = " + rentalId + "";
		return uiQuery;
	}
	
	public static String fetchMembershipTypeId(String content, int level) {
		String uiQuery = "SELECT * FROM " + DBTable.MEMBERSHIP.getDbTableName()
				+ " WHERE " + DBColumn.CONTENT.getDbColumnName() + " = '" + content 
				+ "' AND " + DBColumn.LEVEL.getDbColumnName() + " = " + level + ";";
		
		return uiQuery;
	}
	
	public static String fetchCastIdFromMedia(DBTable table, DBColumn column, int mediaId) {
		String uiQuery = "SELECT " + column.getDbColumnName() + " FROM " + table.getDbTableName()
				+ " WHERE " + DBColumn.MEDIA_ID.getDbColumnName() + " = " + mediaId + ";";
		
		return uiQuery;
	}
	
	public static String fetchToday() {
		String uiQuery = "SELECT CURDATE();";
		
		return uiQuery;
	}
	
	public static String fetchUserRentedInfo(DBTable media, String email) {
		String uiQuery = "SELECT " + USERRENTEDCOLUMNS 
				+ " FROM ";
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += " JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre"
				+ " JOIN rented JOIN member";

		uiQuery +=  " ON media.id = media_platform.media_id"
				+ " AND media_platform.platform_id = platform.id"
				+ " AND media.id = titles.media_id"
				+ " AND media.id = media_genre.media_id"
				+ " AND media_genre.genre_id = genre.id"
				+ " AND media.id = rented.media_id"
				+ " AND rented.member_human_id = member.human_id"
				+ " WHERE email = '" + email + "';";

		return uiQuery;
	}
	
	public static String fetchUserNotRentedInfo(DBTable media, String email) {
		String uiQuery = "SELECT " + MEDIACOLUMNS
				+ " FROM ";
		
		uiQuery += getMediaTable(media, "titles");

		uiQuery += " JOIN media JOIN media_platform JOIN platform JOIN media_genre JOIN genre";

		uiQuery +=  " ON media.id = media_platform.media_id"
				+ " AND media_platform.platform_id = platform.id"
				+ " AND media.id = titles.media_id"
				+ " AND media.id = media_genre.media_id"
				+ " AND media_genre.genre_id = genre.id"
				+ " AND media.id NOT IN" 
				+ " (SELECT media_id FROM rented"
				+ " NATURAL JOIN"
				+ " member WHERE member.human_id = rented.member_human_id"
				+ " AND member.email= '" + email + "');";
		return uiQuery;
	}
		
	public static String fetchPersonByMedia(DBTable person ,DBTable media, String keyword) {
		DBTable dbRelation;
		DBColumn id;
		
		if(person.equals(DBTable.ACTOR)){
			dbRelation = DBTable.MEDIA_ACTOR;
			id = DBColumn.ACTORFID;
		}else{// if (person.equals(DBTable.DIRECTOR)){
			dbRelation = DBTable.MEDIA_DIRECTOR;
			id = DBColumn.DIRECTORFID;
		}
		
		String uiQuery = "SELECT " + MEDIACOLUMNS
		+ "FROM ";

		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += "NATURAL JOIN "
		+ "(SELECT id AS media_id, in_stock FROM media) AS media "
		+ "NATURAL JOIN "
		+ "(SELECT * FROM media_platform)"
		+ "NATURAL JOIN "
		+ "(SELECT * FROM media_genre)"
		+ "NATURAL JOIN "
		+ "(SELECT * FROM " + person.getDbTableName() + ")"
		+ "NATURAL JOIN "
		+ "(SELECT * FROM human WHERE first_name LIKE '%" + keyword + "%' OR surname LIKE '%" + keyword + "%') AS human "
		+ "NATURAL JOIN "
		+ "(SELECT * FROM " + dbRelation.getDbTableName() + ") as titles"
		+ "WHERE " + person.getDbTableName() + ".human_id = human.id "
		+ "AND " + dbRelation.getDbTableName() + "." + id.getDbColumnName() + " = " + person.getDbTableName() + ".human_id "
		+ "AND media.media_id = " + dbRelation.getDbTableName() + ".media_id ";
		
		return uiQuery;
	}
	
	public static String fetchMediaByColumn(DBTable media, DBColumn column, String keyword) {
		String uiQuery = "SELECT " + MEDIACOLUMNS
		+ "FROM ";
		
		uiQuery += getMediaTable(media, "titles");
		
		uiQuery += "NATURAL JOIN "
		+ "(SELECT id AS media_id, in_stock FROM media) AS media "
		+ "NATURAL JOIN "
		+ "(SELECT * FROM media_platform) AS media_platform "
		+ "NATURAL JOIN "
		+ "(SELECT * FROM media_genre) AS media_genre "
		+ "WHERE " + column.getDbColumnName() + " LIKE '%" + keyword + "%';";
		
		return uiQuery;
	}
	
	public static String fetchMultiKeyword(DBTable media,  Map<DBTable, String> values){
		String uiQuery =  "";
		
		if(values.containsKey(DBTable.ACTOR) && values.containsKey(DBTable.DIRECTOR)){
			uiQuery +=  "SELECT "
					+ "actors.title AS 'Title', "
					+ "actors.console AS 'Console', "
					+ "actors.version AS 'Platform Version', "
					+ "actors.available AS 'In Stock', "
					+ "actors.genre AS 'Genre', "
					+ "actors.first AS 'Actor:First', "
					+ "actors.last AS 'Last', "
					+ "directors.first AS 'Director: First', "
					+ "directors.last AS 'Last' "
					+ "FROM (";
			uiQuery += fetchKeyword(media, values);
			uiQuery += ") AS actors INNER JOIN (";
			values.remove(DBTable.ACTOR);
			uiQuery += fetchKeyword(media, values);
			uiQuery += ") as directors ON actors.title = directors.title";
		}else{	
			uiQuery += fetchKeyword(media, values);
		}
		
		return uiQuery;
	}
	
	public static String fetchKeyword(DBTable media,  Map<DBTable, String> values) {
		String keyword = "";
		String primaryName = "";
		boolean hasWhere = false;
		
		String uiQuery = "SELECT " + MEDIACOLUMNS +  ", media.date AS 'Year', human.first_name AS 'First', human.surname AS 'Last'";
		
		uiQuery += " FROM human JOIN media JOIN media_genre JOIN genre JOIN media_platform JOIN platform JOIN";
		
		uiQuery += getMediaTable(media, "titles");

		if(values.containsKey(DBTable.ACTOR)){
			uiQuery += " JOIN " + DBTable.ACTOR.getDbTableName() + " JOIN " + DBTable.MEDIA_ACTOR.getDbTableName() +" ";
		}else if(values.containsKey(DBTable.DIRECTOR)){
			uiQuery += " JOIN " + DBTable.DIRECTOR.getDbTableName() + " JOIN " + DBTable.MEDIA_DIRECTOR.getDbTableName() +" ";
		}

		uiQuery +=  " ON " + getTableColumn(DBTable.TITLE, DBColumn.MEDIA_ID)
				+ " = " + getTableColumn(DBTable.MEDIA, DBColumn.ID)
				+ " AND " + getTableColumn(DBTable.MEDIA, DBColumn.ID)
				+ " = " + getTableColumn(DBTable.MEDIA_GENRE, DBColumn.MEDIA_ID)
				+ " AND " + getTableColumn(DBTable.MEDIA_GENRE, DBColumn.GENREFID)
				+ " = " + getTableColumn(DBTable.GENRE, DBColumn.ID)
				+ " AND " + getTableColumn(DBTable.MEDIA, DBColumn.ID)
				+ " = " + getTableColumn(DBTable.MEDIA_PLATFORM, DBColumn.MEDIA_ID)
				+ " AND " + getTableColumn(DBTable.MEDIA_PLATFORM, DBColumn.PLATFORMFID)
				+ " = " + getTableColumn(DBTable.PLATFORM, DBColumn.ID);
				
		if(values.containsKey(DBTable.MOVIE) || values.containsKey(DBTable.GAME) || values.containsKey(DBTable.ALL)){
			uiQuery += " AND " + getTableColumn(DBTable.MEDIA, DBColumn.ID)
					+ " = " + getTableColumn(DBTable.TITLE, DBColumn.MEDIA_ID);
		}
	
		if(values.containsKey(DBTable.ACTOR)){
			primaryName = values.get(DBTable.ACTOR);
			uiQuery += " AND " + getTableColumn(DBTable.HUMAN, DBColumn.ID)
					+ " = " + getTableColumn(DBTable.ACTOR, DBColumn.HUMANFID)
					+ " AND " + getTableColumn(DBTable.ACTOR, DBColumn.HUMANFID)
					+ " = " + getTableColumn(DBTable.MEDIA_ACTOR, DBColumn.ACTORFID)
					+ " AND " + getTableColumn(DBTable.MEDIA_ACTOR, DBColumn.MEDIA_ID)
					+ " = " + getTableColumn(DBTable.TITLE, DBColumn.MEDIA_ID);
		}else if(values.containsKey(DBTable.DIRECTOR)){
			primaryName = values.get(DBTable.DIRECTOR);
			uiQuery += " AND " + getTableColumn(DBTable.HUMAN, DBColumn.ID)
					+ " = " + getTableColumn(DBTable.DIRECTOR, DBColumn.HUMANFID)
					+ " AND " + getTableColumn(DBTable.DIRECTOR, DBColumn.HUMANFID)
					+ " = " + getTableColumn(DBTable.MEDIA_DIRECTOR, DBColumn.DIRECTORFID)
					+ " AND " + getTableColumn(DBTable.MEDIA_DIRECTOR, DBColumn.MEDIA_ID)
					+ " = " + getTableColumn(DBTable.TITLE, DBColumn.MEDIA_ID);
		}
		if(values.containsKey(DBTable.ALL)|| values.containsKey(DBTable.ACTOR)
				|| values.containsKey(DBTable.DIRECTOR) || values.containsKey(DBTable.GENRE)
				|| values.containsKey(DBTable.PLATFORM) || values.containsKey(DBTable.PLATFORM_VERSION)){
			uiQuery += " WHERE (";
		
			if(values.containsKey(DBTable.ALL)){
				keyword = values.get(DBTable.ALL);
				if(values.containsKey(DBTable.MOVIE) || values.containsKey(DBTable.GAME) || values.containsKey(DBTable.MEDIA)){
					uiQuery += getTableColumn(DBTable.TITLE, DBColumn.TITLE) + " like '%" + keyword + "%' OR ";
				}
				
				uiQuery += DBColumn.FIRSTNAME.getDbColumnName() + " like '%"+ keyword + "%' OR "
						+ DBColumn.SURNAME.getDbColumnName() + " like '%" + keyword + "%' OR " 
						+ DBColumn.GENRE.getDbColumnName() + " like '%" + keyword + "%' OR " 
						+ DBColumn.PLATFORM_CONSOLE.getDbColumnName() + " like '%" + keyword + "%' OR " 
						+ DBColumn.PLATFORM_VERSION.getDbColumnName() + " like '%" + keyword + "%' ";
				hasWhere = true;
			}
			if(values.containsKey(DBTable.ACTOR)){
				if(hasWhere){
					uiQuery += ") AND (";
				}
				uiQuery += DBColumn.FIRSTNAME.getDbColumnName() + " like '%" + primaryName + "%' OR " 
						+ DBColumn.SURNAME.getDbColumnName() + " like '%" + primaryName + "%' ";
				hasWhere = true;
			}else if(values.containsKey(DBTable.DIRECTOR)){
				if(hasWhere){
					uiQuery += ") AND (";
				}
				uiQuery += DBColumn.FIRSTNAME.getDbColumnName() + " like '%" + primaryName + "%' OR " 
						+ DBColumn.SURNAME.getDbColumnName() + " like '%" + primaryName + "%' ";
				hasWhere = true;
			}
			if(values.containsKey(DBTable.GENRE)){
				if(hasWhere){
					uiQuery += ") AND (";
				}
				uiQuery += DBColumn.GENRE.getDbColumnName() + " = '" + values.get(DBTable.GENRE) + "' ";
				hasWhere = true;
			}
			if(values.containsKey(DBTable.PLATFORM)){
				if(hasWhere){
					uiQuery += ") AND (";
				}
				uiQuery += DBColumn.PLATFORM_CONSOLE.getDbColumnName() + " = '" + values.get(DBTable.PLATFORM) + "' ";
				hasWhere = true;
			}
			if(values.containsKey(DBTable.PLATFORM_VERSION)){
				if(hasWhere){
					uiQuery += ") AND (";
				}
				uiQuery += DBColumn.PLATFORM_VERSION.getDbColumnName() + " = '" + values.get(DBTable.PLATFORM_VERSION) + "' ";
				hasWhere = true;
			}
			if(hasWhere){
				uiQuery += ")";
			}
		}
		
		return uiQuery;
	}
}
