package hk.edu.uic.doordie.client.activity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hk.edu.uic.doordie.client.controller.DataTransporter;
import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyTodosActivity extends Activity implements OnClickListener{

	private SharedPreferences account;
	private User my;

	private ListView lv;
	private SimpleAdapter todoListAdapter;
	private ArrayList<HashMap<String, Object>> todoArrayList;
	OnItemClickListener ocListener;
	private List<Todo> todoList;
	private boolean isReload;

	private Button addTodoButton;
	private Button refreshButton;

	private DataTransporter dataTransporter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_todos);

		// get ui component
		addTodoButton = (Button) findViewById(R.id.new_todo);
		refreshButton = (Button) findViewById(R.id.refresh);
		addTodoButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);
		
		// get account
		account = getSharedPreferences("account.xml", 0);
		my = new User();
		my.setId(account.getInt("id", 0));
		my.setEmail(account.getString("email", null));
		my.setPassword(account.getString("password", null));

		// new data transporter
		dataTransporter = new DataTransporter();

		// new thread to get my todo list
		isReload = false;
		lv = (ListView) findViewById(R.id.mytodos_listview);
		//lv.setSelector(R.drawable.item_background);
		todoArrayList = new ArrayList<HashMap<String, Object>>();
		ocListener = new OnItemClickListener() {
			HashMap<String, Object> map;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// show comments
				map = (HashMap<String, Object>)lv.getItemAtPosition(arg2);
				Todo todo = new Todo();
				todo.setId((Integer)map.get("id"));
				todo.setName((String)map.get("name"));
				todo.setDeadline((Timestamp)map.get("deadline"));
				todo.setCreatedDate((Timestamp)map.get("createdDate"));
				todo.setUid((Integer)map.get("uid"));
				todo.setIsFinished((Integer)map.get("isFinished"));
				todo.setIsMonitored((Integer)map.get("isMonitored"));
				
				// change to comment activity
				Intent myLocalIntent = new Intent(MyTodosActivity.this, NotificationActivity.class);
				myLocalIntent.putExtra("todo", todo);
				startActivity(myLocalIntent);
			}
			
		};
		lv.setOnItemClickListener(ocListener);
		
		// run thread
		new GetMyTodosThread().execute();

	}

	public void onResume() {
		super.onResume();
		if(isReload) {
			lv = (ListView) findViewById(R.id.mytodos_listview);
			todoArrayList = new ArrayList<HashMap<String, Object>>();
			todoListAdapter = null;
			lv.setOnItemClickListener(ocListener);
			
			new GetMyTodosThread().execute();
		}
	}
	
	private class GetMyTodosThread extends AsyncTask<String, Void, List<Todo>> {

		int myId;

		@Override
		protected void onPreExecute() {
			myId = my.getId();
			//notice(myId + "");
		}

		@Override
		protected List<Todo> doInBackground(String... params) {

			try {
				List<Todo> myTodos = dataTransporter.getMyTodos(myId);
				return myTodos;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Todo> result) {
			if (result != null) {
				// notice(result.get(0).getId()+"333333");

				for (int i = 0; i < result.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					Todo todo = result.get(i);
					
					map.put("id", todo.getId());
					map.put("name", todo.getName());
					map.put("deadline", todo.getDeadline());
					map.put("createdDate", todo.getCreatedDate());
					map.put("uid", todo.getUid());
					map.put("isFinished", todo.getIsFinished());
					map.put("isMonitored", todo.getIsMonitored());
					
					todoArrayList.add(map);
				}
				
				//todoListAdapter = new SimpleAdapter(MyTodosActivity.this, todoArrayList, myId, null, null);
				todoListAdapter = new SimpleAdapter(MyTodosActivity.this, todoArrayList, R.layout.single_todo,
						new String[]{"todo_id", "name", "deadline", "createdDate"},
						new int[]{R.id.todo_id, R.id.my_todo_name, R.id.my_todo_deadline, R.id.my_todo_createdDate});
				
				lv.setAdapter(todoListAdapter);
			} else {
				//notice("get todo list failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(MyTodosActivity.this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
		case R.id.new_todo:
			isReload = true;
			Intent intent = new Intent(MyTodosActivity.this, AddTodoActivity.class);
			startActivity(intent);
			break;
		case R.id.refresh:
			lv = (ListView) findViewById(R.id.mytodos_listview);
			todoArrayList = new ArrayList<HashMap<String, Object>>();
			todoListAdapter = null;
			lv.setOnItemClickListener(ocListener);
			
			new GetMyTodosThread().execute();
		}
	}
}
