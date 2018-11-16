package com.example.flame.kotlinstudy.datasource.net
import com.example.flame.kotlinstudy.model.HttpGirl
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


/**
 * Created by flame on 2018/2/2.
 */
interface ApiService {

    @GET("data/福利/10/{page}")
    fun getGirlList(@Path("page") page: Int): Call<HttpGirl>

    @GET()
    fun getImage(@Url url:String): Observable<ResponseBody>

}