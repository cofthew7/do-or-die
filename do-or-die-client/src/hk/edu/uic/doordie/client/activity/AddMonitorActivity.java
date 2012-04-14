package hk.edu.uic.doordie.client.activity;

import hk.edu.uic.doordie.client.model.vo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class AddMonitorActivity extends Activity implements OnClickListener{

	private ListView lv;
	private SimpleAdapter friendListAdapter;
	private ArrayList<HashMap<String, Object>> friendArrayList;
	
	private Intent myLocalIntent;
	private List<User> friendList;
	
	private ArrayList<String> selectedFriends;
	private StringBuffer sb;
	
	private Button ok;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_monitor);
		
		lv = (ListView)findViewById(R.id.add_monitor_listview);
		friendArrayList = new ArrayList<HashMap<String, Object>>();
		selectedFriends = new ArrayList<String>();
		sb = new StringBuffer();
		ok = (Button)findViewById(R.id.add_monitor);
		ok.setOnClickListener(this);
		
		myLocalIntent = getIntent();
		friendList = (List<User>) myLocalIntent.getSerializableExtra("friendList");
		
		for(int i = 0; i < friendList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			User user = friendList.get(i);
			
			map.put("email", user.getEmail());
			map.put("id", user.getId());
			
			Log.d("do", user.getEmail());
			Log.d("do", String.valueOf(user.getId()));
			friendArrayList.add(map);
		}
		
		friendListAdapter = new SimpleAdapter(this, friendArrayList, R.layout.friend, 
				new String[]{"id", "email"},
				new int[]{R.id.friend_id, R.id.friend_email});
		OnItemClickListener listener = new OnItemClickListener() {
			HashMap<String, Object> map;
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				map = (HashMap<String, Object>)lv.getItemAtPosition(arg2);
				Log.d("do", String.valueOf(map.get("id")));
				if (!isSelected(map.get("id").toString(), sb.toString().split(" "))) {
					arg1.setSelected(true);
					arg1.setBackgroundColor(Color.GRAY);
					sb.append(map.get("id") + " ");
				} else {
					arg1.setSelected(false);
					arg1.setBackgroundColor(Color.WHITE);
					String[] temp = sb.toString().split(" ");
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].equals(map.get("id").toString())) {
							temp[i] = "";
						}
					}
					sb = new StringBuffer();
					
					for (int i = 0; i < temp.length; i++) {
						sb.append(temp[i] + " ");
					}
				}
				
				Log.d("do", sb.toString());
			}
		
		};
		lv.setOnItemClickListener(listener);
		lv.setAdapter(friendListAdapter);
	}
	
	public boolean isSelected(String id, String[] selected) {
		for (int i = 0; i < selected.length; i++) {
			if(id.equals(selected[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.add_monitor) {
			myLocalIntent.putExtra("friendIds", sb.toString().split(" "));
			setResult(Activity.RESULT_OK, myLocalIntent);
			finish();
		}
		
	}
}
