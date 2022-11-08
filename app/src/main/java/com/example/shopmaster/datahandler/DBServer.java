package com.example.shopmaster.datahandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBServer {
    private static final String TAG = DBServer.class.toString();
    private final DBHelper dbhelper;
    public DBServer(Context context)
    {
        this.dbhelper = new DBHelper(context);
    }

    /**
     * Add item into cart or history. If exist, increment the quantity.
     * @param entity:   item that needs to be added.
     * @param tableName:  three kinds of tables {'grocery','cart','history'}
     */
    @SuppressLint("Range")
    public void addItem(Grocery entity, String tableName)
    {
        if (tableName.equals("grocery")){
            throw new IllegalStateException("Grocery table should not be edited.");
        }
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        String[] arrayOfString = new String[2];
        arrayOfString[0] = String.valueOf(entity.getId());
        arrayOfString[1] = entity.getHistDate();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM "+tableName
                +" WHERE item_id=? AND date=?", arrayOfString);
        if (localCursor.getCount()>0){
            localCursor.moveToNext();
            ContentValues values = new ContentValues();
            values.put("quantity",localCursor.getInt(localCursor.getColumnIndex("quantity"))+1);
            localSQLiteDatabase.update("history",values,
                    "item_id=? and date=?",arrayOfString);
        }
        else
        {
            Object[] arrayOfObject = new Object[8];
            arrayOfObject[0] = entity.getId();
            arrayOfObject[1] = entity.getName();
            arrayOfObject[2] = entity.getCate();
            arrayOfObject[3] = entity.getPrice();
            arrayOfObject[4] = entity.getImgUrl();
            arrayOfObject[5] = entity.getStore();
            arrayOfObject[6] = entity.getQuantity();
            arrayOfObject[7] = entity.getHistDate();
            localSQLiteDatabase.execSQL("INSERT OR IGNORE INTO "+tableName+
                    "(item_id,name,cate,price,imgurl,store,quantity,date)"
                    +" VALUES(?,?,?,?,?,?,?,?)", arrayOfObject);
        }
        localSQLiteDatabase.close();
        localCursor.close();
    }


    /**
     * Find a grocery item by its unique id in the db table.
     * @param item_id:  Unique item id.
     * @param tableName:    three kinds of tables {'grocery','cart','history'}
     * @return : expected grocery item.
     */
    @SuppressLint("Range")
    public Grocery findItemById(Integer item_id, String date, String tableName)
    {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Grocery entity = new Grocery();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM "+tableName
                +" WHERE item_id=? AND date=?", new String[]{String.valueOf(item_id),date});
        Log.d(TAG,"Find by id in history: cursor declared");
        localCursor.moveToNext();
        Log.d(TAG,"Find by id in history: cursor size: "+localCursor.getCount());
        entity.setId(localCursor.getInt(localCursor.getColumnIndex("item_id")));
        entity.setName(localCursor.getString(localCursor.getColumnIndex("name")));
        entity.setCate(localCursor.getString(localCursor.getColumnIndex("cate")));
        entity.setPrice(localCursor.getString(localCursor.getColumnIndex("price")));
        entity.setStore(localCursor.getString(localCursor.getColumnIndex("store")));
        entity.setImgUrl(localCursor.getString(localCursor.getColumnIndex("imgurl")));
        entity.setQuantity(localCursor.getInt(localCursor.getColumnIndex("quantity")));
        entity.setHistDate(localCursor.getString(localCursor.getColumnIndex("date")));

        localSQLiteDatabase.close();
        localCursor.close();
        return entity;
    }

    /**
     * Find an grocery item in the grocery table by key word in item name.
     * @param partialName - key word in item name.
     * @return A list of grocery item that contains the key word.
     */
    @SuppressLint("Range")
    public List<Grocery> findItemByName(String partialName)
    {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        List<Grocery> localArrayList=new ArrayList<>();
        Log.d(TAG,"Find item by name: before cursor");
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT *  FROM grocery "
                +"WHERE name LIKE '%"+partialName+"%'",null );
        if (localCursor.getCount()==0){
            return localArrayList;
        }
        while (localCursor.moveToNext())
        {
            Grocery temp=new Grocery();
            temp.setId(localCursor.getInt(localCursor.getColumnIndex("item_id")));
            temp.setName(localCursor.getString(localCursor.getColumnIndex("name")));
            temp.setPrice(localCursor.getString(localCursor.getColumnIndex("price")));
            temp.setStore(localCursor.getString(localCursor.getColumnIndex("store")));
            temp.setImgUrl(localCursor.getString(localCursor.getColumnIndex("imgurl")));
            temp.setCate(localCursor.getString(localCursor.getColumnIndex("cate")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        localCursor.close();
        return localArrayList;
    }

    /**
     * Delete an item from the cart or history based on item_id and date.
     * @param entity:  item needs to be deleted.
     * @param tableName:    three kinds of tables {'grocery','cart','history'}
     */
    public void deleteItem(Grocery entity, String tableName)
    {
        if (tableName.equals("grocery")){
            throw new IllegalStateException("Grocery table should not be edited.");
        }
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = entity.getId();
        arrayOfObject[1] = entity.getHistDate();
        localSQLiteDatabase.execSQL("DELETE FROM "+tableName+" WHERE item_id=? AND date=?",
                arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * Update the quantity of an item.
     * @param entity:   item waits to be updated
     * @param tableName:    three kinds of tables {'grocery','cart','history'}
     * @param newQuantity:  Integer.
     */
    public void updateItemQuantity(Grocery entity, String tableName, Integer newQuantity)
    {
        if (Objects.equals(tableName, "grocery")){
            // grocery table should not be edited. We are not considering inventory.
            throw new IllegalStateException("Grocery table should not be edited.");
        }
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = newQuantity;
        arrayOfObject[1] = entity.getId();
        arrayOfObject[2] = entity.getHistDate();
        localSQLiteDatabase.execSQL("UPDATE "+tableName+" SET quantity=?" +
                "  WHERE item_id=? AND date=?", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * Find items by category. Be used to show items in categories.
     * @param cate: category name.
     * @param tableName:    three kinds of tables {'grocery','cart','history'}
     * @return A list of grocery item that belongs to the category.
     */
    @SuppressLint("Range")
    public List<Grocery> findItemsByCategoryInTable(String cate, String tableName)
    {
        List<Grocery> localArrayList=new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * from "+tableName
                +" WHERE cate=?", new String[]{cate});
        while (localCursor.moveToNext())
        {
            Grocery temp=new Grocery();
            temp.setId(localCursor.getInt(localCursor.getColumnIndex("item_id")));
            temp.setName(localCursor.getString(localCursor.getColumnIndex("name")));
            temp.setCate(localCursor.getString(localCursor.getColumnIndex("cate")));
            temp.setPrice(localCursor.getString(localCursor.getColumnIndex("price")));
            temp.setStore(localCursor.getString(localCursor.getColumnIndex("store")));
            temp.setImgUrl(localCursor.getString(localCursor.getColumnIndex("imgurl")));
            temp.setQuantity(localCursor.getInt(localCursor.getColumnIndex("quantity")));
            temp.setHistDate(localCursor.getString(localCursor.getColumnIndex("date")));

            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        localCursor.close();
        return localArrayList;
    }

    /**
     * Find all items in the table.
     * @param tableName:    three kinds of tables {'grocery','cart','history'}
     * @return A list of all the grocery items in the table.
     */
    @SuppressLint("Range")
    public List<Grocery> findAllItemsInTable(String tableName)
    {
        List<Grocery> localArrayList=new ArrayList<>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from "+tableName+" " +
                "where 1=1 ", null);
        while (localCursor.moveToNext())
        {
            Grocery temp=new Grocery();
            temp.setId(localCursor.getInt(localCursor.getColumnIndex("item_id")));
            temp.setName(localCursor.getString(localCursor.getColumnIndex("name")));
            temp.setCate(localCursor.getString(localCursor.getColumnIndex("cate")));
            temp.setPrice(localCursor.getString(localCursor.getColumnIndex("price")));
            temp.setStore(localCursor.getString(localCursor.getColumnIndex("store")));
            temp.setImgUrl(localCursor.getString(localCursor.getColumnIndex("imgurl")));
            temp.setQuantity(localCursor.getInt(localCursor.getColumnIndex("quantity")));
            temp.setHistDate(localCursor.getString(localCursor.getColumnIndex("date")));

            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        localCursor.close();
        return localArrayList;
    }

}


