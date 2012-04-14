package hk.edu.uic.doordie.client.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private int todoid;

	private int uid;

	private String content;

	private Timestamp createdDate;

	public String getContent() {
		return content;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public int getId() {
		return id;
	}

	public int getTodoid() {
		return todoid;
	}

	public int getUid() {
		return uid;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTodoid(int todoid) {
		this.todoid = todoid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
}
