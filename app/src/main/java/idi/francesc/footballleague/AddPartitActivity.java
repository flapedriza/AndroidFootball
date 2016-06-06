package idi.francesc.footballleague;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

public class AddPartitActivity extends AppCompatActivity {

    Spinner locSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String data = intent.getStringExtra("date");
        setContentView(R.layout.activity_add_partit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_partit);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Nou Partit");
        //TextView dataview = (TextView) findViewById(R.id.data);
        //dataview.setText(data);
        locSpinner = (Spinner) findViewById(R.id.local_spinner);
        String[] from = {EquipsContract.EquipEntry._ID, EquipsContract.EquipEntry.COLUMN_NAME_NOM};
        int[] to = {R.id.spinner_id, R.id.spinner_text};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.spinner_item, DBHandler.getDbInstance(this)
        .cursorClassificacio(), from, to);
        locSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_equip, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
