package com.curriculum.constant;

import java.util.Locale;

public enum WebCodeEnum
{
    OK(0, "success", "成功"),
    UNKNOWN_ERROR(100000, "Server Error", "服务出错"),
    NO_USER_ERROR(100001, "no user", "此用户名不存在"),
    PASSWORD_ERROR(100002, "wrong password", "密码错误"),
    USER_ALREADY_EXISTS(100003, "username already exsits", "用户名已存在"),
    RETURN_NULL_ERROR(100004, "Return Null", "返回为空"),
    DELETE_QUESTION_ERROR(100005, "delete question error", "删除试题失败"),
    NO_PAPER_ERROR(100006, "no paper error", "删除试卷失败"),
    DELETE_PAPER_ERROR(100007," can't delete this paper","");

    private int errorCode;
    private String enErrorMsg;
    private String zhErrorMsg;

    private WebCodeEnum(int errorCode, String enErrorMsg, String zhErrorMsg) { this.errorCode = errorCode;
        this.enErrorMsg = enErrorMsg;
        this.zhErrorMsg = zhErrorMsg; }

    public int getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getEnErrorMsg() {
        return this.enErrorMsg;
    }

    public void setEnErrorMsg(String enErrorMsg) {
        this.enErrorMsg = enErrorMsg;
    }

    public String getZhErrorMsg() {
        return this.zhErrorMsg;
    }

    public void setZhErrorMsg(String zhErrorMsg) {
        this.zhErrorMsg = zhErrorMsg;
    }

    public String getErrorMsg(Locale locale)
    {
        if ((locale.equals(Locale.CHINESE)) || (locale.equals(Locale.CHINA)) || (locale.equals(Locale.TAIWAN)) || (locale.equals(Locale.PRC)) || (locale.equals(Locale.SIMPLIFIED_CHINESE)) || (locale.equals(Locale.TRADITIONAL_CHINESE))) {
            return this.zhErrorMsg;
        }
        return this.enErrorMsg;
    }
}