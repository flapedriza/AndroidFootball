package idi.francesc.footballleague;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddEditEquipActivity extends AppCompatActivity {
    private Equip equip;
    private DBHandler dbInstance;
    private Context context = AddEditEquipActivity.this;
    private Boolean edit;
    private int idE;
    private static final int SELECT_PICTURE = 100;
    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        edit = intent.getBooleanExtra("edit", false);
        idE = intent.getIntExtra("id", 0);
        dbInstance = DBHandler.getDbInstance(context);
        if(edit) {
            equip = dbInstance.getEquipById(idE);
            if(equip.get_nom() == null) {
                Log.e(this.toString(), "No s'ha trobat el equip a la bd!");
                Toast.makeText(context, "Equip no trobat", Toast.LENGTH_SHORT).show();
                finish();
            }
            setTitle(equip.get_nom());
            imageButton.setImageBitmap(BitmapFactory.decodeByteArray(equip.get_escut(), 0, equip.get_escut().length));
//            Toast.makeText(context, "Nom: " + equip.get_nom() + " ID: " + equip.get_id() + " Punts: " +
//            equip.get_punts(), Toast.LENGTH_LONG).show();

        }
        else {
            setTitle("Nou equip");
            equip = new Equip();
        }
        imageButton = (ImageView) findViewById(R.id.escut_select);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_equip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editEquip);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabok);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
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
        if (equip != null && equip.get_nom() != null && equip.get_id() == -1) {
            dbInstance.addEquip(equip);
            Log.v(this.toString(), "Equip creat");
            finish();
        }
        else if (equip != null && equip.get_nom() != null && equip.get_id() != -1) {
            equip.set_punts(-5);
            dbInstance.updateEquip(equip);
            Log.v(this.toString(), "Equip editat");
            finish();
        }
        else {
            Log.e(this.toString(), "No es pot guardar l'equip, problema de dades");
            Toast.makeText(context, "Introduïu totes les dades abans de guardar", Toast.LENGTH_SHORT).show();
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
