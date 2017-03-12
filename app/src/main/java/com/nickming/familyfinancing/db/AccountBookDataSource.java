package com.nickming.familyfinancing.db;

import com.nickming.familyfinancing.entity.AccountBookEntity;

import java.util.List;

import rx.Observable;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/17
 * Time:10:05
 * E-mail:962570483@qq.com
 */

public interface AccountBookDataSource {

    Observable<List<AccountBookEntity>> getAllBooks();

    long addAccountBook(AccountBookEntity entity);

    long deleteAccountBook(AccountBookEntity entity);

    void isExistInDb(AccountBookEntity entity,IExistInDbListener listener);

    public interface IExistInDbListener {
        void isExist(boolean exist);
    }
}
