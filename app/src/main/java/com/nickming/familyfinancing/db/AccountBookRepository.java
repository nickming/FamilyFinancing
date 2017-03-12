package com.nickming.familyfinancing.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nickming.familyfinancing.entity.AccountBookEntity;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/17
 * Time:10:06
 * E-mail:962570483@qq.com
 */

public class AccountBookRepository implements AccountBookDataSource {

    private volatile static AccountBookRepository mInstance;

    private final BriteDatabase mDatabaseHelper;

    private final Func1<Cursor, AccountBookEntity> mTaskMapperFunction;

    private AccountBookRepository(Context context) {
        FinancingDbOpenHelper openHelper = new FinancingDbOpenHelper(context.getApplicationContext());
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
        mTaskMapperFunction = new Func1<Cursor, AccountBookEntity>() {
            @Override
            public AccountBookEntity call(Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_NAME));
                int date = cursor.getInt(cursor.getColumnIndexOrThrow(FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_DATE));
                return AccountBookEntity.create(name, date);
            }
        };
    }

    public static AccountBookRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FinancingDbRepository.class) {
                if (mInstance == null) {
                    mInstance = new AccountBookRepository(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public Observable<List<AccountBookEntity>> getAllBooks() {
        String sql = String.format("SELECT * FROM %s ",
                FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction);
    }

    @Override
    public long addAccountBook(AccountBookEntity entity) {
        ContentValues values = new ContentValues();
        values.put(FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_NAME, entity.name());
        values.put(FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_DATE, entity.date());
        return mDatabaseHelper.insert(FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public long deleteAccountBook(AccountBookEntity entity) {
        String selection = FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {String.valueOf(entity.name())};
        return mDatabaseHelper.delete(FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void isExistInDb(final AccountBookEntity entity, final IExistInDbListener listener) {
        String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME,
                FinancingDbPersistenceContract.AccountBookEntry.COLUMN_NAME_NAME, entity.name());
        mDatabaseHelper.createQuery(FinancingDbPersistenceContract.AccountBookEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AccountBookEntity>>() {
                    @Override
                    public void call(List<AccountBookEntity> accountBookEntities) {
                        if (accountBookEntities.size() == 0) {
                            if (listener != null) {
                                listener.isExist(false);
                            }
                        } else {
                            if (listener != null) {
                                listener.isExist(true);
                            }
                        }
                    }
                });
    }
}
