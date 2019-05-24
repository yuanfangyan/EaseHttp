package com.yuan.easehttp;

public class Bean {

    /**
     * resultBody : {"admin":true}
     * resultCode : 0
     */

    private ResultBodyBean resultBody;
    private String resultCode;
    private String resultMsg;

    public ResultBodyBean getResultBody() {
        return resultBody;
    }

    public void setResultBody(ResultBodyBean resultBody) {
        this.resultBody = resultBody;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public static class ResultBodyBean {
        /**
         * admin : true
         */

        private boolean admin;

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }
}
