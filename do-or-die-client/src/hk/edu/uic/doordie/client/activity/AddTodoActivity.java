package hk.edu.uic.doordie.client.activity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import hk.edu.uic.doordie.client.controller.DataTransporter;
import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddTodoActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnDateChangedListener {

	private EditText name;
	private DatePicker datePicker;
	private Calendar c;
	private CheckBox isMonitored;
	private Button addTodo;

	private StringBuffer dateBuf;
	private String[] monitors = {};
	private List<User> friendList;
	private User my;

	private SharedPreferences account;
	private SharedPreferences.Editor editor;
	
	private DataTransporter dataTransporter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_todo);

		dataTransporter = new DataTransporter();
		name = (EditText) findViewById(R.id.add_todo_name);
		datePicker = (DatePicker) findViewById(R.id.add_todo_deadline_datapicker);
		isMonitored = (CheckBox) findViewById(R.id.add_todo_isMonitored);
		isMonitored.setOnCheckedChangeListener(this);
		addTodo = (Button) findViewById(R.id.add_todo);
		c = Calendar.getInstance();
		dateBuf = new StringBuffer();
		datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), this);
		addTodo.setOnClickListener(this);

		account = getSharedPreferences("account.xml",0);
		my = new User();
		my.setId(account.getInt("id", 0));
		my.setEmail(account.getString("email", null));
		my.setPassword(account.getString("password", null));
		//Intent myLocalIntent = getIntent();
		// Bundle myBundle = myLocalIntent.getExtras();
		//my = (User) myLocalIntent.getSerializableExtra("user");
		// get friend list
		friendList = new LinkedList<User>();
		new GetFriendListThread().execute();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.add_todo) {
			StringBuffer month = new StringBuffer();
			if(datePicker.getMonth()+1 < 10) {
				month.append("0");
				month.append(datePicker.getMonth() + 1);
			}
			
			dateBuf.append(String.valueOf(datePicker.getYear()) + "-"
					+ String.valueOf(month) + "-"
					+ String.valueOf(datePicker.getDayOfMonth()) + " 00:00:00");
			//notice(dateBuf.toString());
			Timestamp.valueOf(dateBuf.toString());
			if (!name.getText().toString().equals("") && !dateBuf.toString().equals("")) {
				Todo todo = dataTransporter.addTodo(name.getText().toString(), dateBuf.toString(), 0, 0, my.getId());
				for (int i =0; i < monitors.length; i++) {
					dataTransporter.addMonitor(todo.getId(), Integer.parseInt((monitors[i])));
				}
			}
			
			Intent intent = new Intent(AddTodoActivity.this, MainActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		// popup dia
		
		if (arg1) {
			Intent addMonitorIntent = new Intent(AddTodoActivity.this, AddMonitorActivity.class);
			addMonitorIntent.putExtra("friendList", (Serializable)friendList);
			startActivityForResult(addMonitorIntent, 0);
		}
	}

	private class GetFriendListThread extends
			AsyncTask<String, Void, List<User>> {

		int myId;

		@Override
		protected void onPreExecute() {
			myId = my.getId();
			//notice(myId + "");
		}

		@Override
		protected List<User> doInBackground(String... params) {

			try {
				List<User> myFriends = dataTransporter.getFriendList(myId);
				return myFriends;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<User> result) {
			if (result != null) {
				//notice(result.get(0).getId()+"333333");

				Iterator<User> it = result.iterator();
				while (it.hasNext()) {
					User temp = it.next();
					friendList.add(temp);
				}

			} else {
				//notice("get friend list failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(AddTodoActivity.this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		c.set(year, monthOfYear, dayOfMonth);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode) {
		case RESULT_OK:
			monitors = data.getStringArrayExtra("friendIds");
			Log.d("do", "" + monitors.length);
			if(monitors.length == 0) {
				isMonitored.setChecked(false);
			}
			for(int i = 0; i < monitors.length; i++) {
				
				if(!monitors[i].equals(" ")) {
					
					isMonitored.setChecked(true);
				} else {
					isMonitored.setChecked(false);
				}
			}
		}
	}
	
}
