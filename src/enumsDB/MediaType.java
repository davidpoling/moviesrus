package enumsDB;

public enum MediaType {
	GAME("game"),
	MOVIE("movie"),
	MEDIA("media");
	
	private final String mediaType;
	
	MediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType() {
		return mediaType;
	}
}
