package com.github.sauterl.demeter.cineast

import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
class Item(@JsonProperty("object") val theObject: Object, val metadata: List<MetaData>, val uri: URI) {

    companion object {
        data class MetaData(val key: String, val value: String, val domain: Domain = Domain.WEB) {
            companion object {
                enum class Domain {

                    WEB;

                    override fun toString(): String {
                        return name.capitalize()
                    }
                }
            }
        }

        data class Object(val name: String, val mediatype: MediaType = MediaType.IMAGE) {

            enum class MediaType {
                IMAGE
            }
        }
    }

}
