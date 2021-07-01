package com.example.logicsquare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity {
	String user, lv;
	Button next, main;
	OnClickListener goMain, goNext;
	TextView score;
	ArrayList<User> userlist = new ArrayList<User>();
	
	native int nowScore(int lv);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		
		Intent intent = getIntent();
		user = intent.getStringExtra("user_name");
		lv = intent.getStringExtra("user_lv");
		lv = String.valueOf(Integer.valueOf(lv) + 1);
		
		next = (Button)findViewById(R.id.button1);
		main = (Button)findViewById(R.id.button2);
		score = (TextView)findViewById(R.id.textView2);
		score.setText("Score: " + String.valueOf(nowScore(Integer.valueOf(lv))));
		
		goMain = new OnClickListener() {
			public void onClick(View v){
				Intent intent = new Intent(EndActivity.this, MainActivity.class);
				startActivity(intent);
			}
		};
		
		goNext = new OnClickListener() {
			public void onClick(View v){
				if (lv.equals("6")){
					Intent intent = new Intent(EndActivity.this, MainActivity.class);
					startActivity(intent);
				}
				else {
				Intent intent = new Intent(EndActivity.this, GameActivity.class);
				intent.putExtra("user_name", user);
				intent.putExtra("user_lv", lv);
				startActivity(intent);
				}
			}
		};
		

		
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
		
		int flag = 0;
		for (int i = 0; i < cnt; i++){
			if (userlist.get(i).user.equals(user)){
				userlist.get(i).lv = lv;
				flag = 1;
			}
		}
		
		if (flag == 0){
			userlist.add(new User(user, lv));
			cnt++;
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < cnt; i++){
				writer.write(userlist.get(i).user+" "+userlist.get(i).lv+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		next.setOnClickListener(goNext);
		main.setOnClickListener(goMain);
	}
}
