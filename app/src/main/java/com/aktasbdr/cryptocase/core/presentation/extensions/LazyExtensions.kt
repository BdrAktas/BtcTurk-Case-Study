package com.aktasbdr.cryptocase.core.presentation.extensions

import kotlin.LazyThreadSafetyMode.NONE

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(NONE, initializer)
