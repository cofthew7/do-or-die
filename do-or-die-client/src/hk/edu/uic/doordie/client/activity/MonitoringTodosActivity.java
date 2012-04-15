package hk.edu.uic.doordie.client.activity;

import hk.edu.uic.doordie.client.controller.DataTransporter;
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MonitoringTodosActivity extends Activity implements
		OnClickListener {

	private SharedPreferences account;
	private User my;

	private ListView lv;
	private SimpleAdapter todoListAdapter;
	private ArrayList<HashMap<String, Object>> todoArrayList;
	OnItemClickListener ocListener;
	private Map<List<User>, List<Todo>> monitoringTodomap;
	private boolean isReload;

	private Button refreshButton;

	private DataTransporter dataTransporter;
	
	private Context mContext;
	private EditText notification;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_monitoring_todos);

		// get ui component
		refreshButton = (Button) findViewById(R.id.monitoring_refresh);
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
		mContext = MonitoringTodosActivity.this;
		lv = (ListView) findViewById(R.id.monitoring_listview);
		todoArrayList = new ArrayList<HashMap<String, Object>>();
		ocListener = new OnItemClickListener() {
			HashMap<String, Object> map;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				map = (HashMap<String, Object>)lv.getItemAtPosition(arg2);
				notificationDialog((Integer)map.get("todo_id"), (Integer)map.get("user_id"));
			}

		};
		lv.setOnItemClickListener(ocListener);

		// run thread
		new GetMonitoringTodosThread().execute();
	}

	public void onResume() {
		super.onResume();
		if (isReload) {
			lv = (ListView) findViewById(R.id.mytodos_listview);
			todoArrayList = new ArrayList<HashMap<String, Object>>();
			todoListAdapter = null;
			lv.setOnItemClickListener(ocListener);

			new GetMonitoringTodosThread().execute();
		}
	}

	private class GetMonitoringTodosThread extends
			AsyncTask<String, Void, Map<List<User>, List<Todo>>> {

		int myId;

		@Override
		protected void onPreExecute() {
			myId = my.getId();
			//notice(myId + "");
		}

		@Override
		protected Map<List<User>, List<Todo>> doInBackground(String... params) {

			try {
				Map<List<User>, List<Todo>> myTodos = dataTransporter
						.getMonitoringTodos(myId);
				return myTodos;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Map<List<User>, List<Todo>> result) {
			if (result != null) {
				// notice(result.get(0).getId()+"333333");

				List<User> userList = null;
				List<Todo> todoList = null;

				Iterator<Entry<List<User>, List<Todo>>> it = result.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<List<User>, List<Todo>> entry = it.next();
					userList = entry.getKey();
					todoList = entry.getValue();
				}

				for (int i = 0; i < userList.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					User user = userList.get(i);
					Todo todo = todoList.get(i);

					map.put("todo_id", todo.getId());
					map.put("todo_name", todo.getName());
					map.put("todo_deadline", todo.getDeadline());
					map.put("todo_createdDate", todo.getCreatedDate());
					map.put("todo_uid", todo.getUid());
					map.put("todo_isFinished", todo.getIsFinished());
					map.put("todo_isMonitored", todo.getIsMonitored());

					map.put("user_id", user.getId());
					map.put("user_email", user.getEmail());

					todoArrayList.add(map);
				}
				Log.e("monitoring", userList.size() + "        ");
				// todoListAdapter = new SimpleAdapter(MyTodosActivity.this,
				// todoArrayList, myId, null, null);
				todoListAdapter = new SimpleAdapter(
						MonitoringTodosActivity.this, todoArrayList,
						R.layout.single_monitoring_todo, new String[] {
								"todo_id", "todo_name", "todo_deadline",
								"user_email" }, new int[] {
								R.id.monitoring_todo_id,
								R.id.monitoring_todo_name,
								R.id.monitoring_todo_deadline,
								R.id.monitoring_user_email });

				lv.setAdapter(todoListAdapter);
			} else {
				//notice("get todo list failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(MonitoringTodosActivity.this, str, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.monitoring_refresh:
			lv = (ListView) findViewById(R.id.monitoring_listview);
			todoArrayList = new ArrayList<HashMap<String, Object>>();
			todoListAdapter = null;
			lv.setOnItemClickListener(ocListener);

			new GetMonitoringTodosThread().execute();
		}

	}
	
	public void notificationDialog(final int todoId, final int uid) {
		final AlertDialog.Builder builder =new AlertDialog.Builder(mContext);
		LayoutInflater flater = LayoutInflater.from(mContext);
		final View view = flater.inflate(R.layout.notification_box, null);
		Button send = (Button) view.findViewById(R.id.send_notification);
		Button cancle = (Button) view.findViewById(R.id.cancle_notification);
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		dialog.show();
		
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notification = (EditText) view.findViewById(R.id.notification);
				dataTransporter.addNotification(todoId, uid, notification.getText().toString());
				dialog.dismiss();
			}
		});
		
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
        
	}
}
