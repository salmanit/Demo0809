package com.sage.demo0809.app;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 * Created by Sage on 2016/9/5.
 */
public class ApiManager {

    public static ApiService service;

    public static ApiService getService() {
        if(service==null){
            init();
        }
        return service;
    }

    public static void init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.HOST_ADDRESS)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
                .build();

         service = retrofit.create(ApiService.class);
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                //  .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("x-ua", "0")
                                .addHeader("x-sid", "jiajia.user.session.b34fa450-3630-11e6-bc2b-3f0bc6e3147a")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return httpClient;
    }

   public static class ChunkingConverterFactory extends Converter.Factory {
        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                              Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            boolean isBody = false;

            for (Annotation annotation : parameterAnnotations) {
                isBody |= annotation instanceof Body;

            }
            if (!isBody ) {
                return null;
            }

            // Look up the real converter to delegate to.
            final Converter<Object, RequestBody> delegate =
                    retrofit.nextRequestBodyConverter(this, type, parameterAnnotations, methodAnnotations);
            // Wrap it in a Converter which removes the content length from the delegate's body.
            return new Converter<Object, RequestBody>() {
                @Override public RequestBody convert(Object value) throws IOException {
                    final RequestBody realBody = delegate.convert(value);
                    return new RequestBody() {
                        @Override public MediaType contentType() {
                            return realBody.contentType();
                        }

                        @Override public void writeTo(BufferedSink sink) throws IOException {
                            realBody.writeTo(sink);
                        }
                    };
                }
            };
        }
    }




}
