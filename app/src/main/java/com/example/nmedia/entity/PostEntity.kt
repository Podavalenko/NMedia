package com.example.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nmedia.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    val likes: Long,
    val reposts: Long,
    val videoUrl: String?,
    val wasRead: Boolean,
    //val attachment: Attachment?
) {
    fun toDto() = Post(id, author, authorAvatar, content, published, likedByMe, likes, reposts, videoUrl, wasRead/*, attachment*/)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.authorAvatar, dto.content, dto.published, dto.likedByMe, dto.likes, dto.reposts, dto.videoUrl, dto.wasRead/*, dto.attachment*/)
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)