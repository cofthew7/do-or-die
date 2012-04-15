package hk.edu.uic.doordie.client.activity;

import hk.edu.uic.doordie.client.controller.DataTransporter;
import hk.edu.uic.doordie.client.model.vo.Comment;
import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NotificationActivity extends Activity implements OnClickListener {
	private SharedPreferences account;
	private User my;

	private ListView lv;
	private SimpleAdapter notifyListAdapter;
	private ArrayList<HashMap<String, Object>> notifyArrayList;
	OnItemClickListener ocListener;
	private List<Todo> notifyList;
	private Todo notifiedTodo;
	private boolean isReload;

	private Button refreshButton;
	private TextView myTodoName;
	private TextView myTodoDeadline;
	private TextView myTodoCreatedDate;

	private DataTransporter dataTransporter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_list);

		// get ui component
		refreshButton = (Button) findViewById(R.id.notification_refresh);
		refreshButton.setOnClickListener(this);
		myTodoCreatedDate = (TextView) findViewById(R.id.notify_todo_createdDate);
		myTodoName = (TextView) findViewById(R.id.notify_todo_name);
		myTodoDeadline = (TextView) findViewById(R.id.notify_todo_deadline);

		// get intent
		Intent myLocalIntent = getIntent();
		notifiedTodo = (Todo)myLocalIntent.getSerializableExtra("todo");
		
		// set todo view
		myTodoDeadline.setText(notifiedTodo.getDeadline().toString());
		myTodoName.setText(notifiedTodo.getName());
		myTodoCreatedDate.setText(notifiedTodo.getCreatedDate().toString());
		
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
		lv = (ListView) findViewById(R.id.notification_listview);
		notifyArrayList = new ArrayList<HashMap<String, Object>>();
		ocListener = new OnItemClickListener() {
			HashMap<String, Object> map;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}

		};
		lv.setOnItemClickListener(ocListener);

		// run thread
		new GetNotificationThread().execute();

	}

	public void onResume() {
		super.onResume();
		if (isReload) {
			lv = (ListView) findViewById(R.id.mytodos_listview);
			notifyArrayList = new ArrayList<HashMap<String, Object>>();
			notifyListAdapter = null;
			lv.setOnItemClickListener(ocListener);

			new GetNotificationThread().execute();
		}
	}

	private class GetNotificationThread extends AsyncTask<String, Void, Map<List<User>, List<Comment>>> {

		int myId;

		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected Map<List<User>, List<Comment>> doInBackground(String... params) {

			try {
				Map<List<User>, List<Comment>> notifications = dataTransporter.getNotificationList(notifiedTodo.getId());
				return notifications;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Map<List<User>, List<Comment>> result) {
			if (result != null) {
				// notice(result.get(0).getId()+"333333");

				List<User> userList = null;
				List<Comment> commentList = null;
				
				Iterator<Entry<List<User>, List<Comment>>> it = result.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<List<User>, List<Comment>> entry = it.next();
					userList = entry.getKey();
					commentList = entry.getValue();
				}
				
				for (int i = 0; i < userList.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					User user = userList.get(i);
					Comment comment = commentList.get(i);

					map.put("comment_id", comment.getId());
					map.put("comment_name", comment.getContent());
					map.put("comment_createdDate", comment.getCreatedDate());

					map.put("user_id", user.getId());
					map.put("user_email", user.getEmail());

					notifyArrayList.add(map);
				}
				Log.e("monitoring", userList.size() + "        ");
				// todoListAdapter = new SimpleAdapter(MyTodosActivity.this,
				// todoArrayList, myId, null, null);
				notifyListAdapter = new SimpleAdapter(
						NotificationActivity.this, notifyArrayList,
						R.layout.single_notification, new String[] {
								"comment_id", "comment_name", "comment_createdDate",
								"user_email" }, new int[] {
								R.id.notification_id,
								R.id.notification_content,
								R.id.notification_createdDate,
								R.id.notifier });

				lv.setAdapter(notifyListAdapter);
			} else {
				//notice("get comment list failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(NotificationActivity.this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.refresh:
			isReload = true;
			lv = (ListView) findViewById(R.id.mytodos_listview);
			notifyArrayList = new ArrayList<HashMap<String, Object>>();
			notifyListAdapter = null;
			lv.setOnItemClickListener(ocListener);

			new GetNotificationThread().execute();
		}

	}
}
