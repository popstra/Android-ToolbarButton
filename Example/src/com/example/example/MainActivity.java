package com.example.example;

import java.util.Timer;
import java.util.TimerTask;

import toolbarelements.RefreshButton;
import toolbarelements.ToolBarButton;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
		toolbar.setTitle("Agenda");

		addButtons(toolbar);
	}

	ToolBarButton refreshButton;
	private void addButtons(Toolbar toolbar)
	{
		refreshButton = new RefreshButton(this, R.drawable.ic_action_refresh);
		refreshButton.addToToolbar(toolbar);
		refreshButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				refreshButton.startAnimation(refreshButton.rotation);
				Timer t = new Timer();
				t.schedule(new TimerTask()
				{
					public void run()
					{
						refreshButton.cancelAnimation();
					}
				}, 2000);
			}
		});
		
		ToolBarButton settingsButton = new ToolBarButton(this, R.drawable.ic_action_settings);
		settingsButton.addToToolbar(toolbar, Gravity.LEFT);
	}
}
