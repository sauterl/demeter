package com.github.sauterl.demeter.cineast



/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
data class Object(val name:String, val mediatype:String?){
    companion object {
        const val MEDIA_TYPE_IMAGE = "IMAGE"
    }
}