package com.example.challengeevenly.model

// Convenience functions for computed properties
// Place
fun Place.getFoursquareURL(): String = "https://de.foursquare.com/v/${this.name}/${this.fsqID}"
fun Place.getCategoryIconURL(): String = "${this.categories[0].icon.prefix}64${this.categories[0].icon.suffix}"
fun Place.getPostcodeLocality(): String = "${this.location.postcode} ${this.location.locality}"
// Photo
fun Photo.getLinkToOriginalSize(): String = "${this.prefix}original${this.suffix}"
fun Photo.getLinkWithDimensions(width: Int, height: Int): String = "${this.prefix}${width}x${height}${this.suffix}"
fun Photo.getLinkToThumbnail(size: Int): String = "${this.prefix}${size}${this.suffix}"