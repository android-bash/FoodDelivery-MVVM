package com.kopa.me.driver.presentation.utils

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop


/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getStringRes(@StringRes stringRes: Int): String = resources.getString(stringRes)


inline fun <T: View> T.setDebounceOnClick(debounceTime: Long = 1000L,
                                          crossinline block: T.() -> Unit) =
	setOnClickListener {
		when {
			tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
			else -> {
				tag = System.currentTimeMillis() + debounceTime
				block()
			}
		}
	}

inline fun <T: View> T.setSingleOnClick(crossinline block: T.() -> Unit) = setOnClickListener {
	block()
	it.isClickable = false
	setOnClickListener(null)
}

inline fun <T: TextView> T.setOnClickWithSelection(crossinline block: T.() -> Unit) = setOnClickListener {
	block()
	it.isSelected = true
}

fun View.updateMargins(
	@Px start: Int = marginStart,
	@Px top: Int = marginTop,
	@Px end: Int = marginEnd,
	@Px bottom: Int = marginBottom
) {
	val params = layoutParams as MarginLayoutParams
	params.setMargins(start, top, end, bottom)
	this.layoutParams = params
}


fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()