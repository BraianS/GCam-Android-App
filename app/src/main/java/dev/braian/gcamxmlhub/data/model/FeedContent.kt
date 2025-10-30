package dev.braian.gcamxmlhub.data.model



open class FeedContent (
    open val id: String,
    open val title: String,
    open val description: String,
    open val user: User,
    open val date: String,
    open val likes: Int,
    open val type: ContentType,
    open val imageUrl:String = ""
)

