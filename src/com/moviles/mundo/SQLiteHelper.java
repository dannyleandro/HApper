package com.moviles.mundo;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper
{
    /**
     * Version de la base de datos
     */	
    private static final int DATABASE_VERSION = 1;
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "HApperDB";
	
    /**
     * Constructor de la clase asistente de la base de datos
     * @param context
     * @param name
     * @param factory
     * @param version
     */
	public SQLiteHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String CREATE_BOOK_TABLE = "CREATE TABLE alarmas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT )";
 
        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS alarmas");
		 
        this.onCreate(db);	
	}
	
    /**
     * CRUD operaciones alarmas
     */
	// Books table name
    private static final String TABLA_ALARMAS = "alarmas";
 
    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DESCRIPCION = "descripcion";
 
    private static final String[] COLUMNS = {KEY_ID, KEY_NOMBRE, KEY_DESCRIPCION};
 
    public void addAlarma(Alarma al)
    {
    	Log.d("addAlarma", al.toString());
 
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, al.getNombre());
        values.put(KEY_DESCRIPCION, al.getDescripcion());
 
       db.insert(TABLA_ALARMAS, null, values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close();
    }
 
    public Alarma getAlarma(int id)
    {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = db.query(TABLA_ALARMAS, COLUMNS, " id = ?",
                
        		new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        Alarma alarma = new Alarma(cursor.getString(1), cursor.getString(2), new Date()); //Completar!!!!!!!!!!!
        
        Log.d("getBook("+id+")", alarma.toString());
 
        // 5. return book
        return alarma;
    }
 
     // Updating single book
    public int updateAlarma(Alarma alarma) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, alarma.getNombre()); // get title
        values.put(KEY_DESCRIPCION, alarma.getDescripcion()); // get author
 
        // 3. updating row
        int i = db.update(TABLA_ALARMAS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(0) }); //selection args mmmmm!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO completar
        // 4. close
        db.close();
        return i;
 
    }
 
    // Deleting single book
    public void deleteAlarma(Alarma alarma) 
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLA_ALARMAS, KEY_ID+" = ?", new String[] { String.valueOf(0) });
        //TODO completar
        
        // 3. close
        db.close();
 
        Log.d("deleteBook", alarma.toString());
 
    }

}
