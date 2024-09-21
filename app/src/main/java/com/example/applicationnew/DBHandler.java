package com.example.applicationnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SERVICES = "services";
    private static final String TABLE_TIME_SLOTS = "time_slots";
    private static final String TABLE_BOOKINGS = "bookings";

    // Users Table Columns
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    // Services Table Columns
    private static final String SERVICE_ID = "service_id";
    private static final String SERVICE_NAME = "service_name";
    private static final String SERVICE_DESCRIPTION = "service_description";

    // Time Slots Table Columns
    private static final String SLOT_ID = "slot_id";
    private static final String SERVICE_ID_FK = "service_id_fk"; // Foreign Key from Services
    private static final String TIME_SLOT = "time_slot"; // E.g., 9:00 AM - 10:00 AM

    // Bookings Table Columns
    private static final String BOOKING_ID = "booking_id";
    private static final String BOOKING_SERVICE_NAME = "service_name";
    private static final String BOOKING_TIME_SLOT = "timeslot";
    private static final String BOOKING_DATE = "booking_date";

    // Constructor
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + USERNAME + " TEXT, "
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Services Table
        String CREATE_SERVICES_TABLE = "CREATE TABLE " + TABLE_SERVICES + "("
                + SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SERVICE_NAME + " TEXT, "
                + SERVICE_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_SERVICES_TABLE);

        // Create Time Slots Table
        String CREATE_TIME_SLOTS_TABLE = "CREATE TABLE " + TABLE_TIME_SLOTS + "("
                + SLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SERVICE_ID_FK + " INTEGER, "
                + TIME_SLOT + " TEXT, "
                + "FOREIGN KEY(" + SERVICE_ID_FK + ") REFERENCES " + TABLE_SERVICES + "(" + SERVICE_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TIME_SLOTS_TABLE);

        // Create Bookings Table
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOKING_SERVICE_NAME + " TEXT, "
                + BOOKING_TIME_SLOT + " TEXT, "
                + BOOKING_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME_SLOTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

        // Recreate the tables
        onCreate(db);
    }

    // Method to Add New User
    public void addUser(String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name); // Add the name
        values.put(USERNAME, username); // Add the username
        values.put(PASSWORD, password); // Add the password

        // Inserting the new row into the database
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing the database connection after insertion
    }

    // Method to Add New Service
    public void addService(String serviceName, String serviceDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_NAME, serviceName);
        values.put(SERVICE_DESCRIPTION, serviceDescription);

        // Inserting Row
        db.insert(TABLE_SERVICES, null, values);
        db.close(); // Closing the database connection
    }

    // Method to Add Time Slot for a Service
    public void addTimeSlot(int serviceId, String timeSlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_ID_FK, serviceId);
        values.put(TIME_SLOT, timeSlot);

        // Inserting Row
        db.insert(TABLE_TIME_SLOTS, null, values);
        db.close(); // Closing the database connection
    }

    // Method to Add a New Booking
    public void addBooking(String serviceName, String timeSlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKING_SERVICE_NAME, serviceName); // Ensure this matches the column name in your bookings table
        values.put(BOOKING_TIME_SLOT, timeSlot); // Ensure this matches the column name in your bookings table

        // Inserting the booking
        db.insert(TABLE_BOOKINGS, null, values);
        db.close(); // Closing the database connection
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS, null);
    }

    // Method to Get All Services
    public Cursor getAllServices() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SERVICES, null);
    }

    // Method to Get Time Slots for a Specific Service
    public Cursor getServiceTimeSlots(int serviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TIME_SLOTS + " WHERE " + SERVICE_ID_FK + " = ?", new String[]{String.valueOf(serviceId)});
    }

    // Method to Check Username and Password for Login
    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USERNAME + " = ? AND " + PASSWORD + " = ?", new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid; // Returns true if a match is found
    }
}
