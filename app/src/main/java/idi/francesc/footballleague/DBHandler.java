package idi.francesc.footballleague;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Francesc Lapedriza.
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "football.db";
    private static final String CREATE_EQUIPS = "CREATE TABLE " + EquipsContract.EquipEntry.TABLE_NAME +
            "(" + EquipsContract.EquipEntry.COLUMN_NAME_EQUIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EquipsContract.EquipEntry.COLUMN_NAME_NOM + " VARCHAR NOT NULL," +
            EquipsContract.EquipEntry.COLUMN_NAME_ESCUT + " BLOB NOT NULL," +
            EquipsContract.EquipEntry.COLUMN_NAME_GOLS_FAV + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_GOLS_CONTRA + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_DERROTES + " INTEGER," +
            EquipsContract.EquipEntry.COLUMN_NAME_EMPATS + " INTEGER);";
    private static final String CREATE_JUGADORS = "CREATE TABLE " + JugadorsContract.JugadorEntry.TABLE_NAME +
            "(" + JugadorsContract.JugadorEntry.COLUMN_NAME_JUGADOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_NOM + " VARCHAR NOT NULL," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_COGNOMS + " VARCHAR," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_GOLS + " INTEGER," +
            JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP + " TEXT," +
            "FOREIGN KEY (" + JugadorsContract.JugadorEntry.COLUMN_NAME_EQUIP + ") REFERENCES " +
            EquipsContract.EquipEntry.TABLE_NAME + "(" + EquipsContract.EquipEntry.COLUMN_NAME_NOM + "));";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EQUIPS);
        db.execSQL(CREATE_JUGADORS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EquipsContract.EquipEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + JugadorsContract.JugadorEntry.TABLE_NAME);
        onCreate(db);
    }
}
