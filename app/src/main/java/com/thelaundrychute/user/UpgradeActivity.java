package com.thelaundrychute.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thelaundrychute.user.test.R;

/**
 * Created by jialuc on 7/1/16.
 */
public class UpgradeActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        Button doUpgrade = (Button) findViewById(R.id.upgradeButton);
        doUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "market://details?id=" + getPackageName();
                Intent localIntent = new Intent("android.intent.action.VIEW");
                localIntent.setData(Uri.parse(str));
                startActivity(localIntent);
            }
        });

    }

}
