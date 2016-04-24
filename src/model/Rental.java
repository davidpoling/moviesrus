package model;

import java.sql.Date;

public class Rental {
	private Media media = null;
	private Member member = null;
	private Date checkedOut;
	private Date shipped;
	private Date returned;
	private boolean overdue;
	
	public Rental() {
		super();
	}
	
	public Rental(Media media, Member member) {
		super();
		this.media = media;
		this.member = member;
	}

	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public Date getCheckedOut() {
		return checkedOut;
	}
	public void setCheckedOut(Date checkedOut) {
		this.checkedOut = checkedOut;
	}

	public Date getShipped() {
		return shipped;
	}
	public void setShipped(Date shipped) {
		this.shipped = shipped;
	}

	public Date getReturned() {
		return returned;
	}
	public void setReturned(Date returned) {
		this.returned = returned;
	}

	public boolean isOverdue() {
		return overdue;
	}
	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
	}
	
	@Override
	public String toString() {
		String rental = "";
		if(media != null){
			rental += media.toString();
		}
		if(member != null){
			rental+= member.toString();
		}

		return rental;
	}
}
