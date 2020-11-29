package com.example.networkcalling.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database_workers";
    public static final String TABLE_NAME_ALL_WORKERS = "workers";
    public static final String TABLE_NAME_OUR_COMPANY_WORKERS = "our_company_workers";
    public static final int VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Чтобы у пользователей у которых уже установлено приложение
         * Добавить таблицу - нужно ее добавить в методе onUpgrade при условии что старая версия равно той
         * которую мы ожидаем, и новая соответственно.
         *
         * Чтобы у пользователей у которых приложение НЕ установленно
         * добавить создание таблицы к методу onCreate
         *
         */

        db.execSQL("create table " + TABLE_NAME_ALL_WORKERS + "(" +
                "id integer primary key autoincrement," +
                "employeeName text," +
                "employeeSalary text," +
                "employeeAge text" +
                ");");

        db.execSQL("create table " + TABLE_NAME_OUR_COMPANY_WORKERS + "(" +
                "id integer primary key autoincrement," +
                "employeeName text," +
                "employeeSalary text," +
                "employeeAge text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("create table " + TABLE_NAME_OUR_COMPANY_WORKERS + "(" +
                    "id integer primary key autoincrement," +
                    "employeeName text," +
                    "employeeSalary text," +
                    "employeeAge text" +
                    ");");
        }
    }
}
