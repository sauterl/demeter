# Demeter Crawler

Demeter (greek olympean goddess of harvest) is a 
social media crawling project.

Its main purpose is the _harvesting_ of (social) multimedia related to a certain tag.

## Setup

This is a [Kotlin](https://kotlinlang.org/) project using [Gradle](https://gradle.org/).
Thus all dependencies are handled by the gradle buildfile.
Although this project is being developed with the Oracle JDK,
it might work with others as well.


## Goal

Eventually _demeter_ will continously query a subset of common social media services
(such as [Twitter](https://twitter.com), [Flickr](https://flickr.com) )
for multimedia content (e.g. images) and send this multimedia items to the extraction API of [cineast](https://github.com/vitrivr/cineast/).
_demeter_ keeps track of already downloaded items and will send each unique item only once to cineast.

## Software Quality

Since this is a very small project, which one of the goals is to get (more) familiar with Kotlin, the software quality is most probably very low.

## Contributors

Concepts: luca.rossetto@unibas.ch

Code: loris.sauter@unibas.ch

---

_TODO: More to come_
