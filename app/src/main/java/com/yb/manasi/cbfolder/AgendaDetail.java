package com.yb.manasi.cbfolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AgendaDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        YbEvent ybEvent = intent.getParcelableExtra(MainActivity.YBEVENT_KEY);
        setTextForView(R.id.id_tvadStartTime, ybEvent.getStartLong());
        setTextForView(R.id.id_tvadEndTime, ybEvent.getEndLong());
        setTextForView(R.id.id_tvadSummary, ybEvent.m_strSummary);
        setTextForView(R.id.id_tvadLocation, ybEvent.m_strLocation);
        setTextForView(R.id.id_tvadDescription, ybEvent.m_strDescription);
        }

    private void setTextForView(int idTxtView, String text) {
        TextView tv = (TextView) findViewById(idTxtView);
        tv.setText(text);
        }
}
