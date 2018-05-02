package com.github.sauterl.demeter.cineast

import java.net.URI

/**
 * TODO: write JavaDoc
 * @author loris.sauter
 */
data class AbstractImage(val id:String, val name:String, val sourceUrl:String){
    lateinit var path:URI
    lateinit var sha256:String

    constructor(id:String, name:String, sourceUrl:String, path:URI, sha:String) : this(id,name,sourceUrl) {
        this.path = path
        this.sha256=sha
    }
}
