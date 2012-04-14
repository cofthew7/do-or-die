package hk.edu.uic.doordie.client.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		//Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, MyTodosActivity.class);
		RelativeLayout myTodosTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.minitab, null);
		TextView myTodosLabel = (TextView) myTodosTab.findViewById(R.id.tab_label);
		myTodosLabel.setText("My Todos");
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("MyTodos")
				.setIndicator(myTodosTab).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, MonitoringTodosActivity.class);
		RelativeLayout monitoringTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.minitab, null);
		TextView monitoringLabel = (TextView)monitoringTab.findViewById(R.id.tab_label);
		monitoringLabel.setText("Monitoring");
		
		spec = tabHost
				.newTabSpec("Monitoring")
				.setIndicator(monitoringTab).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, UnknownUsersActivity.class);
		RelativeLayout userTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.minitab, null);
		TextView userLabel = (TextView)userTab.findViewById(R.id.tab_label);
		userLabel.setText("Users");
		
		spec = tabHost
				.newTabSpec("Users")
				.setIndicator(userTab).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}
