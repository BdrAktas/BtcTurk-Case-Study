package com.aktasbdr.btcchallenge.utils

import kotlin.LazyThreadSafetyMode.NONE

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(NONE, initializer)
