package com.nickming.familyfinancing.engine;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:20:53
 * E-mail:962570483@qq.com
 */

public class TypeIconFactory {

    public static String getChannelName(int type) {
        switch (type) {
            case FinancingTypeContract.PayType.TYPE_CASH:
                return "现金";
            case FinancingTypeContract.PayType.TYPE_BANK_CARD:
                return "银行卡";
            case FinancingTypeContract.PayType.TYPE_WECHAT:
                return "微信";
            case FinancingTypeContract.PayType.TYPE_ALIPAY:
                return "支付宝";
            default:
                return null;
        }
    }

    /**
     * 生成指定的entity
     *
     * @param type
     * @return
     */
    public static RecordTypeEntity createTypeEntityForType(int type) {
        RecordTypeEntity entity = null;
        switch (type) {
            case FinancingTypeContract.ExpendType.TYPE_BEAUTIFY:
                entity = RecordTypeEntity.create(R.color.icon_red, R.mipmap.ic_beautify, R.string.icon_beautify, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_BONUS:
                entity = RecordTypeEntity.create(R.color.icon_blue, R.mipmap.ic_bonus, R.string.icon_red_pocket, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_BOOKS:
                entity = RecordTypeEntity.create(R.color.icon_amber, R.mipmap.ic_books, R.string.icon_books, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_CHILDREN:
                entity = RecordTypeEntity.create(R.color.icon_brown, R.mipmap.ic_children, R.string.icon_children, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_CLOTHES:
                entity = RecordTypeEntity.create(R.color.icon_cyan, R.mipmap.ic_clothes, R.string.icon_clothes, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_FOOD:
                entity = RecordTypeEntity.create(R.color.icon_deep_orange, R.mipmap.ic_food, R.string.icon_food, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_MEDICAL:
                entity = RecordTypeEntity.create(R.color.icon_green, R.mipmap.ic_medical, R.string.icon_medical, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_OTHERS:
                entity = RecordTypeEntity.create(R.color.icon_lim, R.mipmap.ic_other, R.string.icon_others, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_PET:
                entity = RecordTypeEntity.create(R.color.icon_pink, R.mipmap.ic_pets, R.string.icon_pet, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_PHONE:
                entity = RecordTypeEntity.create(R.color.icon_purple, R.mipmap.ic_phone, R.string.icon_phone, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_SOCIAL:
                entity = RecordTypeEntity.create(R.color.icon_red, R.mipmap.ic_social, R.string.icon_social, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_SPORTS:
                entity = RecordTypeEntity.create(R.color.icon_blue, R.mipmap.ic_sport, R.string.icon_sport, type);
                break;
            case FinancingTypeContract.ExpendType.TYPE_TRAFFIC:
                entity = RecordTypeEntity.create(R.color.icon_amber, R.mipmap.ic_traffic, R.string.icon_traffic, type);
                break;
            case FinancingTypeContract.EarningType.TYPE_BONUS:
                entity = RecordTypeEntity.create(R.color.icon_cyan, R.mipmap.ic_bonus, R.string.icon_bonus, type);
                break;
            case FinancingTypeContract.EarningType.TYPE_INVEST:
                entity = RecordTypeEntity.create(R.color.icon_pink, R.mipmap.ic_invest, R.string.icon_invest, type);
                break;
            case FinancingTypeContract.EarningType.TYPE_OTHERS:
                entity = RecordTypeEntity.create(R.color.icon_green, R.mipmap.ic_other, R.string.icon_others, type);
                break;
            case FinancingTypeContract.EarningType.TYPE_PART_TIME_JOB:
                entity = RecordTypeEntity.create(R.color.icon_brown, R.mipmap.ic_part_time_job, R.string.icon_part_time_job, type);
                break;
            case FinancingTypeContract.EarningType.TYPE_SALARY:
                entity = RecordTypeEntity.create(R.color.icon_red, R.mipmap.ic_salary, R.string.icon_salary, type);
                break;
            case FinancingTypeContract.PayType.TYPE_CASH:
                entity = RecordTypeEntity.create(R.color.icon_red, R.mipmap.ic_cash, R.string.icon_cash, type);
                break;
            case FinancingTypeContract.PayType.TYPE_BANK_CARD:
                entity = RecordTypeEntity.create(R.color.icon_cyan, R.mipmap.ic_bank_card, R.string.icon_bankcard, type);
                break;
            case FinancingTypeContract.PayType.TYPE_WECHAT:
                entity = RecordTypeEntity.create(R.color.icon_green, R.mipmap.ic_wechat, R.string.icon_wechat, type);
                break;
            case FinancingTypeContract.PayType.TYPE_ALIPAY:
                entity = RecordTypeEntity.create(R.color.icon_blue, R.mipmap.ic_alipay, R.string.icon_aplipay, type);
                break;
        }
        return entity;
    }

    /**
     * 支出类型返回true,否则返回false
     *
     * @param type
     * @return
     */
    public static boolean isEarningOrExpendType(int type) {
        if (type >= 11 && type <= 23) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成支出
     *
     * @return
     */
    public static List<RecordTypeEntity> getExpendList() {
        List<RecordTypeEntity> result = new ArrayList<>();
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_FOOD));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_TRAFFIC));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_CLOTHES));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_BONUS));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_CHILDREN));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_PHONE));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_MEDICAL));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_BEAUTIFY));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_BOOKS));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_PET));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_SOCIAL));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_SPORTS));
        result.add(createTypeEntityForType(FinancingTypeContract.ExpendType.TYPE_OTHERS));
        return result;
    }


    public static List<RecordTypeEntity> getEarningList() {
        List<RecordTypeEntity> result = new ArrayList<>();
        result.add(createTypeEntityForType(FinancingTypeContract.EarningType.TYPE_SALARY));
        result.add(createTypeEntityForType(FinancingTypeContract.EarningType.TYPE_INVEST));
        result.add(createTypeEntityForType(FinancingTypeContract.EarningType.TYPE_PART_TIME_JOB));
        result.add(createTypeEntityForType(FinancingTypeContract.EarningType.TYPE_BONUS));
        result.add(createTypeEntityForType(FinancingTypeContract.EarningType.TYPE_OTHERS));
        return result;
    }

    public static List<RecordTypeEntity> getPayTypeList() {
        List<RecordTypeEntity> result = new ArrayList<>();
        result.add(createTypeEntityForType(FinancingTypeContract.PayType.TYPE_CASH));
        result.add(createTypeEntityForType(FinancingTypeContract.PayType.TYPE_BANK_CARD));
        result.add(createTypeEntityForType(FinancingTypeContract.PayType.TYPE_WECHAT));
        result.add(createTypeEntityForType(FinancingTypeContract.PayType.TYPE_ALIPAY));
        return result;
    }
}
