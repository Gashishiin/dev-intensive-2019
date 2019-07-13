package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.isKeyboardOpen():Boolean {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50F, this.resources.displayMetrics)
    val bounds = Rect()
    val rootView = findViewById<View>(android.R.id.content)
    rootView.getWindowVisibleDisplayFrame(bounds)
    val heightDiff = rootView.height - bounds.height()
    val marginError = Math.round(px)
    return heightDiff > marginError
}

fun Activity.isKeyboardClosed():Boolean = !isKeyboardOpen()