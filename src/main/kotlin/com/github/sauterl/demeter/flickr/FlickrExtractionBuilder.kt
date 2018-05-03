package com.github.sauterl.demeter.flickr

import com.github.sauterl.demeter.cineast.AbstractImage
import com.github.sauterl.demeter.cineast.ExtractionBuilder
import com.github.sauterl.demeter.cineast.Item

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class FlickrExtractionBuilder : ExtractionBuilder {

  override fun createMetaDataList(image: AbstractImage): List<Item.Companion.MetaData> {
    return emptyList()
  }


}
