package edu.uw.longt8.motionkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Leon on 5/18/2017.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView xAxis = (TextView) findViewById(R.id.xAxis);
        TextView yAxis = (TextView) findViewById(R.id.yAxis);
        TextView zAxis = (TextView) findViewById(R.id.zAxis);

        xAxis.setText("Move device along the X axis: " + new String(new int[]{0x1F602}, 0, 1));
        yAxis.setText("Move device along the Y axis: " + new String(new int[]{0x1F60A}, 0, 1));
        zAxis.setText("Move device along the Z axis: " + new String(new int[]{0x1F60C}, 0, 1));
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
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
