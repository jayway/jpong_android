package com.jayway.pong;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mPlayButton;
    private Button mMatchHistoryButton;
    private Button mAboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonListeners();
    }

    private void setButtonListeners() {
        mPlayButton = (Button)findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(this);

        mMatchHistoryButton = (Button)findViewById(R.id.match_history_button);
        mMatchHistoryButton.setOnClickListener(this);

        mAboutButton = (Button)findViewById(R.id.about_button);
        mAboutButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mPlayButton) {
            goToPlay();
        } else if (v == mMatchHistoryButton) {
            goToMatchHistory();
        } else if (v == mAboutButton) {
            goToAbout();
        }
    }

    private void goToAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void goToMatchHistory() {
        Intent intent = new Intent(this, MatchHistoryActivity.class);
        startActivity(intent);
    }

    private void goToPlay() {

    }
}
