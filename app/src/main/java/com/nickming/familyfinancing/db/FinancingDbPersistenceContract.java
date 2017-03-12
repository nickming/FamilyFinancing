package com.nickming.familyfinancing.db;

import android.provider.BaseColumns;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:21:50
 * E-mail:962570483@qq.com
 */

public class FinancingDbPersistenceContract {

    public static abstract class FinancingEntry implements BaseColumns {
        public static final String TABLE_NAME = "financing_account_item";
        public static final String COLUMN_NAME_MONEY = "money";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_REMARK = "remark";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PAYCHANNEL="paychannel";

    }

    public static abstract class AccountBookEntry implements BaseColumns {
        public static final String TABLE_NAME = "account_book";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
