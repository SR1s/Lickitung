package me.sr1.lickitung.huawei.protocol;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 华为登陆授权API
 * @author SR1
 */

public interface HuaweiOauthApi {

    @FormUrlEncoded
    @POST("https://login.vmall.com/oauth2/token")
    Call<OauthResponse> requestAccessToken(@Field("grant_type") String grantType,
                                          @Field("client_secret") String appSecret,
                                          @Field("client_id") String appId,
                                          @Field("scope") String scope);

    class OauthResponse {

        @SerializedName("access_token")
        public final String AccessToken;

        @SerializedName("expires_in")
        public final long ExpiresIn;

        @SerializedName("scope")
        public final String Scope;

        @SerializedName("error")
        public final int Error;

        @SerializedName("error_description")
        public final String ErrorDescription;

        public OauthResponse(String accessToken,
                             long expiresIn,
                             String scope,
                             int error,
                             String errorDescription) {
            AccessToken = accessToken;
            ExpiresIn = expiresIn;
            Scope = scope;
            Error = error;
            ErrorDescription = errorDescription;
        }

        @Override
        public String toString() {
            return "OauthResponse{" +
                    "AccessToken='" + AccessToken + '\'' +
                    ", ExpiresIn=" + ExpiresIn +
                    ", Scope='" + Scope + '\'' +
                    ", Error=" + Error +
                    ", ErrorDescription='" + ErrorDescription + '\'' +
                    '}';
        }
    }
}
