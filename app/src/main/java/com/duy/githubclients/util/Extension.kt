package com.duy.githubclients.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.SearchView

fun TextView.setResError(resError: Int?) {
    resError?.let {
        error = context.resources.getString(it)
    } ?: setError(null)
}

fun Activity.hideKeyboard() = currentFocus?.let {
    val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(it.windowToken, 0)
}

fun SearchView.onQueryTextChange(action: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            action.invoke(query)
            return true
        }
        override fun onQueryTextChange(newText: String): Boolean {
            return true
        }
    })
}
