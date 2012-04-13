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
			obj.put("deadline", todo.getDeadline().toString());
			obj.put("isMonitored", todo.getIsMonitored());
			obj.put("isFinished", todo.getIsFinished());
			obj.put("createdDate", todo.getCreatedDate().toString());
			
			myTodos.add(obj);
		}
		
		return myTodos;
	}

	public JSONArray packageMyFriendsTodos(List<Todo> todoList, List<User> userList) {
		JSONArray myFriendsTodos = new JSONArray();
		
		for(int i = 0; i < todoList.size(); i++) {
			Todo todo = todoList.get(i);
			User user = userList.get(i);
			
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			
			obj.put("id", todo.getId());
			obj.put("uid", todo.getUid());
			obj.put("name", todo.getName());
			obj.put("deadline", todo.getDeadline().toString());
			obj.put("isMonitored", todo.getIsMonitored());
			obj.put("isFinished", todo.getIsFinished());
			obj.put("createdDate", todo.getCreatedDate().toString());
			
			obj2.put("id", user.getId());
			obj2.put("email", user.getEmail());
			obj2.put("avatar", user.getAvatar());
			
			obj.put("user", obj2);
			
			myFriendsTodos.add(obj);
		}
		
		return myFriendsTodos;
	}
	
	public JSONArray packageComments(List<Comment> commentList, List<User> userList) {
		JSONArray comments = new JSONArray();
		
		for(int i = 0; i < commentList.size(); i++) {
			Comment comment = commentList.get(i);
			User user = userList.get(i);
			
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			
			obj.put("id", comment.getId());
			obj.put("todoId", comment.getTodoid());
			obj.put("uid", comment.getUid());
			obj.put("content", comment.getContent());
			obj.put("createdDate", comment.getCreatedDate().toString());
			
			obj2.put("id", user.getId());
			obj2.put("email", user.getEmail());
			obj2.put("avatar", user.getAvatar());
			
			obj.put("user", obj2);
			
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
