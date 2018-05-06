package com.github.sauterl.demeter.api

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
interface ImageProvider<T> {

  fun serve(tag: String): List<ConcreteImage<T>>
}
