package edu.uw.longt8.motionkey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Leon on 5/18/2017.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();

        SettingsFragment fragment = new SettingsFragment();

        ft.replace(android.R.id.content, fragment, "SettingsFragment");
        ft.commit();
    }
}
