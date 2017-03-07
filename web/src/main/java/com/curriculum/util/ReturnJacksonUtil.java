package com.curriculum.util;

import com.curriculum.constant.WebCodeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ReturnJacksonUtil
{
    public static String resultOk(Locale locale)
            throws JsonProcessingException
    {
        WebCodeEnum webCodeEnum = WebCodeEnum.OK;
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                null, null);
    }

    public static String resultOk() throws JsonProcessingException {
        WebCodeEnum webCodeEnum = WebCodeEnum.OK;
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(Locale.ENGLISH),
                null, null);
    }

    public static String resultOkWithEnum(WebCodeEnum webCodeEnum, Locale locale)
            throws JsonProcessingException
    {
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                null, null);
    }

    public static String resultOk(Object data, Locale locale)
            throws JsonProcessingException
    {
        WebCodeEnum webCodeEnum = WebCodeEnum.OK;
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                data, null);
    }

    public static String resultOk(String key, Object value, Locale locale)
            throws JsonProcessingException
    {
        Map data = new HashMap();
        data.put(key, value);
        WebCodeEnum webCodeEnum = WebCodeEnum.OK;
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                data, null);
    }

    public static String resultOkWithExclude(Object data, Class clazz, Locale locale, String[] excludeFileds)
            throws JsonProcessingException
    {
        Map exclude = new HashMap();
        exclude.put(clazz, new HashSet(Arrays.asList(excludeFileds)));
        WebCodeEnum webCodeEnum = WebCodeEnum.OK;
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                data, null, exclude);
    }

    public static String resultOkWithInclude(Object data, Class clazz, Locale locale, String[] includeFileds)
            throws JsonProcessingException
    {
        Map include = new HashMap();
        include.put(clazz, new HashSet(Arrays.asList(includeFileds)));
        WebCodeEnum webCodeEnum = WebCodeEnum.OK;
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                data, include, null);
    }

    public static String resultWithFailed(WebCodeEnum webCodeEnum, Locale locale)
            throws JsonProcessingException
    {
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(locale),
                null, null);
    }

    public static String resultWithFailed(WebCodeEnum webCodeEnum) throws JsonProcessingException {
        return result(webCodeEnum.getErrorCode(), webCodeEnum
                        .getErrorMsg(Locale.ENGLISH),
                null, null);
    }

    public static <T> String result(int resultCode, String resultMsg, Object data, Class<T> jsonViewClazz)
            throws JsonProcessingException
    {
        Map status = new HashMap();
        status.put("code", Integer.valueOf(resultCode));
        status.put("msg", String.valueOf(resultMsg));

        Map result = new HashMap();
        result.put("status", status);
        if (null != data) {
            result.put("data", data);
        }
        if (null == jsonViewClazz) {
            return JacksonUtils.toJson(result);
        }
        return JacksonUtils.toJson(result, jsonViewClazz);
    }

    public static String result(int resultCode, String resultMsg, Object data, Map<Class, Set<String>> include, Map<Class, Set<String>> exclude)
            throws JsonProcessingException
    {
        Map status = new HashMap();
        status.put("code", Integer.valueOf(resultCode));
        status.put("msg", String.valueOf(resultMsg));

        Map result = new HashMap();
        result.put("status", status);
        if (null != data) {
            result.put("data", data);
        }
        return JacksonUtils.toJson(result, include, exclude);
    }

    public static String resultWithException(int resultCode, String resultMsg, String data)
            throws JsonProcessingException
    {
        Map status = new HashMap();
        status.put("code", Integer.valueOf(resultCode));
        status.put("msg", String.valueOf(resultMsg));

        Map result = new HashMap();
        result.put("status", status);
        if (null != data) {
            result.put("data", data);
        }
        return JacksonUtils.toJson(result);
    }
}