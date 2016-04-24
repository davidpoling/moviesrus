package enumsDB;

public enum DBColumn {
	ACTORFID("actor_id"),
	ADDRESSFID("address_id"),
	APT_NUM("apartment"),
	CATEGORY("category"),
	CITY("city"),
	CONSOLE("console"),
	CONTENT("content"),
	DATE("date"),
	DIRECTORFID("director_id"),
	EMAIL("email"),
	EXPIRATION("expiration"),
	FIRSTNAME("first_name"),
	GENRE("genre"),
	GENREFID("genre_id"),
	HONOR("honor"),
	HUMANFID("human_id"),
	ID("id"),
	LEVEL("level"),
	MEDIA_ID("media_id"),
	MEDIA_CONSOLE("media_console"),
	MEDIA_VERSION("media_version"),
	MEMBERFID("member_id"),
	MEMBER_HUMANFID("member_human_id"),
	MEMBERSHIPFID("membership_id"),
	PARENT("parent_id"),
	PASSWORD("password"),
	PLATFORM_CONSOLE("console"),
	PLATFORMFID("platform_id"),
	PLATFORM_VERSION("version"),
	POSTAL("postal"),
	ROLE("role"),
	RETURNED("returned"),
	SHIPPED("shipped"),
	STATE("state_name"),
	STREET("street"),
	SURNAME("surname"),
	TITLE("title"),
	TYPE("type"),
	VERSION("version");
	
	private final String dbColumnName;
	
	DBColumn(String dbColumnName){
		this.dbColumnName = dbColumnName;
	}
	
	public String getDbColumnName() {
		return dbColumnName;
	}
}
