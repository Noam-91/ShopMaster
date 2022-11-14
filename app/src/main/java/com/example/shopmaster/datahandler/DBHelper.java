package com.example.shopmaster.datahandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shopmaster.db";
    private static final String TABLE_NAME_GROC = "grocery";
    private static final String TABLE_NAME_CART = "cart";
    private static final String TABLE_NAME_HIST = "history";
    public static final String COLUMN_NAME = "name";
    public static final String _ID = "item_id";
    public static final String COLUMN_CATEGORY = "cate";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STORE = "store";
    public static final String COLUMN_IMGURL = "imgurl";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String  COLUMN_DATE = "date";
    private final String TAG = DBHelper.class.getSimpleName();

    private final String grocSQL;
    private final String histSQL;
    private final String cartSQL;

    public DBHelper(Context context) {
        // When user create/edit shopping NewListFragment
        super(context, DB_NAME, null, 1);
        // For first time use: copy asset database file to local.
        String databasePath = context.getDatabasePath(DB_NAME).getPath();
        if (!doesDatabaseExist(databasePath)) {
            copyDatabaseFromAssets(context,databasePath,DB_NAME);
        }

        cartSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_CART + "("
                + _ID + " INTEGER NOT NULL,"
                + COLUMN_NAME + " VARCHAR NOT NULL,"
                + COLUMN_CATEGORY + " VARCHAR NOT NULL,"
                + COLUMN_PRICE + " VARCHAR NOT NULL,"
                + COLUMN_STORE + " VARCHAR NOT NULL,"
                + COLUMN_IMGURL + " VARCHAR NOT NULL,"
                + COLUMN_QUANTITY + " INTEGER NOT NULL,"
                + COLUMN_DATE + " VARCHAR NOT NULL"+ ");";
        histSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_HIST +"("
                + _ID + " INTEGER NOT NULL,"
                + COLUMN_NAME + " VARCHAR NOT NULL,"
                + COLUMN_CATEGORY + " VARCHAR NOT NULL,"
                + COLUMN_PRICE + " VARCHAR NOT NULL,"
                + COLUMN_STORE + " VARCHAR NOT NULL,"
                + COLUMN_IMGURL + " VARCHAR NOT NULL,"
                + COLUMN_QUANTITY + " INTEGER NOT NULL,"
                + COLUMN_DATE + " VARCHAR NOT NULL"+ ");";
        grocSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_GROC + "("
                + _ID + " INTEGER NOT NULL,"
                + COLUMN_NAME + " VARCHAR NOT NULL,"
                + COLUMN_CATEGORY + " VARCHAR NOT NULL,"
                + COLUMN_PRICE + " VARCHAR NOT NULL,"
                + COLUMN_STORE + " VARCHAR NOT NULL,"
                + COLUMN_IMGURL + " VARCHAR NOT NULL,"
                + COLUMN_QUANTITY + " INTEGER NOT NULL,"
                + COLUMN_DATE + " VARCHAR NOT NULL"+ ");";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(grocSQL);
        Log.d(TAG, "Create/Open table 'grocery'.");
        db.execSQL(cartSQL);
        Log.d(TAG, "Create/Open table 'NewListFragment'.");
        db.execSQL(histSQL);
        Log.d(TAG, "Create/Open table 'history'.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Check if the db file has been copied to local.
     * @param databasepath: the path of db file in local.
     * @return boolean
     */
    private boolean doesDatabaseExist(String databasepath) {
        if (new File(databasepath).exists()) return true;
        if (Objects.requireNonNull(new File(databasepath).getParentFile()).exists()) return false;
        Objects.requireNonNull(new File(databasepath).getParentFile()).mkdirs();
        return false;
    }

    /**
     * Copy the db file from assets to local.
     * @param context: context
     * @param databasepath: the path of db file in local.
     * @param assetfilename: the path of db file in assets.
     */
    private void copyDatabaseFromAssets(Context context, String databasepath, String assetfilename) {
        int bSize = 4096, bytes;
        byte[] buffer = new byte[bSize];
        try {
            InputStream asset = context.getAssets().open("databases/"+assetfilename);
            FileOutputStream database = new FileOutputStream(databasepath);
            while((bytes = asset.read(buffer)) > 0) {
                database.write(buffer,0,bytes);
            }
            database.flush();
            database.close();
            asset.close();
            Log.d(TAG, "Asset DB file has been copied to local.");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error copy Asset File " + assetfilename + " to " + databasepath);
        }
    }
}