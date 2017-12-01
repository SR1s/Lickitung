package me.sr1.lickitung.huawei;

import com.google.gson.annotations.SerializedName;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 华为推送API
 * @author SR1
 */

public interface HuaweiPushApi {

    class NspCtx {

        @SerializedName("ver")
        public String Version = "1";

        @SerializedName("appId")
        public String AppId;
    }

    @FormUrlEncoded
    @POST("https://api.push.hicloud.com/pushsend.do")
    Call<ResponseBody> sendMessage(@Query("nsp_ctx") String nspCtx,
                                   @Field("access_token") String accessToken,
                                   @Field("nsp_svc") String api,
                                   @Field("nsp_ts") long timestamp,
                                   @Field("device_token_list")String targetTokens,
                                   @Field("payload") String payload);

}
