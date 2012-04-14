package hk.edu.uic.doordie.client.activity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hk.edu.uic.doordie.client.controller.DataTransporter;
import hk.edu.uic.doordie.client.model.vo.Todo;
import hk.edu.uic.doordie.client.model.vo.User;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddTodoActivity extends Activity implements OnClickListener, OnCheckedChangeListener{
	
	private EditText name;
	private DatePicker datePicker;
	private Calendar calendar;
	private CheckBox isMonitored;
	private Button addTodo;
	
	private StringBuffer dateBuf;
	private String[] monitors;
	private List<User> friendList;
	private User my;
	
	private DataTransporter dataTransporter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_todo);
		
		dataTransporter = new DataTransporter();
		name = (EditText)findViewById(R.id.add_todo_name);
		datePicker = (DatePicker)findViewById(R.id.add_todo_deadline_datapicker);
		isMonitored = (CheckBox)findViewById(R.id.add_todo_isMonitored);
		addTodo = (Button)findViewById(R.id.add_todo);
		calendar = Calendar.getInstance();
		
		addTodo.setOnClickListener(this);
		
		Intent myLocalIntent = getIntent();
		Bundle myBundle = myLocalIntent.getExtras();
		my = (User)myBundle.getSerializable("user");
		// get friend list
		new GetFriendListThread().execute();
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// upload todo
		// update ui
		
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		// popup dia
	}
	
	private class GetFriendListThread extends AsyncTask<String, Void, List<User>> {

		int myId;
		@Override
		protected void onPreExecute() {
			myId = my.getId();
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
				notice(result.toString());

				Iterator<User> it = result.iterator();
				while(it.hasNext()) {
					User temp = it.next();
					friendList.add(temp);
				}
				
			} else {
				notice("get friend list failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(AddTodoActivity.this, str, Toast.LENGTH_SHORT).show();
	}
}
