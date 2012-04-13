package hk.edu.uic.doordie.client.activity;

import hk.edu.uic.doordie.client.controller.DataTransporter;
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

public class SignupActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {
	private EditText email;
	private EditText password;
	private Button signup;
	private DataTransporter dataTransporter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		email = (EditText) findViewById(R.id.signup_email);
		password = (EditText) findViewById(R.id.signup_password);
		signup = (Button) findViewById(R.id.signup_signup);
		dataTransporter = new DataTransporter();

		email.setOnFocusChangeListener(this);
		password.setOnFocusChangeListener(this);
		signup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_signup:
			new SignupThread().execute();
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.signup_email && hasFocus && email.getText().toString().equals("Email")) {
			email.setText("");
		} else if (v.getId() == R.id.signup_email && !hasFocus && email.getText().toString().equals("")) {
				email.setText("Email");
		} else if (v.getId() == R.id.signup_password && hasFocus && password.getText().toString().equals("Password")) {
			password.setText("");
		} else if (v.getId() == R.id.signup_password && !hasFocus && password.getText().toString().equals("")) {
				password.setText("Password");
		}
	}
	
	private class SignupThread extends AsyncTask<String, Void, User> {
		String emailString;
		String passwordString;

		@Override
		protected void onPreExecute() {
			emailString = email.getText().toString();
			passwordString = password.getText().toString();
		}

		@Override
		protected User doInBackground(String... params) {

			try {
				User user = dataTransporter.signup(emailString, passwordString);
				return user;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(User result) {
			if (result != null) {
				notice(result.getId() + " " + result.getEmail());
				Intent intent = new Intent();

				intent.setClass(SignupActivity.this, LoginActivity.class);
				startActivity(intent);
			} else {
				notice("Signup failed!");
			}
		}
	}

	private void notice(String str) {
		Toast.makeText(SignupActivity.this, str, Toast.LENGTH_SHORT).show();
	}
}