package idi.francesc.footballleague;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditEquipActivity extends AppCompatActivity {
    private Equip equip;
    private DBHandler dbInstance;
    private Context context = AddEditEquipActivity.this;
    private Boolean edit;
    private int idE;
    EditText editNom;
    EditText editCiutat;
    private static final int SELECT_PICTURE = 100;
    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_equip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editEquip);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        edit = intent.getBooleanExtra("edit", false);
        idE = intent.getIntExtra("id", 0);
        dbInstance = DBHandler.getDbInstance(context);
        imageButton = (ImageView) findViewById(R.id.escut_select);
        editNom = (EditText) findViewById(R.id.edit_nom_equip);
        editCiutat = (EditText) findViewById(R.id.edit_ciutat_equip);
        if(edit) {
            equip = dbInstance.getEquipById(idE);
            if(equip.get_nom() == null) {
                Log.e(this.toString(), "No s'ha trobat el equip a la bd!");
                Toast.makeText(context, "Equip no trobat", Toast.LENGTH_SHORT).show();
                finish();
            }
            setTitle(equip.get_nom());
            editNom.setText(equip.get_nom());
            editCiutat.setText(equip.get_ciutat());
            findViewById(R.id.dades_edit).setVisibility(View.VISIBLE);
            TextView gfavor = (TextView) findViewById(R.id.dades_favor);
            TextView gcontra = (TextView) findViewById(R.id.dades_contra);
            TextView vict = (TextView) findViewById(R.id.dades_victories);
            TextView derr = (TextView) findViewById(R.id.dades_derrotes);
            TextView empats = (TextView) findViewById(R.id.dades_empats);
            TextView punts = (TextView) findViewById(R.id.dades_punts);
            gfavor.setText("Gols a favor: "+equip.get_gfavor());
            gcontra.setText("Gols en contra: "+equip.get_gcontra());
            vict.setText("Victories: "+equip.get_victories());
            derr.setText("Derrotes: "+equip.get_derrotes());
            empats.setText("Empats: "+equip.get_empats());
            punts.setText("Punts: "+equip.get_punts());


        }
        else {
            setTitle("Nou equip");
            equip = new Equip();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_equip, menu);
        if(!edit) menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                guardar();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(context)
                        .setTitle("Eliminar")
                        .setMessage("Segur que voleu eliminar aquest equip?")
                        .setPositiveButton("si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                borrar();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null) {
                    String path = getPathFromUri(selectedImageUri);
                    imageButton.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void guardar() {
        equip.set_nom( editNom.getText().toString());
        equip.set_ciutat(editCiutat.getText().toString());

        if(equip != null && !"".equals(equip.get_nom()) && !"".equals(equip.get_ciutat())) {
            if(edit) {
                dbInstance.updateEquip(equip);
                finish();
            }
            else {
                dbInstance.addEquip(equip);
                finish();
            }
        }
        else {
            Toast.makeText(context, "Introduïu totes les dades abans de desar l'equip", Toast.LENGTH_SHORT).show();

        }
    }

    private void borrar() {
        if (equip.get_id() != 0) {
            dbInstance.deleteEquip(equip.get_id());
            finish();
        }
        else finish();
    }

    public void selectImage(View view) {
        imageButton = (ImageView) view;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imatge"), SELECT_PICTURE);
    }

    private String getPathFromUri(Uri uri) {
        String ret = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()) {
            ret = cursor.getString(cursor.getColumnIndex(proj[0]));
        }
        cursor.close();
        return ret;
    }
}
