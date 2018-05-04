package com.github.sauterl.demeter.cineast

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
@FunctionalInterface interface ExtractionBuilder {

  fun build(images: List<AbstractImage>): ExtractionContainer {
    return ExtractionContainer(images.map {
      val metaData = mutableListOf<Item.Companion.MetaData>()
      metaData.add(Item.Companion.MetaData("source", it.sourceUrl))
      metaData.add(Item.Companion.MetaData("id", it.id))
      val metas = createMetaDataList(it)
      metas.forEach {
        if (!metaData.contains(it)) {
          metaData.add(it)
        }
      }
      return@map Item(Item.Companion.Object(it.name), metaData, it.path)
    })
  }

  /**
   * Contract: Return at least empty list
   */
  fun createMetaDataList(image: AbstractImage): List<Item.Companion.MetaData>

}
