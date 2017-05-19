package edu.uw.longt8.motionkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView xAxisInstruction = (TextView) findViewById(R.id.xAxis);
        TextView yAxisInstruction = (TextView) findViewById(R.id.yAxis);
        TextView zAxisInstruction = (TextView) findViewById(R.id.zAxis);
//        TextView txtBallMovingSpeed = (TextView) findViewById(R.id.ballMovingSpeed);

        xAxisInstruction.setText("Shake device along the x-axis to make " + new String(Character.toChars(0x1F618)));
        yAxisInstruction.setText("Shake device along the y-axis to make " + new String(Character.toChars(0x1F618)));
        zAxisInstruction.setText("Shake device along the z-axis to make " + new String(Character.toChars(0x1F618)));
//        txtBallMovingSpeed.setText("The current ball moving speed is " + ballMovingSpeed);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.menu_settings:

                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
