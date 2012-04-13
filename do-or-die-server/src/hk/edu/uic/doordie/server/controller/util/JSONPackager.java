package hk.edu.uic.doordie.server.controller.util;

import java.util.List;

import hk.edu.uic.doordie.server.model.vo.Comment;
import hk.edu.uic.doordie.server.model.vo.Todo;
import hk.edu.uic.doordie.server.model.vo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONPackager {
	
	// package my info and my todos
	public JSONObject packageMyInfoAndTodos(User user, List<Todo> todoList) {
		JSONObject myInfoAndTodos = new JSONObject();
		
		myInfoAndTodos.put("id", user.getId());
		myInfoAndTodos.put("email", user.getEmail());
		myInfoAndTodos.put("password", user.getPassword());
		myInfoAndTodos.put("avatar", user.getAvatar());
		if(todoList!=null) {
			myInfoAndTodos.put("myTodos", packageMyTodos(todoList));
		} else {
			myInfoAndTodos.put("myTodos", null);
		}
		
		
		return myInfoAndTodos;
	}
	
	// package todos
	public JSONArray packageMyTodos(List<Todo> todoList) {
		JSONArray myTodos = new JSONArray();
		
		for(Todo todo:todoList) {
			JSONObject obj = new JSONObject();
			
			obj.put("id", todo.getId());
			obj.put("uid", todo.getUid());
			obj.put("name", todo.getName());
			obj.put("deadline", todo.getDeadline());
			obj.put("isMonitored", todo.getIsMonitored());
			obj.put("isFinished", todo.getIsFinished());
			obj.put("createdDate", todo.getCreatedDate());
			
			myTodos.add(obj);
		}
		
		return myTodos;
	}

	public JSONArray packageComments(List<Comment> commentList) {
		JSONArray comments = new JSONArray();
		
		for(Comment comment:commentList) {
			JSONObject obj = new JSONObject();
			
			obj.put("id", comment.getId());
			obj.put("todoId", comment.getTodoid());
			obj.put("uid", comment.getUid());
			obj.put("content", comment.getContent());
			obj.put("createdDate", comment.getCreatedDate());
			
			comments.add(obj);
		}
		
		return comments;
	}

	public JSONArray packageFriends(List<User> userList) {
		JSONArray friends = new JSONArray();
		
		for (User user:userList) {
			JSONObject obj = new JSONObject();
			
			obj.put("id", user.getId());
			obj.put("email", user.getEmail());
			obj.put("password", user.getPassword());
			obj.put("avatar", user.getAvatar());
			
			friends.add(obj);
		}
		
		return friends;
	}
}
