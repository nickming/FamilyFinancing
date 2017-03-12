package com.nickming.familyfinancing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:13:18
 * E-mail:
 */

public class SharesEntity implements Serializable{

//    {
//        "error_code": 0,
//            "reason": "SUCCESSED!",
//            "result": {
//        "totalCount": "1765", /*总条数*/
//                "page": "1",/*当前页*/
//                "num": "20", /*显示条数*/
//                "data": [
//        {
//            "symbol": "sz300001",/*代码*/
//                "name": "特锐德",/*名称*/
//                "trade": "20.450",/*最新价*/
//                "pricechange": "0.060",/*涨跌额*/
//                "changepercent": "0.294",/*涨跌幅*/
//                "buy": "20.440",/*买入*/
//                "sell": "20.450",/*卖出*/
//                "settlement": "20.390",/*昨收*/
//                "open": "21.010",/*今开*/
//                "high": "21.040",/*最高*/
//                "low": "19.610",/*最低*/
//                "volume": 71773,/*成交量*/
//                "amount": 146356152,/*成交额*/
//                "ticktime": "10:41:25",/*时间*/
//        },
//        ...]}

    /**
     * error_code : 0
     * reason : SUCCESSED!
     * result : {"totalCount":"1765","page":"1","num":"20","data":[{"symbol":"sz300001","name":"特锐德","trade":"20.450","pricechange":"0.060","changepercent":"0.294","buy":"20.440","sell":"20.450","settlement":"20.390","open":"21.010","high":"21.040","low":"19.610","volume":71773,"amount":146356152,"ticktime":"10:41:25"}]}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * totalCount : 1765
         * page : 1
         * num : 20
         * data : [{"symbol":"sz300001","name":"特锐德","trade":"20.450","pricechange":"0.060","changepercent":"0.294","buy":"20.440","sell":"20.450","settlement":"20.390","open":"21.010","high":"21.040","low":"19.610","volume":71773,"amount":146356152,"ticktime":"10:41:25"}]
         */

        private String totalCount;
        private String page;
        private String num;
        private List<DataBean> data;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * symbol : sz300001
             * name : 特锐德
             * trade : 20.450
             * pricechange : 0.060
             * changepercent : 0.294
             * buy : 20.440
             * sell : 20.450
             * settlement : 20.390
             * open : 21.010
             * high : 21.040
             * low : 19.610
             * volume : 71773
             * amount : 146356152
             * ticktime : 10:41:25
             */

            private String symbol;
            private String name;
            private String trade;
            private String pricechange;
            private String changepercent;
            private String buy;
            private String sell;
            private String settlement;
            private String open;
            private String high;
            private String low;
            private int volume;
            private int amount;
            private String ticktime;

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTrade() {
                return trade;
            }

            public void setTrade(String trade) {
                this.trade = trade;
            }

            public String getPricechange() {
                return pricechange;
            }

            public void setPricechange(String pricechange) {
                this.pricechange = pricechange;
            }

            public String getChangepercent() {
                return changepercent;
            }

            public void setChangepercent(String changepercent) {
                this.changepercent = changepercent;
            }

            public String getBuy() {
                return buy;
            }

            public void setBuy(String buy) {
                this.buy = buy;
            }

            public String getSell() {
                return sell;
            }

            public void setSell(String sell) {
                this.sell = sell;
            }

            public String getSettlement() {
                return settlement;
            }

            public void setSettlement(String settlement) {
                this.settlement = settlement;
            }

            public String getOpen() {
                return open;
            }

            public void setOpen(String open) {
                this.open = open;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
                this.volume = volume;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getTicktime() {
                return ticktime;
            }

            public void setTicktime(String ticktime) {
                this.ticktime = ticktime;
            }
        }
    }
}
