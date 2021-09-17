package com.swein.shjetpackcompose.examples.schedulenote.model

import com.swein.framework.extension.string.urlDecode
import com.swein.framework.extension.string.urlEncode
import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity
import com.swein.shjetpackcompose.examples.schedulenote.database.model.IScheduleModel

class ScheduleModel: IScheduleModel {

    var uuid: String = ""
    var title: String = ""
    var content: String = ""
    var contentImage: String = ""
    var createDate: String = ""
    var isImportant: Boolean = false
    var isUrgent: Boolean = false
    var isFinished: Boolean = false
    var tag: String = ""

    override fun parsingEntity(scheduleEntity: ScheduleEntity) {

        this.uuid = scheduleEntity.uuid
        this.title = scheduleEntity.title.urlDecode()
        this.content = scheduleEntity.content.urlDecode()
        this.contentImage = scheduleEntity.contentImage
        this.createDate = scheduleEntity.createDate
        this.isImportant = scheduleEntity.isImportant
        this.isUrgent = scheduleEntity.isUrgent
        this.isFinished = scheduleEntity.isFinished
        this.tag = scheduleEntity.tag
    }

    override fun toEntity(): ScheduleEntity {
        return ScheduleEntity(uuid, title.urlEncode(), content.urlEncode(), contentImage, createDate, isImportant, isUrgent, isFinished, tag)
    }

//    fun initWithJSONObject(jsonObject: JSONObject) {
//        uuid = ParsingUtility.parsingString(jsonObject, "uuid")
//        title = ParsingUtility.parsingString(jsonObject, "title")
//        content = ParsingUtility.parsingString(jsonObject, "content")
//        contentImage = ParsingUtility.parsingString(jsonObject, "contentImage")
//        createDate = ParsingUtility.parsingString(jsonObject, "createDate")
//        isImportant = ParsingUtility.parsingBoolean(jsonObject, "isImportant")
//        isUrgent = ParsingUtility.parsingBoolean(jsonObject, "isUrgent")
//        isFinished = ParsingUtility.parsingBoolean(jsonObject, "isFinished")
//        tag = ParsingUtility.parsingString(jsonObject, "tag")
//    }
//
//    fun toJSONObject(): JSONObject {
//
//        return JSONObject().apply {
//            put("uuid", uuid)
//            put("title", title)
//            put("content", content)
//            put("contentImage", contentImage)
//            put("createDate", createDate)
//            put("isImportant", isImportant)
//            put("isUrgent", isUrgent)
//            put("isFinished", isFinished)
//            put("tag", tag)
//        }
//    }

    override fun toString(): String {
        return "uuid:$uuid title:$title content:$content contentImage:$contentImage createDate:$createDate isImportant:$isImportant isUrgent:$isUrgent isFinished:$isFinished tag:$tag"
    }
}
