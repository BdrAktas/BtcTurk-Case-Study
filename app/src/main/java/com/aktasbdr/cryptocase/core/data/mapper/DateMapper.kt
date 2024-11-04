package com.aktasbdr.cryptocase.core.data.mapper

import android.annotation.SuppressLint
import java.util.*

@SuppressLint("NewApi")
fun Date.toEpochSecond() = this.toInstant().epochSecond
