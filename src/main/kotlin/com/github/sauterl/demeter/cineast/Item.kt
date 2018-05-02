package com.github.sauterl.demeter.cineast

import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
data class Item(@JsonProperty("object")val theObject:Object,val metadata:Array<MetaData>,val uri: URI)