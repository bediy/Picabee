package com.example.picabee.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ImageEntity(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)

@Entity(tableName = "image_hit")
data class Hit(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int,
    val id: Int,
    val comments: Int,
    val downloads: Int,
    val favorites: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val user_id: Int,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
)