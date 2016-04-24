package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import queryUtils.DBInserts;
import queryUtils.SQLConnection;
import enumsDB.MediaType;

public class Media {
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private int id;
	private String title = "";
	private Platform platform;
	private int inStock;
	private Genre genre;
	private int year;
	
	private MediaType mediaType;
	private Director director;
	private ArrayList<Actor> actors;
	private ArrayList<Award> awards;
	private Media sequel;

	public Media(){
		this.mediaType = MediaType.MEDIA;
		this.platform = new Platform();
		this.inStock = 0;
		this.genre = new Genre();
		this.year = 0;
		this.director = null;
		this.actors = new ArrayList<Actor>();
		this.awards = new ArrayList<Award>();
		this.sequel = null;
	}
	
	public Media(String title, int count){
		this.mediaType = MediaType.MEDIA;
		this.title = title;
		this.inStock = count;
	}
	
	public Media(int year, Platform platform, MediaType mediaType,
			String title, Director director,
			ArrayList<Actor> actors, Genre genre, ArrayList<Award> awards,
			Media sequel) {
		super();
		this.year = year;
		this.platform = platform;
		this.mediaType = mediaType;
		this.title = title;
		this.director = director;
		this.actors = actors;
		this.genre = genre;
		this.awards = awards;
		this.sequel = sequel;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getInStock() {
		return inStock;
	}
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public MediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Director getDirector() {
		return director;
	}
	public void setDirector(Director director) {
		this.director = director;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}
	public void setActors(ArrayList<Actor> actors) {
		this.actors = actors;
	}
	public void addActor(Actor actor) {
		this.actors.add(actor);
	}

	public String getGenre() {
		return genre.getTitle();
	}
	public void setGenre(String title) {
		this.genre.setTitle(title);
	}

	public ArrayList<Award> getAwards() {
		return awards;
	}
	public void setAwards(ArrayList<Award> awards) {
		this.awards = awards;
	}
	public void addAward(Award award) {
		this.awards.add(award);
	}

	public Media getSequel() {
		return sequel;
	}
	public void setSequel(Media sequel) {
		this.sequel = sequel;
	}
	
	public void createMedia(SQLConnection sqlConn){
		int mediaId;
		ResultSet resultSet;
		Connection conn = sqlConn.getConnection();
		Statement statement;
		CallableStatement stmt = null;
		logger.info(this.toString());
		try {
			statement = conn.createStatement();
			stmt = conn.prepareCall(DBInserts.addMedia(year, inStock, mediaType, title, platform, genre));
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			stmt.execute();
	    	mediaId = stmt.getInt("@newMediaId");
	    	if(director != null){
				stmt = conn.prepareCall(DBInserts.addMediaDirector(director.getFirstName(), director.getSurName(), mediaId));
				stmt.execute();
	    	}
			for(Actor actor: actors){
				stmt = conn.prepareCall(DBInserts.addMediaActor(actor.getFirstName(), actor.getSurName(), mediaId, actor.getRole()));
				stmt.execute();
			}
			for(Award award: awards){
				stmt = conn.prepareCall(DBInserts.addMediaAward(award.getHonor(), award.getCategory(), mediaId));
				stmt.execute();
			}
			if(sequel != null){
				stmt = conn.prepareCall(DBInserts.addMediaSequel(sequel.getTitle(), mediaId));
				stmt.execute();
			}
			statement.close();
			JOptionPane.showMessageDialog(null, "(" + inStock + ") " + title + " added to inventory.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Failed to add new media");
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String media = "media id: " + id + "\n"
				+ title + "\n"
				+ platform.toString();
				for(Award award: awards){
					media += award.toString();
				};
				media += "in Stock: " + inStock;
				media += genre.toString();
				media += "Year:" + year + " Media Type: " + mediaType.getMediaType() + "\n";
				if(director != null){
					media += director.toString();
				}
				media += "\n";
				for(Actor actor: actors){
					media += actor.toString();
				};
				if(sequel != null){
					media+= sequel.getTitle();
				}
		return media;
	}
}
