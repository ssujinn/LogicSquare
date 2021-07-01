package com.example.logicsquare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


class User {
	public String user;
	public String lv;
	
	User() {
		user = "";
		lv = "";
	}
	
	User(String u, String l) {
		user = u;
		lv = l;
	}
}

public class MainActivity extends Activity{
	
	EditText data;
	TextView tv;
	Button btn;
	OnClickListener ltn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		data=(EditText)findViewById(R.id.editText1);
		tv=(TextView)findViewById(R.id.textView1);
		Button btn=(Button)findViewById(R.id.button1);
		ltn=new OnClickListener(){
			public void onClick(View v){
				String newUser = data.getText().toString();
				ArrayList<User> userlist = new ArrayList<User>();
				
				int cnt = 0;
				File file = new File("/data/local/tmp/user-info.txt");
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String s = "";
					
					while ((s = reader.readLine()) != null){
						String[] u = s.split(" ");
						userlist.add(new User(u[0], u[1]));
						cnt++;
					}
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int i = 0;
				String userLv = "0";
				while (i < cnt){
					if (userlist.get(i).user.equals(newUser)){
						userLv = userlist.get(i).lv;
						
					}
					i++;
				}
				
				if (userLv.equals("0")){
					userlist.add(new User(newUser, "1"));
					userLv = "1";
				}
				
				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				intent.putExtra("user_name", newUser);
				intent.putExtra("user_lv", userLv);
				startActivity(intent);
				
			}
		};
		btn.setOnClickListener(ltn);
		
	}
}
