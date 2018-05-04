package com.github.sauterl.demeter.cineast

import com.github.sauterl.demeter.api.ConcreteImage

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
interface MetaDataExtractor<T> {

  fun extract(img: ConcreteImage<T>): List<Item.Companion.MetaData>

  operator fun invoke(img: ConcreteImage<T>): List<Item.Companion.MetaData> = extract(img)
}
