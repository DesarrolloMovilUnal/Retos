package co.edu.unal.myapplicationsqlite.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import co.edu.unal.myapplicationsqlite.Model.ContactModel;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID ="id";
    public static final String CONTACTS_ENTERPRISE_NAME = "enterprise";
    public static final String CONTACTS_URL = "url";
    public static final String CONTACTS_PHONE = "phone";
    public static final String CONTACTS_EMAIL = "email";
    public static final String CONTACTS_PROD_SERVICES = "products";
    public static final String CONTACTS_CLASSIFICATION = "classification";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts"+
                " (id integer primary key, enterprise text, url text, phone text, email text, products text, classification text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(ContactModel contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_ENTERPRISE_NAME,contact.getEnterprise());
        contentValues.put(CONTACTS_URL,contact.getURL());
        contentValues.put(CONTACTS_PHONE,contact.getPhoneNumber());
        contentValues.put(CONTACTS_EMAIL,contact.getEmail());
        contentValues.put(CONTACTS_PROD_SERVICES,contact.getProductsNServices());
        contentValues.put(CONTACTS_CLASSIFICATION,contact.getClassification().toString());
        db.insert(CONTACTS_TABLE_NAME,null,contentValues);
        return true;
    }
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id="+id,null);
        return res;
    }
    public ArrayList<String>  getDataByName(String name){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where enterprise='"+name+"'",null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(CONTACTS_ENTERPRISE_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
    public ArrayList<String> getDataByClass(String classification){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where classification='"+classification+"'",null);

        res.moveToFirst();
        while (res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(CONTACTS_ENTERPRISE_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, ContactModel contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_ENTERPRISE_NAME,contact.getEnterprise());
        contentValues.put(CONTACTS_URL,contact.getURL());
        contentValues.put(CONTACTS_PHONE,contact.getPhoneNumber());
        contentValues.put(CONTACTS_EMAIL,contact.getEmail());
        contentValues.put(CONTACTS_PROD_SERVICES,contact.getProductsNServices());
        contentValues.put(CONTACTS_CLASSIFICATION,contact.getClassification().toString());
        db.update(CONTACTS_TABLE_NAME,contentValues,"id = ?", new String[] {Integer.toString(id)});
        return true;
    }

    public Integer deleteContact (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONTACTS_TABLE_NAME,"id = ?", new String[] {Integer.toString(id)});
    }

    public ArrayList<String> getAllContacts(){
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts",null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(CONTACTS_ENTERPRISE_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
}
