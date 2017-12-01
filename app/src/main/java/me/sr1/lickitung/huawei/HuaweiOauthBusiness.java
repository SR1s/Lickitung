package me.sr1.lickitung.huawei;

import android.util.Log;

import java.net.HttpURLConnection;

import me.sr1.lickitung.huawei.protocol.HuaweiOauthApi;
import me.sr1.lickitung.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HuaweiOauthBusiness {

    private static final String TAG = "HuaweiOauthBusiness";

    public interface BusinessCallback<ResponseType, Error> {
        void onSuccess(ResponseType data);
        void onError(Error message);
    }

    public static class Token {
        public final String mToken;
        public final long mExpriesMs;

        public Token(String token, long expries) {
            mToken = token;
            mExpriesMs = expries;
        }
    }

    public void requestAccessToken(String appId, String appSecret,
                                   final BusinessCallback<Token, String> callback) {
        HuaweiOauthApi oauthApi = RetrofitProvider
                .getRetrofit("https://login.vmall.com/")
                .create(HuaweiOauthApi.class);

        oauthApi.requestAccessToken("client_credentials",
                appSecret,
                appId,
                "").enqueue(new Callback<HuaweiOauthApi.OauthResponse>() {
            @Override
            public void onResponse(Call<HuaweiOauthApi.OauthResponse> call, Response<HuaweiOauthApi.OauthResponse> response) {
                Log.i(TAG, "onResponse=" + response + ", data=" + response.body());

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    callback.onSuccess(new Token(response.body().AccessToken, response.body().ExpiresIn));
                } else {
                    callback.onError("请求失败");
                }
            }

            @Override
            public void onFailure(Call<HuaweiOauthApi.OauthResponse> call, Throwable t) {
                Log.i(TAG, "onFailure", t);
                callback.onError("请求失败");
            }
        });
    }

}
