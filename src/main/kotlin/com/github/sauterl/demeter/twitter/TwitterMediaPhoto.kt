package com.github.sauterl.demeter.twitter

/**
 * TODO: Write JavaDoc
 * @author loris.sauter
 */
data class TwitterMediaPhoto(
    val tweet : TwitterResponse.Companion.TwitterTweet,
    val media : TwitterResponse.Companion.TwitterMedia
)