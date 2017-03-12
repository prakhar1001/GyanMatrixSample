package prakhar.com.gyanmatrixsample.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;

/**
 * Created by lendingkart on 3/12/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "PlayerDB";

    // Contacts table name
    private static final String TABLE_PLAYERS = "Player";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_TOTAL_SCORE = "total_score";
    private static final String KEY_MATCHES_PLAYED = "matches_played";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_DESCRIPTION = "description";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PLAYER = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT," + KEY_TOTAL_SCORE + " TEXT," + KEY_MATCHES_PLAYED + " TEXT," + KEY_COUNTRY + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_PLAYER);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addContact(CricketRecordModel.Record contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_IMAGE, contact.getImage()); // Contact Phone
        values.put(KEY_TOTAL_SCORE, contact.getTotalScore()); // Contact Name
        values.put(KEY_MATCHES_PLAYED, contact.getMatchesPlayed()); // Contact Phone
        values.put(KEY_COUNTRY, contact.getCountry()); // Contact Name
        values.put(KEY_DESCRIPTION, contact.getDescription()); // Contact Phone


        // Inserting Row
        db.insert(TABLE_PLAYERS, null, values);
        db.close(); // Closing database connection
    }
    // Getting All Contacts
    public ArrayList<CricketRecordModel.Record> getAllContacts() {
        ArrayList<CricketRecordModel.Record> contactList = new ArrayList<CricketRecordModel.Record>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CricketRecordModel.Record record = new CricketRecordModel.Record(null);

                record.setId(cursor.getString(0));
                record.setName(cursor.getString(1));
                record.setImage(cursor.getString(2));
                record.setTotalScore(cursor.getString(3));
                record.setMatchesPlayed(cursor.getString(4));
                record.setCountry(cursor.getString(5));
                record.setDescription(cursor.getString(6));
                // Adding contact to list
                contactList.add(record);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Getting All Contacts
    public ArrayList<CricketRecordModel.Record> getAllContactsWithName(String query) {
        ArrayList<CricketRecordModel.Record> contactList = new ArrayList<CricketRecordModel.Record>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS + " WHERE " + KEY_NAME + " LIKE " + "'" + "%" + query + "%" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CricketRecordModel.Record record = new CricketRecordModel.Record(null);

                record.setId(cursor.getString(0));
                record.setName(cursor.getString(1));
                record.setImage(cursor.getString(2));
                record.setTotalScore(cursor.getString(3));
                record.setMatchesPlayed(cursor.getString(4));
                record.setCountry(cursor.getString(5));
                record.setDescription(cursor.getString(6));
                // Adding contact to list
                contactList.add(record);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Deleting All
    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYERS, null, null);
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }


        // return count
        return count;
    }
}
