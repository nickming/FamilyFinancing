package com.nickming.familyfinancing.ui.lock;

import com.nickming.familyfinancing.entity.ActivityCloseEvent;

/**
 * Desc:
 * Author:nickming
 * Date:2017/2/1
 * Time:16:04
 * E-mail:962570483@qq.com
 */

public interface IBaseLockPatternClose {

    void closeSelf(ActivityCloseEvent closeEvent);
}