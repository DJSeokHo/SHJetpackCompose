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

    override fun parsingEntity(scheduleEntity: ScheduleEntity) {

        this.uuid = scheduleEntity.uuid
        this.title = scheduleEntity.title.urlDecode()
        this.content = scheduleEntity.content.urlDecode()
        this.contentImage = scheduleEntity.contentImage
        this.createDate = scheduleEntity.createDate
        this.isImportant = scheduleEntity.isImportant
        this.isUrgent = scheduleEntity.isUrgent
        this.isFinished = scheduleEntity.isFinished
    }

    override fun toEntity(): ScheduleEntity {
        return ScheduleEntity(uuid, title.urlEncode(), content.urlEncode(), contentImage, createDate, isImportant, isUrgent, isFinished)
    }

    override fun toString(): String {
        return "uuid:$uuid title:$title content:$content contentImage:$contentImage createDate:$createDate isImportant:$isImportant isUrgent:$isUrgent isFinished:$isFinished"
    }
}
