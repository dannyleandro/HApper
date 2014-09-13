package com.moviles.mundo;

import java.util.Date;
import java.util.Hashtable;

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
		String crearTablaAlarmas = "CREATE TABLE alarmas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, fechaLanzamiento INTEGER, fechaCreacion INTEGER)";
 
        db.execSQL(crearTablaAlarmas);
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
    private static final String KEY_FECHA_LANZAMIENTO = "fechaLanzamiento";
    private static final String KEY_FECHA_CREACION = "fechaCreacion";
 
    private static final String[] COLUMNS = {KEY_ID, KEY_NOMBRE, KEY_DESCRIPCION, KEY_FECHA_LANZAMIENTO, KEY_FECHA_CREACION};
 
    public long addAlarma(String nomb, String desc, long fecLan, long fecCre)
    {
    	Log.d("addAlarma", "Se inserta una nueva fila con los campos Nombre:" + nomb + " Descripcion:" + desc + " FechaLanzamiento:" + fecLan + " FechaCreacion:" + fecCre);
 
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nomb);
        values.put(KEY_DESCRIPCION, desc);
        values.put(KEY_FECHA_LANZAMIENTO, fecLan);
        values.put(KEY_FECHA_CREACION, fecCre);
        
        Long id = db.insert(TABLA_ALARMAS, null, values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close();
        return id;
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
 
        // 4. build Alarma object
        Alarma alarma = new Alarma(id, cursor.getString(1), cursor.getString(2), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)));
        db.close();

        Log.d("getAlarma("+id+")", "Se obtiene la fila con los campos id:" + id + " Nombre:" + alarma.getNombre() + " Descripcion:" + alarma.getDescripcion() + " FechaLanzamiento:" + alarma.getFechaLanzamiento().toString() + " FechaCreacion:" + alarma.getFechaCreacion().toString());
    	
        // 5. return Alarma
        return alarma;
    }
 
     // Updating single book
    public void updateAlarma(int id, String nomb, String desc, long fecLan) 
    {
    	Log.d("updateAlarma("+id+")", "Se actualiza la fila con los campos  id:" + id + " Nombre:" + nomb + " Descripcion:" + desc + " FechaLanzamiento:" + fecLan);
    	
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nomb);
        values.put(KEY_DESCRIPCION, desc);
        values.put(KEY_FECHA_LANZAMIENTO, fecLan);
        
        // 3. updating row
        db.update(TABLA_ALARMAS, values, "id = ? ", new String[] { Integer.toString(id) } );
        
        // 4. close
        db.close(); 
    }
 
    // Deleting single book
    public void deleteAlarma(int id) 
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLA_ALARMAS, KEY_ID + " = ?", new String[] { Integer.toString(id) });
        
        // 3. close
        db.close();
 
        Log.d("deleteAlarma("+id+")", "Se elimina la fila con el número de id:" + id);
    }
    public Hashtable<Integer, Alarma> getAllAlarmas() 
    {
    	Hashtable<Integer, Alarma> alarmas = new Hashtable<Integer, Alarma>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLA_ALARMAS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Alarma al = null;
        if (cursor.moveToFirst()) 
        {
            do 
            {
                al = new Alarma(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)));
                alarmas.put(al.getId(), al);
            } 
            while (cursor.moveToNext());
        }
  
        Log.d("getAllAlarmas()", alarmas.toString());
  
        // return books
        return alarmas;
    }

}
