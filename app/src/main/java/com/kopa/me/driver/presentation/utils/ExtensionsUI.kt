package com.kopa.me.driver.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showToast(text: String, length: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(this.context, text, length).show()

/**
 * Show a SnackBar with [messageRes] resource
 */
fun View.showSnack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT) =
	snack(messageRes, length) {}

/**
 * Show a SnackBar with [message] string
 */
fun View.showSnack(message: String, length: Int = Snackbar.LENGTH_SHORT) =
	snack(message, length) {}


/**
 * Show a SnackBar with [messageRes] resource, execute [f] and show it
 * buttonSubmit.snack(R.string.name_submitted, SnackBar.LENGTH_LONG, { action() })
 */
inline fun View.snack(@StringRes messageRes: Int,
                      length: Int = Snackbar.LENGTH_SHORT,
                      f: Snackbar.() -> Unit) {
	val snack = Snackbar.make(this, messageRes, length)
	snack.f()
	snack.show()
}

/**
 * Show a SnackBar with [message] string, execute [f] and show it
 * buttonSubmit.snack(R.string.name_submitted, SnackBar.LENGTH_LONG, { action() })
 */
inline fun View.snack(message: String,
                      length: Int = Snackbar.LENGTH_SHORT,
                      f: Snackbar.() -> Unit) {
	val snack = Snackbar.make(this, message, length)
	snack.f()
	snack.show()
}

/**
 * Show the view (visibility = View.VISIBLE)
 */
fun View.visible() : View {
	if (visibility != View.VISIBLE) {
		visibility = View.VISIBLE
	}
	return this
}

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.visibleIf(condition: () -> Boolean) : View {
	if (visibility != View.VISIBLE && condition()) {
		visibility = View.VISIBLE
	}
	return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.gone() : View {
	if (visibility != View.GONE) {
		visibility = View.GONE
	}
	return this
}

/**
 * Remove the view if [condition] returns true
 * (visibility = View.GONE)
 */
inline fun View.goneIf(condition: () -> Boolean) : View {
	if (visibility != View.GONE && condition()) {
		visibility = View.GONE
	}
	return this
}

/**
 * Hide the view if [condition] returns true
 * (visibility = View.INVISIBLE)
 */
inline fun View.invisibleIf(condition: () -> Boolean) : View {
	if (visibility != View.INVISIBLE && condition()) {
		visibility = View.GONE
	}
	return this
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
	val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	this.requestFocus()
	imm.showSoftInput(this, 0)
}
/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(inputViewFocused: View? = null): Boolean {
	try {
		val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		return inputMethodManager.hideSoftInputFromWindow(applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
	} catch (ignored: RuntimeException) { }
	finally { inputViewFocused?.clearFocus() }
	return false
}
