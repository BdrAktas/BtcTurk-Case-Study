package com.aktasbdr.cryptocase.core.presentation.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.aktasbdr.cryptocase.R
import com.google.android.material.snackbar.Snackbar

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun View.showError(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(ContextCompat.getColor(context, R.color.error_background))
        setTextColor(ContextCompat.getColor(context, R.color.error_text))
        setActionTextColor(ContextCompat.getColor(context, R.color.error_action_text))
        setAction("Dismiss") { dismiss() }
       }.show()
}