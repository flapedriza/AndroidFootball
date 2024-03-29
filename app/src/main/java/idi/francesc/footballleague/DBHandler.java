package idi.francesc.footballleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Francesc Lapedriza.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler dbInstance;
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "football.db";
    private static final String CREATE_EQUIPS = "CREATE TABLE " + EquipsContract.EquipEntry.TABLE_NAME +
            "(" + EquipsContract.EquipEntry._ID + " INTEGER PRIMARY KEY," +
            EquipsContract.EquipEntry.COLUMN_NAME_NOM + " VARCHAR NOT NULL," +
            EquipsContract.EquipEntry.COLUM_NAME_CIUTAT + " VARCHAR," +
            EquipsContract.EquipEntry.COLUMN_NAME_ESCUT + " BLOB," +
            EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_DERROTES + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_EMPATS + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_PUNTS + " INTEGER);";

    private static final String CREATE_JUGADORS = "CREATE TABLE " + JugadorsContract.JugadorEntry.TABLE_NAME +
            "(" + JugadorsContract.JugadorEntry._ID + " INTEGER PRIMARY KEY," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_NOM + " VARCHAR NOT NULL," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_COGNOMS + " VARCHAR," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_GOLS + " INTEGER," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP + " TEXT," +
            "FOREIGN KEY (" + JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP + ") REFERENCES " +
            EquipsContract.EquipEntry.TABLE_NAME + "(" + EquipsContract.EquipEntry.COLUMN_NAME_NOM + ") ON DELETE SET NULL" +
            " ON UPDATE CASCADE);";

    private static final String CREATE_PARTITS = "CREATE TABLE " + PartitsContract.PartitsEntry.TABLE_NAME +
            "(" + PartitsContract.PartitsEntry._ID + " INTEGER PRIMARY KEY," +
            PartitsContract.PartitsEntry.COLUMN_NAME_DATA + " DATE NOT NULL," +
            PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL + " VARCHAR NOT NULL," +
            PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT + " VARCHAR NOT NULL," +
            PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_LOCAL + " INTEGER," +
            PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_VISITANT + " INTEGER," +
            "FOREIGN KEY (" + PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL + ") REFERENCES " +
            EquipsContract.EquipEntry.TABLE_NAME + "(" + EquipsContract.EquipEntry.COLUMN_NAME_NOM + ") ON DELETE SET NULL " +
            "ON UPDATE CASCADE," +
            "FOREIGN KEY (" + PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT + ") REFERENCES " +
            EquipsContract.EquipEntry.TABLE_NAME + "("  + EquipsContract.EquipEntry.COLUMN_NAME_NOM + ") ON DELETE SET NULL " +
            "ON UPDATE CASCADE);";
    Context context;

    public void dropAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + EquipsContract.EquipEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JugadorsContract.JugadorEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PartitsContract.PartitsEntry.TABLE_NAME);
        onCreate(db);
    }

    public static synchronized DBHandler getDbInstance(Context context) {
        if(dbInstance == null) {
//            Toast.makeText(context, "Crear DB Instance", Toast.LENGTH_SHORT).show();
            dbInstance = new DBHandler(context);
        }
        return dbInstance;
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }


    public void addEquip(Equip equip) {
        ContentValues values = new ContentValues();
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_NOM, equip.get_nom());
        values.put(EquipsContract.EquipEntry.COLUM_NAME_CIUTAT, equip.get_ciutat());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_ESCUT, equip.get_escut());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV, 0);
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA, 0);
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, 0);
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_EMPATS, 0);
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_DERROTES, 0);
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_PUNTS, 0);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(EquipsContract.EquipEntry.TABLE_NAME,
                null,
                values);
    }

    public void addJugador(Jugador jugador) {
        ContentValues values = new ContentValues();
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_NOM, jugador.get_nom());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_COGNOMS, jugador.get_cognoms());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_GOLS, 0);
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP, jugador.get_equip());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(JugadorsContract.JugadorEntry.TABLE_NAME,
                null,
                values);
    }

    public void addPartit(Partit partit) {
        ContentValues values = new ContentValues();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String data = format.format(partit.get_Data());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_DATA, data);
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL, partit.get_Local());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, partit.get_Visitant());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_LOCAL, partit.get_GolsLocal());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_VISITANT, partit.get_GolsVisitant());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(PartitsContract.PartitsEntry.TABLE_NAME,
                null,
                values);
    }

    public void deleteEquip(int id) {
        SQLiteDatabase db = getWritableDatabase();
//        Toast.makeText(context, "Borrar equip " + id, Toast.LENGTH_LONG).show();
        db.execSQL("DELETE FROM " + EquipsContract.EquipEntry.TABLE_NAME + " WHERE " +
                EquipsContract.EquipEntry._ID + "="+id);
    }

    public void deleteJugador(String optn, int id) {
        SQLiteDatabase db = getWritableDatabase();
        if(optn == "KEEP_STATS") {
            ContentValues values = new ContentValues();
            values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP, "reserva");
            db.update(JugadorsContract.JugadorEntry.TABLE_NAME, values,
                    JugadorsContract.JugadorEntry._ID + "=" + id , null);
        }
        else db.execSQL("DELETE FROM " + JugadorsContract.JugadorEntry.TABLE_NAME + " WHERE " +
                JugadorsContract.JugadorEntry._ID + "=" + id);
    }

    public void deletePartit(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + PartitsContract.PartitsEntry.TABLE_NAME + "WHERE " +
                EquipsContract.EquipEntry._ID + "=" + id);
    }

    public void updateEquip(Equip newEquip) {
        ContentValues values = new ContentValues();
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_NOM, newEquip.get_nom());
        values.put(EquipsContract.EquipEntry.COLUM_NAME_CIUTAT, newEquip.get_ciutat());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_ESCUT, newEquip.get_escut());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV, newEquip.get_gfavor());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA, newEquip.get_gcontra());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, newEquip.get_victories());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_EMPATS, newEquip.get_empats());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_DERROTES, newEquip.get_derrotes());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_PUNTS, newEquip.get_punts());
        SQLiteDatabase db = getWritableDatabase();
        db.update(EquipsContract.EquipEntry.TABLE_NAME, values,
                EquipsContract.EquipEntry._ID + "=" + newEquip.get_id(), null);
    }

    public void updateJugador(Jugador oldJugador, Jugador newJugador) {
        ContentValues values = new ContentValues();
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_NOM, newJugador.get_nom());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_COGNOMS, newJugador.get_cognoms());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_GOLS, newJugador.get_gols());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP, newJugador.get_equip());
        SQLiteDatabase db = getWritableDatabase();
        db.update(JugadorsContract.JugadorEntry.TABLE_NAME, values,
                JugadorsContract.JugadorEntry._ID + "=" + oldJugador.get_id(), null);
    }

    /*public void updatePartit(Partit oldPartit, Partit newPartit) {
        ContentValues values = new ContentValues();
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_DATA, newPartit.get_Data().toString());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL, newPartit.get_Local());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, newPartit.get_Visitant());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_LOCAL, newPartit.get_GolsLocal());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, newPartit.get_GolsVisitant());
        SQLiteDatabase db = getWritableDatabase();
        db.update(PartitsContract.PartitsEntry.TABLE_NAME, values,
                JugadorsContract.JugadorEntry._ID + "=" + oldPartit.get_ID(), null);
    }*/

    public ArrayList<Equip> getAllEquips() {
        ArrayList<Equip> ret = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EquipsContract.EquipEntry.TABLE_NAME, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            Equip e = new Equip();
            e.set_id(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry._ID)));
            e.set_nom(c.getString(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_NOM)));
            e.set_ciutat(c.getString(c.getColumnIndex(EquipsContract.EquipEntry.COLUM_NAME_CIUTAT)));
            e.set_escut(c.getBlob(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_ESCUT)));
            e.set_gfavor(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV)));
            e.set_gcontra(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA)));
            e.set_victories(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES)));
            e.set_derrotes(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_DERROTES)));
            e.set_empats(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_EMPATS)));
            int queryPunts = c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_PUNTS));
            if(e.get_punts() != queryPunts) {
                Log.e(this.toString(), "ELS PUNTS NO S'HAN CALCULAT CORRECTAMENT!!");
                e.set_punts(queryPunts);
            }
            ret.add(e);
            c.moveToNext();

        }
        c.close();
        db.close();
        return ret;
    }

    public int getNombreEquips() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) (long) DatabaseUtils.queryNumEntries(db, EquipsContract.EquipEntry.TABLE_NAME);
    }

    public Equip getEquipById(int id) {
        Equip equip = new Equip();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EquipsContract.EquipEntry.TABLE_NAME + " WHERE " +
        EquipsContract.EquipEntry._ID + " = " + id, null);
        if (c.moveToFirst()) {
            equip.set_id(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry._ID)));
            equip.set_nom(c.getString(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_NOM)));
            equip.set_ciutat(c.getString(c.getColumnIndex(EquipsContract.EquipEntry.COLUM_NAME_CIUTAT)));
            equip.set_escut(c.getBlob(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_ESCUT)));
            equip.set_gfavor(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV)));
            equip.set_gcontra(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA)));
            equip.set_victories(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES)));
            equip.set_derrotes(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_DERROTES)));
            equip.set_empats(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_EMPATS)));
            equip.set_punts(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_PUNTS)));
        }
        c.close();
        db.close();
        return equip;

    }

    public Cursor cursorClassificacio() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EquipsContract.EquipEntry.TABLE_NAME + " ORDER BY " +
                EquipsContract.EquipEntry.COLUMN_NAME_PUNTS + " DESC, " + EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV + " DESC," +
                EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA + " ASC", null);
        if(c != null) c.moveToFirst();
        return c;

    }

    public Cursor cursorSpin() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EquipsContract.EquipEntry.TABLE_NAME + " ORDER BY " +
                EquipsContract.EquipEntry.COLUMN_NAME_NOM + " ASC", null);
        if(c != null) c.moveToFirst();
        return c;

    }


    public ArrayList<Jugador> getAllJugadorsEquip(String equip) {
        ArrayList<Jugador> ret = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + JugadorsContract.JugadorEntry.TABLE_NAME + " WHERE " +
                JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP + "=\"" + equip + "\"", null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            Jugador j = new Jugador();
            j.set_id(c.getInt(c.getColumnIndex(JugadorsContract.JugadorEntry._ID)));
            j.set_nom(c.getString(c.getColumnIndex(JugadorsContract.JugadorEntry.COLUMN_NAME_NOM)));
            j.set_cognoms(c.getString(c.getColumnIndex(JugadorsContract.JugadorEntry.COLUMN_NAME_COGNOMS)));
            j.set_gols(c.getInt(c.getColumnIndex(JugadorsContract.JugadorEntry.COLUMN_NAME_GOLS)));
            j.set_equip(c.getString(c.getColumnIndex(JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP)));
            ret.add(j);
            c.moveToNext();

        }
        c.close();
        db.close();
        return ret;
    }

    public Cursor cursorPartits() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + PartitsContract.PartitsEntry.TABLE_NAME +
                " ORDER BY " + PartitsContract.PartitsEntry.COLUMN_NAME_DATA + " DESC";
        Log.v(this.toString(), query);
        Cursor c = db.rawQuery(query, null);
        if(c != null) c.moveToFirst();
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Toast.makeText(context ,(CharSequence) "CREADA DB", Toast.LENGTH_LONG).show();
        Log.v(this.toString(), CREATE_EQUIPS);
        db.execSQL(CREATE_EQUIPS);
        Log.v(this.toString(), "create equips");
        db.execSQL(CREATE_JUGADORS);
        Log.v(this.toString(), "create jugadors");
        db.execSQL(CREATE_PARTITS);
        Log.v(this.toString(), "create partits");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EquipsContract.EquipEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JugadorsContract.JugadorEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PartitsContract.PartitsEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EquipsContract.EquipEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JugadorsContract.JugadorEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PartitsContract.PartitsEntry.TABLE_NAME);
        onCreate(db);
    }
}
