package queryUtils;

import model.Member;
import model.Rental;
import enumsDB.DBColumn;
import enumsDB.DBTable;

public class DBDeletes {
	
	public static String deleteMember(Member member){
		
		String uiQuery = "DELETE FROM " +  DBTable.MEMBER_MEMBERSHIP.getDbTableName()
				+ " where " + DBColumn.MEMBERFID.getDbColumnName()
				+ " = " + member.getId() + ";";
		return uiQuery;
	}
	
	public static String resetPasswordAndDate(Member member){
		
		String uiQuery = "UPDATE " + DBTable.MEMBER.getDbTableName() + " SET "
				+ DBColumn.PASSWORD.getDbColumnName() + " = NULL ,"
				+ DBColumn.EXPIRATION.getDbColumnName() + " = NULL "
				+ " WHERE " + DBColumn.HUMANFID.getDbColumnName() + " = " + member.getId() + ";";
		
		return uiQuery;
	}
	
	public static String checkRented(Rental rental){
		
		String uiQuery = "SELECT " + DBColumn.MEDIA_ID.getDbColumnName() + " FROM " +  DBTable.RENTED.getDbTableName()
				+ " where " + DBColumn.MEDIA_ID.getDbColumnName()
				+ " = " + rental.getMedia().getId() + ";";
		
		return uiQuery;
	}
	
	public static String deleteFromTableColumn(DBTable table, DBColumn column, int mediaId){
		
		String uiQuery = "DELETE FROM " +  table.getDbTableName()
				+ " where " + column.getDbColumnName()
				+ " = " + mediaId + ";";
		
		return uiQuery;
	}
	
	public static String deleteMedia(int mediaId){
		String uiQuery="DELETE FROM media WHERE id =" + mediaId+";";
		return uiQuery;
	}
}
