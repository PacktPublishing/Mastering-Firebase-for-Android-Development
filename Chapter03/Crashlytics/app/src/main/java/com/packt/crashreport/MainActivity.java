package com.packt.crashreport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private Button mCrashbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        mCrashbtn = findViewById(R.id.crashbtn);
        mCrashbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crashlytics.getInstance().crash(); // Force a crash

            }
        });


//        FirebaseCrash.report(new Exception("Firebase crash report is really handy"));
//
//        //Firebase log
//        FirebaseCrash.log("Firebase crash report is really handy");
//
//        //Firebase logcat
//        FirebaseCrash.logcat(1,"MainActivity", "Errors are not boring");
    }
}
