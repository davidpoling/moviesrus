package queryUtils;

import java.util.logging.Logger;

import model.Address;
import model.Rental;
import enumsDB.DBColumn;
import enumsDB.DBTable;
import enumsProject.TableAction;

public class DBUpdates {
	private final static Logger logger = Logger.getLogger(Class.class.getName());

//MEMBER UPDATE**************************************************************************
	public static String updateHuman(String firstName, String surname, int id){
		
		String uiQuery = "UPDATE " + DBTable.HUMAN.getDbTableName() + " SET "
				+ DBColumn.FIRSTNAME.getDbColumnName() + " = '" + firstName + "', "
				+ DBColumn.SURNAME.getDbColumnName() + " = '" + surname + "'"
				+ " WHERE " + DBColumn.ID.getDbColumnName() + " = " + id + ";";
		logger.info("Update Human : " + uiQuery);
		return uiQuery;
	}
	
	public static String updateAddress(Address address){
		
		String uiQuery = "UPDATE " + DBTable.ADDRESS.getDbTableName() + " SET "
				+ DBColumn.STREET.getDbColumnName() + " = '" + address.getStreet() + "', "
				+ DBColumn.APT_NUM.getDbColumnName() + " = '" + address.getApartment() + "', "
				+ DBColumn.CITY.getDbColumnName() + " = '" + address.getCity() + "', "
				+ DBColumn.STATE.getDbColumnName() + " = '" + address.getState() + "', "
				+ DBColumn.POSTAL.getDbColumnName() + " = '" + address.getPostal() + "'"
				+ " WHERE " + DBColumn.ID.getDbColumnName() + " = " + address.getId() + ";";
		
		logger.info("Update Address : " + uiQuery);
		return uiQuery;
	}
	
	public static String updateMemberEmail(String email, int id){
		
		String uiQuery = "UPDATE " + DBTable.MEMBER.getDbTableName() + " SET "
				+ DBColumn.EMAIL.getDbColumnName() + " = '" + email + "'"
				+ " WHERE " + DBColumn.HUMANFID.getDbColumnName() + " = " + id + ";";
		
		logger.info("Update Member : " + uiQuery);
		return uiQuery;
	}
	
	public static String updateMemberPassword(String password, int id){
		
		String uiQuery = "UPDATE " + DBTable.MEMBER.getDbTableName() + " SET "
				+ DBColumn.PASSWORD.getDbColumnName() + " = '" + password + "'"
				+ " WHERE " + DBColumn.HUMANFID.getDbColumnName() + " = " + id + ";";
		
		logger.info("Update Member : " + uiQuery);
		return uiQuery;
	}
	
	public static String updateMemberAddressType(String type, int memberId, int addressId){
		
		String uiQuery = "UPDATE " + DBTable.MEMBER_ADDRESS.getDbTableName() + " SET "
				+ DBColumn.TYPE.getDbColumnName() + " = '" + type + "'"
				+ " WHERE " + DBColumn.MEMBERFID.getDbColumnName() + " = " + memberId
				+ " AND " + DBColumn.ADDRESSFID.getDbColumnName() + " = " + addressId + ";";
		
		logger.info("Update Address Type : " + uiQuery);
		return uiQuery;
	}
	
	public static String updateMemberMemberShip(int memberId, int membershipId){
		
		String uiQuery = "UPDATE " + DBTable.MEMBER_MEMBERSHIP.getDbTableName() + " SET "
				+ DBColumn.MEMBERSHIPFID.getDbColumnName() + " = " + membershipId
				+ " WHERE " + DBColumn.MEMBERFID.getDbColumnName() + " = " + memberId + ";";
		
		logger.info("Update Membership Based on Id : " + uiQuery);
		return uiQuery;
	}
	
//INVENTORY CONTROL=*********************************************************************
	
	public static String updateInventory(Rental rental, TableAction action){
		String uiQuery = "UPDATE " + DBTable.RENTED.getDbTableName() + " SET ";
			if(action.equals(TableAction.SHIP)){
				uiQuery += DBColumn.SHIPPED.getDbColumnName();
			}else if(action.equals(TableAction.RETURN)){
				uiQuery += DBColumn.RETURNED.getDbColumnName();
			}
			uiQuery += " = current_date()"
					+ " WHERE media_id = " + rental.getMedia().getId() + " AND "
					+ DBColumn.MEMBER_HUMANFID.getDbColumnName() + " = " + rental.getMember().getId() +";";
		return uiQuery;
	}
}
