package com.github.sauterl.demeter.twitter

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
class SimpleTwitterCrawler {

    fun test(){

    }

    fun createAuth(){
        val oauthPairs: MutableList<Pair<String,String>> = mutableListOf()

        oauthPairs.add(Pair("oauth_consumer_key", TWITTER_PUBLIC))
        oauthPairs.add(Pair("oauth_nonce", "kYjz1238Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg")) // TODO howto generate nonce
        oauthPairs.add(Pair("oauth_signature", "<signature>"))
        oauthPairs.add(Pair("oauth_signature_method", "HMAC-SHA1"))

    }


}