package com.example.flame.kotlinstudy.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Site

/**
 * Created by flame on 2018/2/17.
 */
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun <T : Activity> Context.openActivity(activity: Class<T>) {
    val intent = Intent(this, activity)
    startActivity(intent)
}

fun <T : Activity> Context.openActivity(activity: Class<T>, key: String, value: Any?) {
    val intent = Intent(this, activity)
    if(value is String?){
        intent.putExtra(key, value)
    }else if(value is Boolean?){
        intent.putExtra(key, value)
    }
    startActivity(intent)
}

fun <T : Activity> Context.openActivity(activity: Class<T>, key: String, value: Int) {
    val intent = Intent(this, activity)
    intent.putExtra(key, value)
    startActivity(intent)
}

fun <T : Activity> Context.openActivity(activity: Class<T>, firstKey: String, firstValue: Int, secondKey: String, secondValue: String?) {
    val intent = Intent(this, activity)
    intent.putExtra(firstKey, firstValue)
    intent.putExtra(secondKey, secondValue)
    startActivity(intent)
}

fun <T : Activity> Context.openActivity(activity: Class<T>, firstKey: String, firstValue: String?, secondKey: String, secondValue: String?) {
    val intent = Intent(this, activity)
    intent.putExtra(firstKey, firstValue)
    intent.putExtra(secondKey, secondValue)
    startActivity(intent)
}

fun <T : Activity> Context.openActivity(activity: Class<T>, firstKey: String, firstValue: Int, secondKey: String, secondValue: Int) {
    val intent = Intent(this, activity)
    intent.putExtra(firstKey, firstValue)
    intent.putExtra(secondKey, secondValue)
    startActivity(intent)
}

fun Context.setRobotoFont(textView: TextView) {
    val mgr = assets
    val tf = Typeface.createFromAsset(mgr, "fonts/RobotoCondensed-Bold.ttf")
    textView.typeface = tf
}

fun Context.dpToPx(dp: Int): Int {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)
    return px.toInt()
}

fun Context.getCurrSite(): Int {
    return this.getSharedPreferences(Constants.KEY_CONFIG, Context.MODE_PRIVATE).getInt(Constants.KEY_SITE_TYPE, 0)
}

fun Context.saveCurrSite(siteType: Int) {
    this.getSharedPreferences(Constants.KEY_CONFIG, Context.MODE_PRIVATE)
            .edit().putInt(Constants.KEY_SITE_TYPE, siteType).apply()
}

fun createShape(color: Int, radius: Int): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.cornerRadius = radius.toFloat()
    //drawable.setStroke()
    drawable.setColor(color)
    return drawable
}

fun createGlideUrl(url: String?): GlideUrl? {
    url?.let {
        return GlideUrl(it, LazyHeaders.Builder().addHeader("Referer", Site.currSite?.endUrl).build())
    }
    return null
}