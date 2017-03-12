package com.nickming.familyfinancing.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:16:07
 * E-mail:
 */

public class SharesDetailEntity implements Serializable {


    /**
     * resultcode : 200
     * reason : SUCCESSED!
     * result : [{"gopicture":{"minurl":"http://image.sinajs.cn/newchart/min/n/sh601009.gif","dayurl":"http://image.sinajs.cn/newchart/daily/n/sh601009.gif","weekurl":"http://image.sinajs.cn/newchart/weekly/n/sh601009.gif","monthurl":"http://image.sinajs.cn/newchart/monthly/n/sh601009.gif"}}]
     */

    private String resultcode;
    private String reason;
    private List<ResultBean> result;
    private List<ResultBean.Item> mResult=new ArrayList<>();

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * gopicture : {"minurl":"http://image.sinajs.cn/newchart/min/n/sh601009.gif","dayurl":"http://image.sinajs.cn/newchart/daily/n/sh601009.gif","weekurl":"http://image.sinajs.cn/newchart/weekly/n/sh601009.gif","monthurl":"http://image.sinajs.cn/newchart/monthly/n/sh601009.gif"}
         */

        private GopictureBean gopicture;

        public GopictureBean getGopicture() {
            return gopicture;
        }

        public void setGopicture(GopictureBean gopicture) {
            this.gopicture = gopicture;
        }

        public static class GopictureBean {
            /**
             * minurl : http://image.sinajs.cn/newchart/min/n/sh601009.gif
             * dayurl : http://image.sinajs.cn/newchart/daily/n/sh601009.gif
             * weekurl : http://image.sinajs.cn/newchart/weekly/n/sh601009.gif
             * monthurl : http://image.sinajs.cn/newchart/monthly/n/sh601009.gif
             */

            private String minurl;
            private String dayurl;
            private String weekurl;
            private String monthurl;

            public String getMinurl() {
                return minurl;
            }

            public void setMinurl(String minurl) {
                this.minurl = minurl;
            }

            public String getDayurl() {
                return dayurl;
            }

            public void setDayurl(String dayurl) {
                this.dayurl = dayurl;
            }

            public String getWeekurl() {
                return weekurl;
            }

            public void setWeekurl(String weekurl) {
                this.weekurl = weekurl;
            }

            public String getMonthurl() {
                return monthurl;
            }

            public void setMonthurl(String monthurl) {
                this.monthurl = monthurl;
            }
        }


        public static class Item{
            private String title;
            private String url;

            public Item(String title, String url) {
                this.title = title;
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

    }
}
