package com.github.sauterl.demeter.twitter

import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.cineast.ExtractionBuilder
import com.github.sauterl.demeter.cineast.Item

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class TwitterExtractionBuilder : ExtractionBuilder {
  override fun createMetaDataList(image: AbstractImage): List<Item.Companion.MetaData> {
    return emptyList()
  }
}
