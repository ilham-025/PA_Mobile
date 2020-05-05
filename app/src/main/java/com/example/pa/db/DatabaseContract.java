package com.example.pa.db;

import android.provider.BaseColumns;

public class DatabaseContract
{
    public static final String TABLE_USER = "users";
    public static final class UserColumns implements BaseColumns {
        static String USER_ID = "user_id";
        static String EMAIL = "email";
        static String PASSWORD = "password";
        static String NAMA = "name";
        static String ROLE = "role";
    }
}
