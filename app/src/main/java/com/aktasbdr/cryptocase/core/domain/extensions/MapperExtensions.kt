package com.aktasbdr.cryptocase.core.domain.extensions

interface Mapper<in Input, out Output> {
    fun map(input: Input): Output
}

fun <MapperClass : Mapper<Input, Output>, Input, Output> Input.mapWith(
    mapperClass: MapperClass
): Output = mapperClass.map(this)

fun <Input, Output> Input.mapWith(
    mapper: (Input) -> Output
): Output = mapper(this)
