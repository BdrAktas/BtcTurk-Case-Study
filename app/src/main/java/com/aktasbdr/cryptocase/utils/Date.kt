package com.aktasbdr.cryptocase.utils

import android.annotation.SuppressLint
import java.util.*

@SuppressLint("NewApi")
fun Date.toEpochSecond() = this.toInstant().epochSecond
