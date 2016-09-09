package com.android.ming.bean;

/**
 * Created by YO on 2016/9/3.
 */
public class PayResult {
    String channelOrderno;
    String message;
    String orderno;
    String payType;
    String refno;
    String respCode;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getChannelOrderno() {
        return channelOrderno;
    }

    public void setChannelOrderno(String channelOrderno) {
        this.channelOrderno = channelOrderno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }
}
