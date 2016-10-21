package ec.oleana.ubicacion.srv;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ddelacruz on 20/10/2016.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "/sdcard/oleana.db";
    public static final int VERSION = 1;

    public DataBase(Context context) {
        super(context, DataBase.DATABASE_NAME, null, DataBase.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder ubicacion = new StringBuilder();
        ubicacion.append("CREATE TABLE ubicacion ( ")
                .append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("fecha DATETIME DEFAULT CURRENT_TIMESTAMP, ")
                .append("latitud NUMERIC NOT NULL, ")
                .append("longitud NUMERIC NOT NULL)");

        db.execSQL(ubicacion.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        onCreate(db);
    }

    public boolean insertarUbicacion(Ubicacion ubicacion) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitud", ubicacion.getLatitud());
        contentValues.put("longitud", ubicacion.getLongitud());

        db.insertOrThrow("ubicacion", null, contentValues);
        return true;
    }
}
