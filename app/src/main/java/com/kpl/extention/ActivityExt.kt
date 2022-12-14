package com.kpl.interfaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.kpl.R


inline fun <reified T : Activity> AppCompatActivity.goToActivityAndClearTask() {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    Animatoo.animateCard(this)
    finish()
}

inline fun <reified T : Activity> Fragment.goToActivityAndClearTask() {
    val intent = Intent(context, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    activity?.finish()
}

inline fun <reified T : Activity> AppCompatActivity.goToActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    Animatoo.animateCard(this)
}

inline fun <reified T : Activity> Fragment.goToActivity() {
    startActivity(Intent(activity, T::class.java))
    Animatoo.animateCard(activity)
}

fun AppCompatActivity.addFragments(fragments: List<Fragment>, containerId: Int) {
    fragments.forEach {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(containerId, it)
        ft.commitAllowingStateLoss()
    }
}
inline fun <reified T : Activity> AppCompatActivity.onBackPress() {
    Animatoo.animateSlideLeft(this)
     finish()
}

fun AppCompatActivity.replaceFragments(fragments: List<Fragment>, containerId: Int) {
    fragments.forEach {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(containerId, it)
        ft.commitAllowingStateLoss()
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, containerId: Int) {
    val ft = supportFragmentManager.beginTransaction()
    ft.replace(containerId, fragment)
    ft.commitAllowingStateLoss()
}

fun Fragment.replaceFragment(fragment: Fragment, containerId: Int) {
    val ft = fragmentManager?.beginTransaction()
    ft?.replace(containerId, fragment)
    ft?.commitAllowingStateLoss()
}

fun AppCompatActivity.addFragment(
    fragment: Fragment,
    containerId: Int,
    addToStack: Boolean = true
) {
    val ft = supportFragmentManager.beginTransaction()
    ft.add(containerId, fragment)
    if (addToStack) ft.addToBackStack(fragment.javaClass.name)
    ft.commitAllowingStateLoss()
}

fun Fragment.addFragment(fragment: Fragment, containerId: Int, addToStack: Boolean = true) {
    val ft = fragmentManager?.beginTransaction()
    ft?.add(containerId, fragment)
    if (addToStack) ft?.addToBackStack(fragment.javaClass.name)
    ft?.commitAllowingStateLoss()
}

fun AppCompatActivity.showFragment(fragment: Fragment) {
    val ft = supportFragmentManager.beginTransaction()
    ft.show(fragment)
    ft.commitAllowingStateLoss()
}

fun AppCompatActivity.hideFragment(fragment: Fragment) {
    val ft = supportFragmentManager.beginTransaction()
    ft.hide(fragment)
    ft.commitAllowingStateLoss()
}

fun statusbarColor(activity: AppCompatActivity, color:Int) {
    val window: Window = activity.getWindow()
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        // finally change the color
        window.setStatusBarColor(color);
    }
}
/*fun Activity.setStatusBarNormal() {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            statusBarColor = getColorCompat(R.color.colorPrimaryDark)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}*/

fun Context.getColorCompat(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

