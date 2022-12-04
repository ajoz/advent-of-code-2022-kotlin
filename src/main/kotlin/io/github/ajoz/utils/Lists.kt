package io.github.ajoz.utils

val <A> List<A>.head: A
    get() = first()

val <A> List<A>.tail: List<A>
    get() = drop(1)