package com.nickming.familyfinancing.engine;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:20:56
 * E-mail:962570483@qq.com
 */

public final class FinancingTypeContract {

    /**
     * 支出项目类别
     */
    public static class ExpendType {
        //饮食
        public static final int TYPE_FOOD = 11;
        //交通
        public static final int TYPE_TRAFFIC = 12;
        //服饰
        public static final int TYPE_CLOTHES = 13;
        //红包
        public static final int TYPE_BONUS = 14;
        //书籍
        public static final int TYPE_BOOKS = 15;
        //话费
        public static final int TYPE_PHONE = 16;
        //社交
        public static final int TYPE_SOCIAL = 17;
        //宠物
        public static final int TYPE_PET = 18;
        //孩子
        public static final int TYPE_CHILDREN = 19;
        //运动
        public static final int TYPE_SPORTS = 20;
        //丽人
        public static final int TYPE_BEAUTIFY = 21;
        //医疗
        public static final int TYPE_MEDICAL = 22;
        //其他
        public static final int TYPE_OTHERS = 23;
    }

    /**
     * 收入项目类别
     */
    public static class EarningType {
        //薪水
        public static final int TYPE_SALARY = 31;
        //兼职
        public static final int TYPE_PART_TIME_JOB = 32;
        //投资
        public static final int TYPE_INVEST = 33;
        //奖金
        public static final int TYPE_BONUS = 34;

        public static final int TYPE_OTHERS = 35;
    }

    public static class PayType {
        public static final int TYPE_CASH = 40;
        public static final int TYPE_BANK_CARD = 41;
        public static final int TYPE_WECHAT = 42;
        public static final int TYPE_ALIPAY = 43;
    }

}
