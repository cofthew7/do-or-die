package hk.edu.uic.doordie.client.activity;

import hk.edu.uic.doordie.client.controller.DataTransporter;
import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UnknownUsersActivity extends Activity implements OnClickListener {

	private SharedPreferences account;
	private User my;

	private ListView lv;
	private SimpleAdapter userListAdapter;
	private ArrayList<HashMap<String, Object>> userArrayList;
	OnItemClickListener ocListener;
	private List<User> userList;
	private boolean isReload;

	private Button refreshButton;

	private DataTransporter dataTransporter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unknow_users);

		// get ui component
		refreshButton = (Button) findViewById(R.id.unknow_user_refresh);
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
		lv = (ListView) findViewById(R.id.unknown_user_listview);
		userArrayList = new ArrayList<HashMap<String, Object>>();
		ocListener = new OnItemClickListener() {
			HashMap<String, Object> map;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*// show comments
				map = (HashMap<String, Object>) lv.getItemAtPosition(arg2);
				Todo todo = new Todo();
				todo.setId((Integer) map.get("id"));
				todo.setName((String) map.get("name"));
				todo.setDeadline((Timestamp) map.get("deadline"));
				todo.setCreatedDate((Timestamp) map.get("createdDate"));
				todo.setUid((Integer) map.get("uid"));
				todo.setIsFinished((Integer) map.get("isFinished"));
				todo.setIsMonitored((Integer) map.get("isMonitored"));

				// change to comment activity
				Intent myLocalIntent = new Intent(UnknownUsersActivity.this,
						CommentActivity.class);
				myLocalIntent.putExtra("todo", todo);
				startActivity(myLocalIntent);*/
			}

		};
		lv.setOnItemClickListener(ocListener);

		// run thread
		new GetUnknownUsersThread().execute();
	}

	public void onResume() {
		super.onResume();
		if (isReload) {
			lv = (ListView) findViewById(R.id.unknow_user_refresh);
			userArrayList = new ArrayList<HashMap<String, Object>>();
			userListAdapter = null;
			lv.setOnItemClickListener(ocListener);

			new GetUnknownUsersThread().execute();
		}
	}

	private class GetUnknownUsersThread extends AsyncTask<String, Void, List<User>> {

		int myId;

		@Override
		protected void onPreExecute() {
			myId = my.getId();
			notice(myId + "");
		}

		@Override
		protected List<User> doInBackground(String... params) {

			try {
				List<User> unknownUsers = dataTransporter.getUnknownUserList(myId);
				return unknownUsers;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<User> result) {
			if (result != null) {
				// notice(result.get(0).getId()+"333333");

				for (int i = 0; i < result.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					User user = result.get(i);

					map.put("id", user.getId());
					map.put("email", user.getEmail());

					userArrayList.add(map);
				}

				// todoListAdapter = new SimpleAdapter(MyTodosActivity.this,
				// todoArrayList, myId, null, null);
				userListAdapter = new SimpleAdapter(
						UnknownUsersActivity.this,
						userArrayList,
						R.layout.single_user,
						new String[] { "id", "email" },
						new int[] { R.id.unknown_user_id, R.id.unknown_user_email});

				lv.setAdapter(userListAdapter);
			} else {
				notice("get todo list failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(UnknownUsersActivity.this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.new_todo:
			isReload = true;
			Intent intent = new Intent(UnknownUsersActivity.this,
					AddTodoActivity.class);
			startActivity(intent);
			break;
		case R.id.refresh:
			lv = (ListView) findViewById(R.id.mytodos_listview);
			userArrayList = new ArrayList<HashMap<String, Object>>();
			userListAdapter = null;
			lv.setOnItemClickListener(ocListener);

			new GetUnknownUsersThread().execute();
		}

	}
}
