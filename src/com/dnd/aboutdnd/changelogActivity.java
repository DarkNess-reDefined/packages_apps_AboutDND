package com.dnd.aboutdnd;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class changelogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);

        TextView logText = (TextView) findViewById(R.id.logText);
        Bundle bundle = getIntent().getExtras();
        String changeLog = bundle.getString("log");
        logText.setText(changeLog);
    }
}
