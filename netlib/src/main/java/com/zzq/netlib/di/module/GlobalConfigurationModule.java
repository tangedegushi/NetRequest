package com.zzq.netlib.di.module;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.zzq.netlib.BuildConfig;
import com.zzq.netlib.di.scope.AppScope;
import com.zzq.netlib.error.ErrorHandle;
import com.zzq.netlib.utils.UtilCheck;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;


/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe
 */
@Module
public class GlobalConfigurationModule {
    private HttpUrl baseUrl;
    private NetModule.ConfigurationGson configGson;
    private NetModule.ConfigurationOkhttp configOkhttp;
    private NetModule.ConfigurationRetrofit configRetrofit;
    private NetModule.ConfigurationRxcache configRxcache;
    private NetModule.ConfigurationLoggingInterceptor configLogging;
    private NetModule.ConfigurationCacheFileDir configCacheFile;
    private ErrorHandle errorHandle;

    private GlobalConfigurationModule(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.configGson = builder.configGson;
        this.configOkhttp = builder.configOkhttp;
        this.configRetrofit = builder.configRetrofit;
        this.configRxcache = builder.configRxcache;
        this.configLogging = builder.configLogging;
        this.configCacheFile = builder.configCacheFile;
        this.errorHandle = builder.errorHandle;
    }

    @AppScope
    @Provides
    public HttpUrl provideBaseUrl() {
        return baseUrl;
    }

    @AppScope
    @Provides
    @Nullable
    public NetModule.ConfigurationGson provideConfigGson() {
        return configGson;
    }

    @AppScope
    @Provides
    @Nullable
    public NetModule.ConfigurationOkhttp provideConfigOkhttp() {
        return configOkhttp;
    }

    @AppScope
    @Provides
    @Nullable
    public NetModule.ConfigurationRetrofit provideConfigRetrofit() {
        return configRetrofit;
    }

    @AppScope
    @Provides
    @Nullable
    public NetModule.ConfigurationRxcache provideConfigRxcache() {
        return configRxcache;
    }

    @AppScope
    @Provides
    @Nullable
    public NetModule.ConfigurationLoggingInterceptor provideConfigLogging() {
        return configLogging;
    }

    @AppScope
    @Provides
    @Nullable
    public List<Interceptor> provideInterceptor() {
        return null;
    }

    @AppScope
    @Provides
    @Nullable
    public NetModule.ConfigurationCacheFileDir provideConfigCacheFile() {
        return configCacheFile;
    }

    @AppScope
    @Provides
    public ErrorHandle provideErrorHandle() {
        return errorHandle == null ? ErrorHandle.DEFAULT_HANDLE : errorHandle;
    }

    public static final class Builder {
        private HttpUrl baseUrl;
        private NetModule.ConfigurationGson configGson;
        private NetModule.ConfigurationOkhttp configOkhttp;
        private NetModule.ConfigurationRetrofit configRetrofit;
        private NetModule.ConfigurationRxcache configRxcache;
        private NetModule.ConfigurationLoggingInterceptor configLogging;
        private NetModule.ConfigurationCacheFileDir configCacheFile;
        private ErrorHandle errorHandle;

        public Builder() {
            if (configGson == null) {
                configGson = (context, builder) -> builder.serializeNulls();
            }
            if (baseUrl == null) {
                baseUrl = HttpUrl.parse(BuildConfig.baseUrl);
            }
        }

        /**
         * @param baseUrl 网络请求的基url
         */
        public Builder baseUrl(@NonNull String baseUrl) {
            String url = UtilCheck.checkNotNull(baseUrl, "baseUrl can not be null");
            this.baseUrl = HttpUrl.parse(url);
            return this;
        }


        /**
         * @param baseUrl 网络请求的基url
         */
        public Builder baseUrl(@NonNull HttpUrl baseUrl) {
            this.baseUrl = UtilCheck.checkNotNull(baseUrl, "%s can not be null", HttpUrl.class.getCanonicalName());
            return this;
        }

        /**
         * @param configGson 配置Gson
         */
        public Builder configGson(NetModule.ConfigurationGson configGson) {
            this.configGson = configGson;
            return this;
        }

        /**
         * @param configOkhttp 配置Okhttp
         */
        public Builder configOkhttp(NetModule.ConfigurationOkhttp configOkhttp) {
            this.configOkhttp = configOkhttp;
            return this;
        }

        /**
         * @param configRetrofit 配置Retrofit
         * @return
         */
        public Builder configRetrofit(NetModule.ConfigurationRetrofit configRetrofit) {
            this.configRetrofit = configRetrofit;
            return this;
        }

        /**
         * @param configRxcache 配置RxCache
         */
        public Builder configRxcache(NetModule.ConfigurationRxcache configRxcache) {
            this.configRxcache = configRxcache;
            return this;
        }

        /**
         * @param configLogging 设置网络请求的相关信息,默认情况下
         */
        public Builder configRequestLogging(NetModule.ConfigurationLoggingInterceptor configLogging) {
            this.configLogging = configLogging;
            return this;
        }


        /**
         * @param configCacheFile 设置缓存的目录
         */
        public Builder configCacheFile(NetModule.ConfigurationCacheFileDir configCacheFile) {
            this.configCacheFile = configCacheFile;
            return this;
        }

        public Builder processErrorHandle(ErrorHandle errorHandle) {
            this.errorHandle = errorHandle;
            return this;
        }

        public GlobalConfigurationModule build() {
            return new GlobalConfigurationModule(this);
        }

    }

    public static GlobalConfigurationModule getDefaultInstance(){
        return new GlobalConfigurationModule.Builder().build();
    }
}
