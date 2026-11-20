package com.CS360Project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "InventoryDB.db";
    private static final int DATABASE_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Products Table
    private static final String TABLE_PRODUCTS = "products";
    private static final String COL_PRODUCT_ID = "productId";
    private static final String COL_PRODUCT_NAME = "name";
    private static final String COL_VENDOR = "vendor";
    private static final String COL_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)");

        // Create Products table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_PRODUCT_ID + " TEXT PRIMARY KEY, " +
                COL_PRODUCT_NAME + " TEXT, " +
                COL_VENDOR + " TEXT, " +
                COL_QUANTITY + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // ===============================
    // USER METHODS
    // ===============================

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE username=? AND password=?",
                new String[]{username, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // ===============================
    // PRODUCT METHODS
    // ===============================

    public boolean addProduct(String id, String name, String vendor, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_PRODUCT_ID, id);
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_VENDOR, vendor);
        values.put(COL_QUANTITY, qty);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        return result != -1;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    public void updateQuantity(String productId, int newQty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_QUANTITY, newQty);

        db.update(TABLE_PRODUCTS, values,
                "productId=?",
                new String[]{productId});
    }

    public Cursor getProductById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_PRODUCTS + " WHERE productId=?",
                new String[]{id});
    }
    public void insertDefaultUsers() {

        SQLiteDatabase db = this.getWritableDatabase();

        // Check if users already exist
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);

        if (cursor.getCount() == 0) {

            ContentValues values = new ContentValues();

            // User 1
            values.put("username", "admin");
            values.put("password", "admin123");
            db.insert("users", null, values);

            // User 2
            values.clear();
            values.put("username", "manager");
            values.put("password", "manager123");
            db.insert("users", null, values);

            // User 3
            values.clear();
            values.put("username", "employee");
            values.put("password", "employee123");
            db.insert("users", null, values);
        }

        cursor.close();
    }
    public void insertDefaultProducts() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor.getCount() == 0) {

            insertProduct(db, "W001", "Macallan 18 Year Old Sherry Oak", "Macallan", 15);
            insertProduct(db, "W002", "Johnnie Walker Blue Label", "Johnnie Walker", 20);
            insertProduct(db, "W003", "Lagavulin 16 Year Old", "Lagavulin", 12);
            insertProduct(db, "W004", "Glenfiddich 18 Year Old", "Glenfiddich", 10);
            insertProduct(db, "W005", "Jameson Black Barrel", "Jameson", 25);
            insertProduct(db, "W006", "Bulleit Bourbon Frontier Whiskey", "Bulleit", 30);
            insertProduct(db, "W007", "Jack Daniel's Old No. 7", "Jack Daniel's", 50);
            insertProduct(db, "W008", "Crown Royal Deluxe", "Crown Royal", 22);
            insertProduct(db, "W009", "Wild Turkey 101", "Wild Turkey", 18);
            insertProduct(db, "W010", "The Glenlivet 12 Year Old", "Glenlivet", 14);
            insertProduct(db, "W011", "Balvenie DoubleWood 12 Year", "Balvenie", 8);
            insertProduct(db, "W012", "Highland Park 12 Year Old", "Highland Park", 11);
            insertProduct(db, "W013", "Talisker 10 Year Old", "Talisker", 1);
            insertProduct(db, "W014", "Oban 14 Year Old", "Oban", 6);
            insertProduct(db, "W015", "Laphroaig 10 Year Old", "Laphroaig", 9);
            insertProduct(db, "W016", "Bushmills Black Bush", "Bushmills", 16);
            insertProduct(db, "W017", "Chivas Regal 18 Year Old", "Chivas Regal", 13);
            insertProduct(db, "W018", "Jim Beam White Label", "Jim Beam", 40);
            insertProduct(db, "W019", "Maker's Mark", "Maker's Mark", 24);
            insertProduct(db, "W020", "Woodford Reserve Double Oaked", "Woodford Reserve", 10);
            insertProduct(db, "W021", "Knob Creek 9 Year Old", "Knob Creek", 15);
            insertProduct(db, "W022", "Glenmorangie The Original", "Glenmorangie", 18);
            insertProduct(db, "W023", "Dalmore 12 Year Old", "Dalmore", 7);
            insertProduct(db, "W024", "Yamazaki 12 Year Old", "Yamazaki", 2);
            insertProduct(db, "W025", "Hibiki Japanese Harmony", "Hibiki", 8);
        }

        cursor.close();
    }
    private void insertProduct(SQLiteDatabase db, String id, String name, String vendor, int qty) {

        ContentValues values = new ContentValues();
        values.put("productId", id);
        values.put("name", name);
        values.put("vendor", vendor);
        values.put("quantity", qty);

        db.insert("products", null, values);
    }
}