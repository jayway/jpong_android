package com.jayway.pong;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MatchHistoryActivity extends ActionBarActivity {

    private MatchHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);

        List<MatchResult> results = new ArrayList<MatchResult>();
        results.add(new MatchResult("Pelle", "Olof", 3, 2));
        results.add(new MatchResult("Lisa", "Peter", 7, 5));
        results.add(new MatchResult("Johannes Olofsson", "Svenning Svenningsdotter", 0, 1));

        mAdapter = new MatchHistoryAdapter(this, R.layout.match_result_item, results);
        
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
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

    private static class MatchHistoryAdapter extends ArrayAdapter<MatchResult> {

        private final List<MatchResult> mResults;
        private HashMap<MatchResult, Integer> mIdMap = new HashMap<MatchResult, Integer>();

        public MatchHistoryAdapter(Context context, int resource, List<MatchResult> results) {
            super(context, resource, results);

            mResults = results;
            
            MatchResult item;
            for (int i = 0; i < results.size(); i++) {
                item = results.get(i);
                mIdMap.put(item, i);
            }
        }

        @Override
        public long getItemId(int position) {
            MatchResult item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.match_result_item, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.namePlayer1Label = (TextView) convertView.findViewById(R.id.player1Name);
                viewHolder.namePlayer2Label = (TextView) convertView.findViewById(R.id.player2Name);
                viewHolder.scoreLabel = (TextView) convertView.findViewById(R.id.score);
                convertView.setTag(viewHolder);
            }

            MatchResult result = mResults.get(position);
            ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.namePlayer1Label.setText(result.namePlayer1);
            holder.namePlayer2Label.setText(result.namePlayer2);
            holder.scoreLabel.setText(result.scorePlayer1 + " - " + result.scorePlayer2);

            return convertView;
        }

        private class ViewHolder {

            public TextView namePlayer1Label;
            public TextView namePlayer2Label;
            public TextView scoreLabel;
        }
    }
}
