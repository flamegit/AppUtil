package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.R


class TestWebSocketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_web_socket)

//        val client = OkHttpClient.Builder()
//                .build()
//        //构造request对象
//        val request = Request.Builder()
//                .url("")
//                .build()
//        //建立连接
//
//        client.newWebSocket(request, object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: Response) {
//                println("client onOpen")
//                System.out.println("client request header:" + response.request().headers())
//                System.out.println("client response header:" + response.headers())
//                println("client response:$response")
//            }
//
//            override fun onMessage(webSocket: WebSocket?, text: String?) {
//                println("client onMessage")
//                println("message:" + text!!)
//            }
//
//            override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
//                println("client onClosing")
//                println("code:$code reason:$reason")
//            }
//
//            override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
//                println("client onClosed")
//                println("code:$code reason:$reason")
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response) {
//                //出现异常会进入此回调
//                println("client onFailure")
//                println("throwable:$t")
//                println("response:$response")
//            }
//        })
    }


}

