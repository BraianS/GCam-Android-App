package dev.braian.gcamxmlhub.data.model

data class GcamXml(
    override val id:String,
    override var title:String,
    override var description:String,
    override var user: User,
    override var date:String,
    override var likes:Int,
    override var type: ContentType,
    override var imageUrl:String = "",

    val downloadCount: Int = 0,
    val requiredGCamVersion: String,
    val compatibleDevices:String
) : FeedContent(id = id, title = title, description = description, user = user, date = date, likes = likes, type = type)
