package hk.edu.uic.doordie.client.model.vo;

import java.sql.Timestamp;

public class Todo {
	private int id;
	private String name;
	private Timestamp deadline;
	private int isMonitored;
	private int isFinished;
	private Timestamp createdDate;

	private int uid;

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public int getId() {
		return id;
	}

	public int getIsFinished() {
		return isFinished;
	}

	public int getIsMonitored() {
		return isMonitored;
	}

	public String getName() {
		return name;
	}

	public int getUid() {
		return uid;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIsFinished(int isFinished) {
		this.isFinished = isFinished;
	}

	public void setIsMonitored(int isMonitored) {
		this.isMonitored = isMonitored;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
}
