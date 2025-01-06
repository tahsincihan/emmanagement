public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserManagement.db";
    private static final int DATABASE_VERSION = 1;

    // User table
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    // Create table SQL
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_ROLE + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        // Insert demo users
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password, role) VALUES ('admin', 'admin123', 'admin')");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password, role) VALUES ('employee', 'emp123', 'employee')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Validate user credentials
    public String validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM " + TABLE_USERS + " WHERE username=? AND password=?",
                new String[]{username, password});

        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(0); // Get the role (admin or employee)
        }
        cursor.close();
        return role;
    }
}