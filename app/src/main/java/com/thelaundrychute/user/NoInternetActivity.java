package com.thelaundrychute.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.thelaundrychute.user.test.R;


public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance().setActivity(this);
        setContentView(R.layout.activity_no_internet);

    }

//    @Override
//    protected void onCreateView


    @Override
    public void onBackPressed() {
    }
}
