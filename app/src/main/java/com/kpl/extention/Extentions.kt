package com.kpl.extention

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kpl.R
import com.kpl.interfaces.SnackbarActionListener
import java.util.*


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun EditText.getValue(): String {
    return this.text.toString().trim()
}

fun TextView.getValue(): String {
    return this.text.toString().trim()
}

fun EditText.isEmpty(): Boolean {
    return this.text.trim().isEmpty()
}

fun TextView.isEmpty(): Boolean {
    return this.text.isNullOrEmpty()
}

fun View.showSnackBar(msg: String, duration: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, msg, duration)
    val sbView = snack.view
    val textView = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snack.show()
}

fun View.showSnackBar(
    msg: String,
    LENGTH: Int = Snackbar.LENGTH_INDEFINITE,
    action: String,
    actionListener: SnackbarActionListener?
) {
    val snackbar = Snackbar.make(this, msg, LENGTH)
    snackbar.setActionTextColor(Color.WHITE)
    if (actionListener != null) {
        snackbar.setAction(action) { view1 ->
            snackbar.dismiss()
            actionListener.onAction()
        }
    }
    val sbView = snackbar.view
    val textView = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snackbar.show()
}

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Calendar.isSameDay(newDate: Calendar): Boolean =
    this.get(Calendar.DAY_OF_MONTH) == newDate.get(Calendar.DAY_OF_MONTH)

fun Calendar.isSameMonth(newDate: Calendar): Boolean =
    this.get(Calendar.MONTH) == newDate.get(Calendar.MONTH)

/*fun TabLayout.changeTabsFont(
    context: Context,
    font: Int = R.font.clan_ot_medium,
    fontSize: Float = 12f
) {
    for (i in 0 until this.tabCount) {

        val tab = this.getTabAt(i)
        if (tab != null) {

            val tabTextView = TextView(context)
            tab.customView = tabTextView

            tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

            tabTextView.text = tab.text
            tabTextView.gravity = Gravity.CENTER
            tabTextView.typeface = ResourcesCompat.getFont(context, font)
            tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            // First tab is the selected tab, so if i==0 then set BOLD typeface
            if (i == 0) {
                tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                tabTextView.typeface = ResourcesCompat.getFont(context, font)
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            }

        }

    }
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(selectedTab: TabLayout.Tab?) {
            println("FragmentContest.onTabReselected")
        }

        override fun onTabUnselected(unselectedTab: TabLayout.Tab?) {
            println("FragmentContest.onTabUnselected: " + unselectedTab!!.text)

            val tabTextView = unselectedTab.customView as TextView
            tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            tabTextView.typeface = ResourcesCompat.getFont(context, font)
            tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        }

        override fun onTabSelected(selectedTab: TabLayout.Tab?) {
            println("FragmentContest.onTabSelected: " + selectedTab!!.text)
            val tabTextView = selectedTab.customView as TextView

            tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            tabTextView.typeface = ResourcesCompat.getFont(context, font)
            tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        }
    })
}*/

var dialog: AlertDialog? = null
fun AppCompatActivity.showAlert(msg: String, cancelable: Boolean = false) {
    dialog = AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
        .setMessage(msg)
        .setCancelable(cancelable)
        .setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which -> dialog.dismiss() }
        .create()

    dialog?.show()
}

fun Fragment.showAlert(msg: String, cancelable: Boolean = false) {
    dismissAlertDialog()
    dialog = AlertDialog.Builder(context, R.style.MyAlertDialogTheme)
        .setTitle(context?.getString(R.string.app_name))
        .setMessage(msg)
        .setCancelable(cancelable)
        .setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which -> dialog.dismiss() }
        .create()
    dialog?.show()
}

fun dismissAlertDialog() {
    dialog?.dismiss()
}

inline
val AppCompatActivity.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager