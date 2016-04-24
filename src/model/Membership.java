package model;

import java.sql.Date;

public class Membership {
	private int id;
	private String content;
	private int quantity;
	private Date expirationDate;

	public Membership() {
	}
	
	public Membership(int id, String content, int quantity, Date date) {
		this.id = id;
		this.content = content;
		this.quantity = quantity;
		this.expirationDate = date;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpiration(Date expiration) {
		this.expirationDate = expiration;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return 	"membership id: " + id + "\n" + 
				"Content: "+ content + " Quantity: " + quantity + "\n";
	}
}
