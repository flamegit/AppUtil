package com.example.flame.kotlinstudy.model

/**
 * Created by flame on 2018/2/2.
 */
class Constants{

    companion object {
        val EMPTY:Int=1
        val ERROR:Int=2
        val LOADING: Int=3
        val COMPLETE:Int=4

        val ENDURL = "http://www.mzitu.com"
        val PATH = "/page/"
        val INDEX = "index"
        val URL = "url"
        val DEC = "desc"
        val TYPE = "type"
        val CHANGE_TYPE_ACTION = "com.flame.changeType"
        val SHOW_DETAIL_ACTION = "com.flame.showDetail"
        val SHOW_FAVORITE_ACTION = "com.flame.showFavorite"
        val SHOW_TAG_ACTION = "com.flame.showTag"
        val HOME = 0
        val CATEGORY = 1
        val TAG = 2
        val FAVORITE = 3
    }
}