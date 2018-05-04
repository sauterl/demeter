package com.github.sauterl.demeter.api

import com.github.sauterl.demeter.cineast.AbstractImage

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
interface AbstractImageRetriever<T> {

    fun retrieve(tag:String):List<ConcreteImage<T>>
}
