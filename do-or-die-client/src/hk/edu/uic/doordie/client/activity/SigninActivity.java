package hk.edu.uic.doordie.client.activity;

import java.io.Serializable;
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
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {
	private EditText email;
	private EditText password;
	private Button signin;
	private DataTransporter dataTransporter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		email = (EditText) findViewById(R.id.signin_email);
		password = (EditText) findViewById(R.id.signin_password);
		signin = (Button) findViewById(R.id.signin_signin);
		dataTransporter = new DataTransporter();

		email.setOnFocusChangeListener(this);
		password.setOnFocusChangeListener(this);
		signin.setOnClickListener(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.signin_email && hasFocus && email.getText().toString().equals("Email")) {
			email.setText("");
		} else if (v.getId() == R.id.signin_email && !hasFocus && email.getText().toString().equals("")) {
				email.setText("Email");
		} else if (v.getId() == R.id.signin_password && hasFocus && password.getText().toString().equals("Password")) {
			password.setText("");
		} else if (v.getId() == R.id.signin_password && !hasFocus && password.getText().toString().equals("")) {
				password.setText("Password");
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.signin_signin:
			new SigninThread().execute();
			break;
		}
	}
	
	private class SigninThread extends AsyncTask<String, Void, Map<User, List<Todo>>> {
		String emailString;
		String passwordString;

		@Override
		protected void onPreExecute() {
			emailString = email.getText().toString();
			passwordString = password.getText().toString();
		}

		@Override
		protected Map<User, List<Todo>> doInBackground(String... params) {

			try {
				Map<User, List<Todo>> myInfoAndTodos = dataTransporter.signin(emailString, passwordString);
				return myInfoAndTodos;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Map<User, List<Todo>> result) {
			if (result != null) {
				//notice(result.toString());
				
				
				User user = null;
				List<Todo> todoList = null;
				
				Iterator<Entry<User, List<Todo>>> it = result.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry<User, List<Todo>> entry = it.next();
					user = entry.getKey();
					todoList = entry.getValue();
				}
				Intent intent = new Intent();
				Bundle myBundle = new Bundle();
				myBundle.putSerializable("user", user);
				myBundle.putSerializable("todoList", (Serializable) todoList);
				intent.putExtra("myInfoAndTodos", myBundle);
				intent.setClass(SigninActivity.this, AddTodoActivity.class);
				startActivity(intent);
			} else {
				notice("Signup failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(SigninActivity.this, str, Toast.LENGTH_SHORT).show();
	}
}
