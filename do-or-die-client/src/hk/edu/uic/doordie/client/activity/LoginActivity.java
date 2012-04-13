package hk.edu.uic.doordie.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private Button signup;
	private Button signin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        signup = (Button)findViewById(R.id.signup_button);
        signup.setOnClickListener(this);
        signin = (Button)findViewById(R.id.signin_button);
        signin.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.signin_button:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, SigninActivity.class);
			startActivity(intent);
			break;
		case R.id.signup_button:
			Intent intent2 = new Intent();
			intent2.setClass(LoginActivity.this, SignupActivity.class);
			startActivity(intent2);
			break;
		}
	}
}