package com.thelaundrychute.user;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thelaundrychute.user.test.R;

public class Auth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = "XYwmFJEvOEa%2FzLWWntqGAQ%3D%3DrDrsH8wJnE%2B%2Fk3moiMf%2F5Q%3D%3D";
        setContentView(R.layout.activity_auth);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.content_actionbar);

    }
}