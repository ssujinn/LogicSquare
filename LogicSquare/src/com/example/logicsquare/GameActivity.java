package com.example.logicsquare;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity{
	TextView levelView;
	TextView nameView;
	Button[] square = new Button[25];
	ImageView puzzle;
	final int[] btnId = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, 
			R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
			R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
			R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20,
			R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25};
	OnClickListener square_listener;
	int[] square_status = {
			0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0};
	String user;
	String lv;
	native void printDot(int[] square_status);
	native void printFnd(int lv);
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Intent intent = getIntent();
		user = intent.getStringExtra("user_name");
		lv = intent.getStringExtra("user_lv");
			System.loadLibrary("square-logic");
		
		levelView=(TextView)findViewById(R.id.textView2);
		nameView=(TextView)findViewById(R.id.textView3);
		puzzle = (ImageView)findViewById(R.id.imageView1);
		
		if (lv.equals("1")) 
			puzzle.setImageResource(R.drawable.puzzle_1);
		else if (lv.equals("2"))
			puzzle.setImageResource(R.drawable.puzzle_2);
		else if (lv.equals("3"))
			puzzle.setImageResource(R.drawable.puzzle_3);
		else if (lv.equals("4"))
			puzzle.setImageResource(R.drawable.puzzle_4);
		else if (lv.equals("5"))
			puzzle.setImageResource(R.drawable.puzzle_5);
		
		for (int i = 0; i < 25; i++) {
			square[i] = (Button)findViewById(btnId[i]);
		}

		printDot(square_status);
		printFnd(Integer.valueOf(lv));
		square_listener = new OnClickListener(){
			public void onClick(View v){
				for (int i = 0; i < 25; i++){
					if (v.getId() == square[i].getId()){
						if (square_status[i] == 0){
							square[i].setBackgroundColor(Color.BLACK);
							square_status[i] = 1;
							printDot(square_status);
						}
						else {
							square[i].setBackgroundColor(Color.WHITE);
							square_status[i] = 0;
							printDot(square_status);
						}
					}
				}
				if (lv.equals("1")){
					int[] answer = {0, 1, 1, 1, 0, 
							1, 1, 1, 1, 0, 
							1, 1, 1, 1, 0,
							0, 1, 1, 1, 1,
							1, 1, 1, 1, 1};
					if (Arrays.equals(square_status, answer)){
						Intent intent = new Intent(GameActivity.this, EndActivity.class);
						intent.putExtra("user_name", user);
						intent.putExtra("user_lv", lv);
						startActivity(intent);
					}
					
				}
				else if (lv.equals("2")){
					int[] answer = {
							1, 1, 1, 1, 1,
							1, 1, 1, 1, 1,
							1, 1, 1, 1, 1,
							0, 1, 1, 1, 0,
							0, 0, 1, 0, 0
					};
					if (Arrays.equals(square_status, answer)){
						Intent intent = new Intent(GameActivity.this, EndActivity.class);
						intent.putExtra("user_name", user);
						intent.putExtra("user_lv", lv);
						startActivity(intent);
					}
				}
				else if (lv.equals("3")){
					int[] answer = {
							1, 1, 1, 1, 0,
							0, 1, 1, 1, 1,
							1, 1, 0, 1, 0,
							1, 1, 1, 1, 1,
							1, 1, 1, 1, 0
					};
					if (Arrays.equals(square_status, answer)){
						Intent intent = new Intent(GameActivity.this, EndActivity.class);
						intent.putExtra("user_name", user);
						intent.putExtra("user_lv", lv);
						startActivity(intent);
					}
				}
				else if (lv.equals("4")){
					int[] answer = {
							1, 1, 1, 1, 1,
							1, 1, 0, 1, 1,
							0, 1, 1, 1, 0,
							0, 0, 1, 0, 0,
							1, 1, 1, 1, 1
					};
					if (Arrays.equals(square_status, answer)){
						Intent intent = new Intent(GameActivity.this, EndActivity.class);
						intent.putExtra("user_name", user);
						intent.putExtra("user_lv", lv);
						startActivity(intent);
					}
				}
				else if (lv.equals("5")){
					int[] answer = {
							0, 1, 1, 1, 0,
							0, 1, 0, 1, 1,
							0, 1, 1, 1, 1,
							1, 1, 1, 1, 1,
							1, 1, 1, 1, 1
					};
					if (Arrays.equals(square_status, answer)){
						Intent intent = new Intent(GameActivity.this, EndActivity.class);
						intent.putExtra("user_name", user);
						intent.putExtra("user_lv", lv);
						startActivity(intent);
					}
				}
			}
		};
		
		for (int i = 0; i < 25; i++) {
			square[i].setOnClickListener(square_listener);
		}
			
		levelView.setText("Level." + lv);
		nameView.setText("User: " + user);
		
	}
}
