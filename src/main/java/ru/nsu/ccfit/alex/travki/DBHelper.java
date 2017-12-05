package ru.nsu.ccfit.alex.travki;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexandra on 12.10.17.
 */

public class DBHelper extends SQLiteOpenHelper{
    String DB_PATH = null;
    private static String DB_NAME = "plants";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    Cursor mquery(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    /*Cursor rawQuery(String what, String from, String where, String[] args){
        return getReadableDatabase().rawQuery("select "+what+" from "+from+" where "+where, args);
    }

    public String[] getListElems(String what, String from, String where, String[] args){
        List<String> lst = new ArrayList<>();
        lst.add("не известно");

        Cursor c = rawQuery(what, from, where, args);

        c.moveToFirst();
        while(! c.isAfterLast()){
            lst.add(c.getString(0));
            c.moveToNext();
        }
        return lst.toArray(new String[0]);
    }*/

    public String[] createListElems(String tab_name, String[] fields ){
        List<String> lst = new ArrayList<>();
        lst.add("не известно");

        //"words", new String[] {"_id", "name"}, "_id = ?", new String[] {Integer.toString(num)}, null, null, null);
        Cursor c = mquery(tab_name, fields, null, null, null, null, null);

        c.moveToFirst();
        while(! c.isAfterLast()){
            lst.add(c.getString(0));
            c.moveToNext();
        }
        return lst.toArray(new String[0]);
    }

    public Map<String, Integer> createHMElems(String tab_name, String[] fields, Integer[] ids){
        Map<String, Integer> hm = new LinkedHashMap<>();
        hm.put("не известно", null);

        //"words", new String[] {"_id", "name"}, "_id = ?", new String[] {Integer.toString(num)}, null, null, null);
        Cursor c = mquery(tab_name, fields, null, null, null, null, null);

        int i = 0;
        c.moveToFirst();
        while(i < ids.length && ! c.isAfterLast()){
            hm.put(c.getString(0), ids[i]);
            ++i;
            c.moveToNext();
        }
        return hm;
    }
}
