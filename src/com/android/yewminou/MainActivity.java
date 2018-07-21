package com.android.yewminou;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.lang.Process;
import java.lang.Runtime;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity {
	private LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setGravity(Gravity.CENTER);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 32, 0, 32);
		TextView textSaveLoc = new TextView(this);
		textSaveLoc.setLayoutParams(layoutParams);
		textSaveLoc.setPadding(16, 32, 16, 32);
		textSaveLoc.setText("Log files will be saved to internal storage:  /sdcard");
		TextView textLog = new TextView(this);
		textLog.setLayoutParams(layoutParams);
		textLog.setPadding(16, 32, 16, 32);
		Button makeLogButton = new Button(this);
		makeLogButton.setLayoutParams(layoutParams);
		makeLogButton.setPadding(32, 32, 32, 32);
		makeLogButton.setText("Create Log File");
		makeLogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					String timeStamp = simpleDateFormat.format(Calendar.getInstance().getTime());
					Runtime.getRuntime().exec(new String[] {"sh", "-c", "echo '========================================" +
							"\nManufacturer:  " + android.os.Build.MANUFACTURER +
							"\nDevice:  " + android.os.Build.DEVICE +
							"\nModel:  " + android.os.Build.MODEL +
							"\nProduct:  " + android.os.Build.PRODUCT +
							"\nKernel:  " + System.getProperty("os.version") +
							"\nOS Version:  " + android.os.Build.VERSION.RELEASE +
							"\nAPI:  " + android.os.Build.VERSION.SDK +
							"\n========================================' > /sdcard/ymlog-" + timeStamp + ".txt"});
					Runtime.getRuntime().exec(new String[] {"sh", "-c", "logcat -P ''; logcat -d -b all >> /sdcard/ymlog-" + timeStamp + ".txt"});
					textLog.setText("Successfully created log file:  ymlog-" + timeStamp + ".txt");
				} catch(Exception e) {
					textLog.setText("Failed to create log:  " + e.toString());
				}
			}
		});
		linearLayout.addView(textSaveLoc);
		linearLayout.addView(makeLogButton);
		linearLayout.addView(textLog);
		setContentView(linearLayout);
	}
}
