package com.sage.demo0809.app;

import com.sage.demo0809.bean.BaseParams;
import com.sage.demo0809.bean.BaseReply;
import com.sage.demo0809.bean.LoginParam;
import com.sage.demo0809.bean.LoginReply;
import com.sage.demo0809.bean.MyRecordChart;
import com.sage.demo0809.bean.MyRecordParams;
import com.sage.demo0809.bean.UploadAvatar;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Sage on 2016/9/5.
 */
public interface ApiService {

    public static  String HOST_ADDRESS="http://hrv.kuailexinli.com/";
//    public static  String HOST_ADDRESS="http://www.sunrisin.com:8088/";

    @Multipart
    @POST("user/avatar")
    Observable<UploadAvatar> uploadAvatar(@Body BaseParams baseParams,@Query("key1") String key1,@Query("key2") String key2);

    @POST("user/test")
    Observable<MyRecordChart> test(@Body BaseParams baseParams,@Query("key1") String key1,@Query("key2") String key2);

    @Multipart
    @POST("user/avatar")
    Observable<UploadAvatar> uploadAvatar2(@Part BaseParams baseParams, @PartMap Map<String,RequestBody> map);

    @POST("api1/myRecord")
    Observable<BaseReply<MyRecordChart>> getTodayInfo(@Body MyRecordParams baseParams);
    @POST("api1/myRecord")
    Observable<BaseReply<MyRecordChart>> getTodayInfo(@QueryMap Map<String,String> map);

    @POST("api1/myRecord")
    Observable<BaseReply<MyRecordChart>> getTodayInfo(@Query("mobile_phone") String phone,@Query("password") String password,
                                           @Query("version") String version,@Query("channel") String channel, @Query("search") String search, @Query("page") String page);

//    @Headers({"x-ua: 0","Content-Type: application/x-www-form-urlencoded"})
    @POST("sso/mobile/login")
    Observable<LoginReply> login(@Body LoginParam loginParam);
}
