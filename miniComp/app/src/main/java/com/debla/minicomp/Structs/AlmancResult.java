package com.debla.minicomp.Structs;

/**
 * Created by Dave-PC on 2017/2/16.
 * 老黄历数据的结构
 */

public class AlmancResult {
    private String reason;
    private AlmancData result;
    private int error_code;

    public AlmancData getResult() {
        return result;
    }

    public void setResult(AlmancData result) {
        this.result = result;
    }

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
    public class AlmancData{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        public String getYangli() {
            return yangli;
        }

        public void setYangli(String yangli) {
            this.yangli = yangli;
        }

        private String yangli;

        public String getYinli() {
            return yinli;
        }

        public void setYinli(String yinli) {
            this.yinli = yinli;
        }

        public String getWuxing() {
            return wuxing;
        }

        public void setWuxing(String wuxing) {
            this.wuxing = wuxing;
        }

        private String yinli;
        private String wuxing;

        public String getChongsha() {
            return chongsha;
        }

        public void setChongsha(String chongsha) {
            this.chongsha = chongsha;
        }

        private String chongsha;

        public String getBaiji() {
            return baiji;
        }

        public void setBaiji(String baiji) {
            this.baiji = baiji;
        }

        private String baiji;

        public String getJishen() {
            return jishen;
        }

        public void setJishen(String jishen) {
            this.jishen = jishen;
        }

        private String jishen;

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        private String yi;

        public String getXiongshen() {
            return xiongshen;
        }

        public void setXiongshen(String xiongshen) {
            this.xiongshen = xiongshen;
        }

        private String xiongshen;

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }

        private String ji;

    }

}

