package com.github.sauterl.demeter.api

import com.github.sauterl.demeter.cineast.AbstractImage

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
data class ConcreteImage<T>(val rep:AbstractImage, val img:T)
