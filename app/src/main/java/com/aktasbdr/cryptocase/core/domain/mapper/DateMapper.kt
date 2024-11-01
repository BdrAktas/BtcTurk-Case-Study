package com.aktasbdr.cryptocase.core.domain.mapper

import android.annotation.SuppressLint
import java.util.*

@SuppressLint("NewApi")
fun Date.toEpochSecond() = this.toInstant().epochSecond
