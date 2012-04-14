package hk.edu.uic.doordie.client.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;

public class JSONParser {
	public User toUser(String userString) {
		User user = new User();
		
		try {
			JSONObject obj = new JSONObject(userString);
			
			user.setId(obj.getInt("id"));
			user.setEmail(obj.getString("email"));
			user.setPassword(obj.getString("password"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public List<User> toFriendList(String myFrineds) {
		List<User> userList = new LinkedList<User>();
		
		try {
			JSONArray ary = new JSONArray(myFrineds);
			
			for (int i = 0; i <ary.length(); i++) {
				JSONObject obj = ary.getJSONObject(i);
				User user = new User();
				user.setId(obj.getInt("id"));
				user.setEmail(obj.getString("email"));
				user.setPassword(obj.getString("password"));
				
				userList.add(user);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userList;
	}
	
	public Map<User, List<Todo>> toUserAndTodos(String myInfoAndString) {
		User user = new User();
		List<Todo> todoList = new LinkedList<Todo>();
		
		try {
			JSONObject obj = new JSONObject(myInfoAndString);
			
			user.setId(obj.getInt("id"));
			user.setEmail(obj.getString("email"));
			user.setPassword(obj.getString("password"));
			
			JSONArray ary = obj.getJSONArray("myTodos");
			for(int i = 0; i < ary.length(); i++) {
				JSONObject obj2 = ary.getJSONObject(i);
				Todo todo = new Todo();
				
				todo.setId(obj2.getInt("id"));
				todo.setUid(obj2.getInt("uid"));
				todo.setName(obj2.getString("name"));
				todo.setDeadline(Timestamp.valueOf(obj2.getString("deadline")));
				todo.setIsMonitored(obj2.getInt("isMonitored"));
				todo.setIsFinished(obj2.getInt("isFinished"));
				todo.setCreatedDate(Timestamp.valueOf(obj2.getString("createdDate")));
				
				todoList.add(todo);
			}
			
			Map<User, List<Todo>> myInfoAndTodos = new HashMap<User, List<Todo>>();
			myInfoAndTodos.put(user, todoList);
			
			return myInfoAndTodos;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
