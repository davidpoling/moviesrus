package enumsDB;

public enum DBTable {
	ACTOR("actor"),
	ADDRESS("address"),
	ADMIN("admin"),
	ALL("all"),
	AWARD("award"),
	CAST("cast"),
	DIRECTOR("director"),
	HUMAN("human"),
	GAME("game"),
	GENRE("genre"),
	MEDIA("media"),
	MEDIA_ACTOR("media_actor"),
	MEDIA_AWARD("media_award"),
	MEDIA_DIRECTOR("media_director"),
	MEDIA_GENRE("media_genre"),
	MEDIA_PLATFORM("media_platform"),
	MEDIA_SEQUEL("media_sequel"),
	MEMBER("member"),
	MEMBER_ADDRESS("member_address"),
	MEMBER_MEMBERSHIP("member_membership"),
	MEMBER_RENTED("member_rented"),
	MEMBERSHIP("membership"),
	MOVIE("movie"),
	PLATFORM("platform"),
	PLATFORM_VERSION("platform_version"),
	RENTED("rented"),
	STATE("state"),
	TITLE("titles");//TABLE ALIAS FOR MOVIES AND GAMES
	
	private final String dbTableName;
	
	DBTable(String dbTableName){
		this.dbTableName = dbTableName;
	}
	
	public String getDbTableName() {
		return dbTableName;
	}
}
