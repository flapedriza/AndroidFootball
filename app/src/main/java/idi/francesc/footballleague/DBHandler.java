package idi.francesc.footballleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Francesc Lapedriza.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler dbInstance;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "football.db";
    private static final String CREATE_EQUIPS = "CREATE TABLE " + EquipsContract.EquipEntry.TABLE_NAME +
            "(" + EquipsContract.EquipEntry._ID + " INTEGER PRIMARY KEY," +
            EquipsContract.EquipEntry.COLUMN_NAME_NOM + " VARCHAR NOT NULL," +
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

    public static synchronized DBHandler getDbInstance(Context context) {
        if(dbInstance == null) {
            dbInstance = new DBHandler(context);
        }
        return dbInstance;
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    public void addEquip(Equip equip) {
        ContentValues values = new ContentValues();
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_NOM, equip.get_nom());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_ESCUT, equip.get_escut());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV, equip.get_gfavor());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA, equip.get_gcontra());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, equip.get_victories());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_EMPATS, equip.get_empats());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_DERROTES, equip.get_derrotes());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_PUNTS, equip.get_punts());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(EquipsContract.EquipEntry.TABLE_NAME,
                null,
                values);
    }

    public void addJugador(Jugador jugador) {
        ContentValues values = new ContentValues();
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_NOM, jugador.get_nom());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_COGNOMS, jugador.get_cognoms());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_GOLS, jugador.get_gols());
        values.put(JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP, jugador.get_equip());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(JugadorsContract.JugadorEntry.TABLE_NAME,
                null,
                values);
    }

    public void addPartit(Partit partit) {
        ContentValues values = new ContentValues();
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_DATA, partit.get_Data().toString());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL, partit.get_Local());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, partit.get_Visitant());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_LOCAL, partit.get_GolsLocal());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, partit.get_GolsVisitant());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(EquipsContract.EquipEntry.TABLE_NAME,
                null,
                values);
    }

    public void deleteEquip(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + EquipsContract.EquipEntry.TABLE_NAME + " WHERE " +
                EquipsContract.EquipEntry._ID + "=" + id);
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

    public void updateEquip(Equip oldEquip, Equip newEquip) {
        ContentValues values = new ContentValues();
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_NOM, newEquip.get_nom());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_ESCUT, newEquip.get_escut());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV, newEquip.get_gfavor());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA, newEquip.get_gcontra());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, newEquip.get_victories());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_EMPATS, newEquip.get_empats());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_DERROTES, newEquip.get_derrotes());
        values.put(EquipsContract.EquipEntry.COLUMN_NAME_PUNTS, newEquip.get_punts());
        SQLiteDatabase db = getWritableDatabase();
        db.update(EquipsContract.EquipEntry.TABLE_NAME, values,
                EquipsContract.EquipEntry._ID + "=" + oldEquip.get_id(), null);
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

    public void updatePartit(Partit oldPartit, Partit newPartit) {
        ContentValues values = new ContentValues();
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_DATA, newPartit.get_Data().toString());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL, newPartit.get_Local());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, newPartit.get_Visitant());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_LOCAL, newPartit.get_GolsLocal());
        values.put(PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, newPartit.get_GolsVisitant());
        SQLiteDatabase db = getWritableDatabase();
        db.update(PartitsContract.PartitsEntry.TABLE_NAME, values,
                JugadorsContract.JugadorEntry._ID + "=" + oldPartit.get_ID(), null);
    }

    public ArrayList<Equip> getAllEquips() {
        ArrayList<Equip> ret = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EquipsContract.EquipEntry.TABLE_NAME, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            Equip e = new Equip();
            e.set_id(c.getInt(c.getColumnIndex(EquipsContract.EquipEntry._ID)));
            e.set_nom(c.getString(c.getColumnIndex(EquipsContract.EquipEntry.COLUMN_NAME_NOM)));
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

    public ArrayList<Partit> getPartitsByDate(Date date) {
        ArrayList<Partit> ret = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(PartitsContract.PartitsEntry.TABLE_NAME, null, PartitsContract.PartitsEntry.COLUMN_NAME_DATA +
        "=" + date,null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
           //TODO buscar manera d'obtenir objectes equip
        }
        return ret;
    }

    //TODO obtenir tots els partits

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EquipsContract.EquipEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JugadorsContract.JugadorEntry.TABLE_NAME);
        onCreate(db);
    }
}
