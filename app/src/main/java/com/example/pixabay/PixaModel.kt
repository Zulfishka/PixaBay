package com.example.pixabay

data class PixaModel (
    val hits : ArrayList <ImageModel>
)

data class ImageModel (
    val largeImageURL: String,
        )
