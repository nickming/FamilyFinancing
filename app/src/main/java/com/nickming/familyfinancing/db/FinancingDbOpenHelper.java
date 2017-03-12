package com.nickming.familyfinancing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nickming.familyfinancing.util.TimeUtil;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:21:45
 * E-mail:962570483@qq.com
 */

public class FinancingDbOpenHelper extends SQLiteOpenHelper {

    public static final int DATEBASE_VERSION = 1;

    public static final String DATEBASE_NAME = "Financing.db";

    public static final String TEXT_TYPE = " TEXT";

    public static final String INTEGER_TYPE = " INTEGER";

    public static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME + "(" +
                    FinancingDbPersistenceContract.FinancingEntry._ID + " integer primary key autoincrement" + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_TYPE + INTEGER_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_MONEY + TEXT_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_DATE + INTEGER_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_REMARK + TEXT_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_PAYCHANNEL + INTEGER_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_USER + TEXT_TYPE +
                    ")";
    private static final String SQL_CREATE_CATEGORY_ENTRIES =
            "CREATE TABLE " + FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME + "(" +
                    FinancingDbPersistenceContract.AccountBookEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_DATE + INTEGER_TYPE +
                    ")";

    public FinancingDbOpenHelper(Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_CATEGORY_ENTRIES);
        db.execSQL(String.format("INSERT INTO %s VALUES (%s,%s,%s)", FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME,
                "1","'mime'", TimeUtil.getCurrentTime(TimeUtil.DATABASE_TIME_FORMAT)));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
