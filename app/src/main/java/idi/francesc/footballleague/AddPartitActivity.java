package idi.francesc.footballleague;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPartitActivity extends AppCompatActivity {

    Spinner locSpinner;
    Spinner visitSpinner;
    EditText goloc;
    EditText golvis;
    String dataPartit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        dataPartit = intent.getStringExtra("date");
        setContentView(R.layout.activity_add_partit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_partit);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Nou Partit");
        locSpinner = (Spinner) findViewById(R.id.local_spinner);
        visitSpinner = (Spinner) findViewById(R.id.visitant_spinner);
        goloc = (EditText) findViewById(R.id.partit_gols_local);
        golvis = (EditText) findViewById(R.id.partit_gols_visitant);
        String[] from = {EquipsContract.EquipEntry._ID, EquipsContract.EquipEntry.COLUMN_NAME_NOM};
        int[] to = {R.id.spinner_id, R.id.spinner_text};
        Cursor cursorSpin = DBHandler.getDbInstance(this).cursorSpin();
        cursorSpin.moveToFirst();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.spinner_item,cursorSpin,
                from, to);
        locSpinner.setAdapter(adapter);
        visitSpinner.setAdapter(adapter);
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
                guardar();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void guardar() {
        DBHandler instance = DBHandler.getDbInstance(this);
        TextView textId = (TextView) locSpinner.getSelectedView().findViewById(R.id.spinner_id);
        int idLoc = Integer.parseInt(textId.getText().toString());
        textId = (TextView) visitSpinner.getSelectedView().findViewById(R.id.spinner_id);
        int idVis = Integer.parseInt(textId.getText().toString());
        if(idLoc == idVis) {
            Toast.makeText(this, "Un equip no pot jugar contra si mateix", Toast.LENGTH_SHORT).show();
            return;
        }
        int golsLoc = Integer.parseInt(goloc.getText().toString());
        int golsVis = Integer.parseInt(golvis.getText().toString());
        Equip local = instance.getEquipById(idLoc);
        Equip visitant = instance.getEquipById(idVis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dataP = new Date(1970,1,1);
        try {
            dataP = format.parse(dataPartit);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Partit partit = new Partit(local.get_nom(), visitant.get_nom(), dataP, golsLoc, golsVis);
        Log.v(this.toString(), partit.get_Data().toString());
        if(golsLoc > golsVis) {
            local.addVictoria();
            visitant.addDerrota();
        }else if(golsVis > golsLoc) {
            visitant.addVictoria();
            local.addDerrota();
        }else {
            local.addEmpat();
            visitant.addEmpat();
        }
        local.incrementGolsFavor(golsLoc);
        local.incrementGolsContra(golsVis);
        visitant.incrementGolsFavor(golsVis);
        visitant.incrementGolsContra(golsLoc);
        instance.updateEquip(local);
        instance.updateEquip(visitant);
        instance.addPartit(partit);
        finish();
    }


}
