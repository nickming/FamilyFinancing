package com.nickming.familyfinancing.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.nickming.familyfinancing.entity.FinancingEntity;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:22:18
 * E-mail:962570483@qq.com
 */

public class FinancingDbRepository implements FinancingItemDataSource {

    private volatile static FinancingDbRepository mInstance;

    private final BriteDatabase mDatabaseHelper;

    private final Func1<Cursor, FinancingEntity> mTaskMapperFunction;

    private FinancingDbRepository(Context context) {
        FinancingDbOpenHelper openHelper = new FinancingDbOpenHelper(context.getApplicationContext());
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
        mTaskMapperFunction = new Func1<Cursor, FinancingEntity>() {
            @Override
            public FinancingEntity call(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry._ID));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_TYPE));
                int date = cursor.getInt(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_DATE));
                String money = cursor.getString(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_MONEY));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_CATEGORY));
                String remark = cursor.getString(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_REMARK));
                String user = cursor.getString(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_USER));
                int payChannel=cursor.getInt(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_PAYCHANNEL));
                return FinancingEntity.create(id, type,payChannel, Double.valueOf(money), category, user, remark, date);
            }
        };
    }

    public static FinancingDbRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FinancingDbRepository.class) {
                if (mInstance == null) {
                    mInstance = new FinancingDbRepository(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public Observable<List<FinancingEntity>> getAllFinancingItems() {
        String[] projection = {
                FinancingDbPersistenceContract.FinancingEntry._ID,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_USER,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_TYPE,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_MONEY,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_REMARK,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_DATE,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_PAYCHANNEL,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_CATEGORY,
        };
        String sql = String.format("SELECT %s FROM %s ", TextUtils.join(",", projection),
                FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction);
    }

    @Override
    public Observable<List<FinancingEntity>> getFinacingItemsForDate(int fromDate, int toDate) {
        String sql = String.format("SELECT * FROM %s WHERE %s BETWEEN %s And %s", FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME,
                FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_DATE, fromDate, toDate
        );
        return mDatabaseHelper.createQuery(FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction);
    }

    @Override
    public long saveFinancingItem(@NonNull FinancingEntity entity) {
        ContentValues values = new ContentValues();
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_TYPE, entity.type());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_CATEGORY, entity.financingCategory());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_USER, entity.user());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_REMARK, entity.remark());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_MONEY, entity.money());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_DATE, entity.date());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_PAYCHANNEL,entity.payChannel());
        return mDatabaseHelper.insert(FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public long updateFinancingItem(@NonNull FinancingEntity entity) {
        ContentValues values = new ContentValues();
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_TYPE, entity.type());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_CATEGORY, entity.financingCategory());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_USER, entity.user());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_REMARK, entity.remark());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_MONEY, entity.money());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_DATE, entity.date());
        values.put(FinancingDbPersistenceContract.FinancingEntry.COLUMN_NAME_PAYCHANNEL,entity.payChannel());
        String where = FinancingDbPersistenceContract.FinancingEntry._ID + " LIKE ?";
        String[] whereArgs = {String.valueOf(entity.id())};
        return mDatabaseHelper.update(FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME,
                values, where, whereArgs);
    }

    @Override
    public long deleteFinancingItem(@NonNull FinancingEntity entity) {
        String selection = FinancingDbPersistenceContract.FinancingEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(entity.id())};
        return mDatabaseHelper.delete(FinancingDbPersistenceContract.FinancingEntry.TABLE_NAME, selection, selectionArgs);
    }
}
