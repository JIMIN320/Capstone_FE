package com.example.whenandwhere

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val URL = "http://43.201.72.114:8080"

class HttpUtil {
    // JWT를 SharedPreference에서 가져오는 함수
    fun getJWTFromSharedPreference(context : Context): String? {
        val sharedPref = context.getSharedPreferences("com.example.whenandwhere.JWT",
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPref.getString("jwt", null)
    }

    // JWT를 SharedPreference에 저장하는 함수
    fun saveJWTToSharedPreference(context : Context, jwt: String) {
        val sharedPref = context.getSharedPreferences("com.example.whenandwhere.JWT",
            AppCompatActivity.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putString("jwt", jwt)
            apply()
        }
    }

    fun createClient(jwt : String?) : OkHttpClient{
        // 헤더 세팅
        val client = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val originalRequest = chain.request()
                if (jwt != null) {
                    val requestWithToken = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $jwt")
                        .build()
                    chain.proceed(requestWithToken)
                } else {
                    chain.proceed(originalRequest)
                }
            }
        }.
        connectTimeout(100, TimeUnit.SECONDS) // 연결 시간 제한을 30초로 설정
        .readTimeout(100, TimeUnit.SECONDS) // 읽기 시간 제한을 30초로 설정
        .build()

        return client
    }

    fun createRetrofit() : Retrofit{
        // 빈 헤더 추가
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS) // 연결 시간 제한을 30초로 설정
            .readTimeout(100, TimeUnit.SECONDS) // 읽기 시간 제한을 30초로 설정
            .build()

        // Retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl(URL) // 로컬 서버의 IP 주소와 포트를 지정합니다.
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    fun createRetrofitWithHeader(client : OkHttpClient?) : Retrofit{
        var retrofit: Retrofit ? = null

        if(client != null){
            retrofit = Retrofit.Builder()
                .baseUrl(URL) // 로컬 서버의 IP 주소와 포트를 지정합니다.
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
        else{
            // 빈 헤더 추가
            val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS) // 연결 시간 제한을 30초로 설정
                .readTimeout(100, TimeUnit.SECONDS) // 읽기 시간 제한을 30초로 설정
                .build()

            // Retrofit 객체 생성
            retrofit = Retrofit.Builder()
                .baseUrl(URL) // 로컬 서버의 IP 주소와 포트를 지정합니다.
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}