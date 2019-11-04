/**
 * LY.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package site.guyw.grpg.util;

import com.alibaba.fastjson.JSON;

import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Marker;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.NotSupportedException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * HttpUtils
 *
 * @author
 * @version Id: HttpUtils.java, v 0.1 2019/3/18 16:32  Exp $$
 */
public class HttpUtils {

    public final static String        GET                = "GET";
    public final static String        POST               = "POST";
    public final static String        PUT                = "PUT";
    public final static String        DELETE             = "DELETE";
    public final static String        PATCH              = "PATCH";

    private final static String       UTF8               = "UTF-8";
    private final static String       GBK                = "GBK";

    private final static String       DEFAULT_CHARSET    = UTF8;
    private final static String       DEFAULT_METHOD     = GET;
    private final static String       DEFAULT_MEDIA_TYPE = "application/json";
    private final static boolean      DEFAULT_LOG        = true;

    private final static OkHttpClient CLIENT             = new OkHttpClient.Builder().connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES)).readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS).build();

    /**
     * GET请求
     * @param url
     * URL地址
     * @return
     */
    public static String get(String url) {
        return execute(OkHttp.builder().url(url).build());
    }

    /**
     * GET请求
     * @param url
     * URL地址
     * @return
     */
    public static String get(String url, String charset) {
        return execute(OkHttp.builder().url(url).responseCharset(charset).build());
    }

    /**
     * 带查询参数的GET查询
     * @param url
     * URL地址
     * @param queryMap
     * 查询参数
     * @return
     */
    public static String get(String url, Map<String, String> queryMap) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).build());
    }

    /**
     * 带查询参数的GET查询
     * @param url
     * URL地址
     * @param queryMap
     * 查询参数
     * @return
     */
    public static String get(String url, Map<String, String> queryMap, String charset) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).responseCharset(charset).build());
    }

    /**
     * POST
     * application/json
     * @param url
     * @param obj
     * @return
     */
    public static String postJson(String url, Object obj) {
        return execute(OkHttp.builder().url(url).method(POST).data(JSON.toJSONString(obj)).mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).build());
    }

    /**
     * POST
     * application/x-www-form-urlencoded
     * @param url
     * @param formMap
     * @return
     */
    public static String postForm(String url, Map<String, String> formMap) {
        String data = "";
        if (MapUtils.isNotEmpty(formMap)) {
            data = formMap.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.joining("&"));
        }
        return execute(OkHttp.builder().url(url).method(POST).data(data).mediaType(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED).build());
    }

    /**
     * put
     * @param url
     * @param params
     * @return
     */
    public static String put(String url, Object params) {
        String data = StringUtils.EMPTY;
        if (params != null) {
            data = JSON.toJSONString(params);
        }

        return execute(OkHttp.builder().url(url).method(PUT).data(JSON.toJSONString(data)).mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).build());
    }

    private static String post(String url, String data, String mediaType, String charset) {
        return execute(OkHttp.builder().url(url).method(POST).data(data).mediaType(mediaType).responseCharset(charset).build());
    }

    /**
     * POST
     * multipart/form-data
     * @param url
     * @param formMap
     * @return
     */
    public static boolean postMultiPart(String url, Map<String, String> headerMap, Map<String, String> formMap, File[] files) {

        MediaType mutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
        requestBodyBuilder.setType(MultipartBody.FORM);
        formMap.forEach(requestBodyBuilder::addFormDataPart);

        //循环添加文件
       for(File file :files){
           requestBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(mutilPart_Form_Data,file));
       }

        RequestBody requestBody = requestBodyBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        headerMap.forEach(builder::addHeader);
        builder.post(requestBody);
        Request request = builder.build();

        Response response = null;
        try {
            response = CLIENT.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response.isSuccessful();
    }

    /**
     * POST
     * application/json
     * @param url
     * @param headerMap
     * @param obj
     * @return
     */
    public static String postJsonWithHeader(String url, Map<String,String> headerMap,Object obj) {
        return execute(OkHttp.builder().url(url).method(POST).headerMap(headerMap).data(obj.toString()).mediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON).build());
    }


    /**
     * 通用执行方法
     */
    private static String execute(OkHttp okHttp) {
        if (StringUtils.isBlank(okHttp.requestCharset)) {
            okHttp.requestCharset = DEFAULT_CHARSET;
        }
        if (StringUtils.isBlank(okHttp.responseCharset)) {
            okHttp.responseCharset = DEFAULT_CHARSET;
        }
        if (StringUtils.isBlank(okHttp.method)) {
            okHttp.method = DEFAULT_METHOD;
        }
        if (StringUtils.isBlank(okHttp.mediaType)) {
            okHttp.mediaType = DEFAULT_MEDIA_TYPE;
        }
        if (okHttp.requestLog) {
            //记录请求日志
            System.out.println(okHttp.toString());
        }

        String url = okHttp.url;

        Request.Builder builder = new Request.Builder();

        if (MapUtils.isNotEmpty(okHttp.queryMap)) {
            String queryParams = okHttp.queryMap.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.joining("&"));
            url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
        }
        builder.url(url);

        if (MapUtils.isNotEmpty(okHttp.headerMap)) {
            okHttp.headerMap.forEach(builder::addHeader);
        }

        String method = okHttp.method.toUpperCase();
        String mediaType = String.format("%s;charset=%s", okHttp.mediaType, okHttp.requestCharset);

        if (StringUtils.equals(method, GET)) {
            builder.get();
        } else if (ArrayUtils.contains(new String[] { POST, PUT, DELETE, PATCH }, method)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), okHttp.data);
            builder.method(method, requestBody);
        } else {
            throw new NotSupportedException(String.format("http method:%s not support!", method));
        }

        String result = "";
        try {
            Response response = CLIENT.newCall(builder.build()).execute();
            byte[] bytes = response.body().bytes();
            result = new String(bytes, okHttp.responseCharset);
            if (okHttp.responseLog) {
                //记录返回日志
                System.out.println(String.format("Got response->%s", result));
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return result;
    }




    @Builder
    @ToString(exclude = { "requestCharset", "responseCharset", "requestLog", "responseLog" })
    static class OkHttp {
        private String              url;
        @Builder.Default
        private String              method          = DEFAULT_METHOD;
        private String              data;
        @Builder.Default
        private String              mediaType       = DEFAULT_MEDIA_TYPE;
        private Map<String, String> queryMap;
        private Map<String, String> headerMap;
        @Builder.Default
        private String              requestCharset  = DEFAULT_CHARSET;
        @Builder.Default
        private boolean             requestLog      = DEFAULT_LOG;
        @Builder.Default
        private String              responseCharset = DEFAULT_CHARSET;
        @Builder.Default
        private boolean             responseLog     = DEFAULT_LOG;
    }
}

