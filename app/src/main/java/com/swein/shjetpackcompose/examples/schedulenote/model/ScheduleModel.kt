package com.swein.shjetpackcompose.examples.schedulenote.model

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
        this.title = scheduleEntity.title
        this.content = scheduleEntity.content
        this.contentImage = scheduleEntity.contentImage
        this.createDate = scheduleEntity.createDate
        this.isImportant = scheduleEntity.isImportant
        this.isUrgent = scheduleEntity.isUrgent
        this.isFinished = scheduleEntity.isFinished
    }

    override fun toEntity(): ScheduleEntity {
        return ScheduleEntity(uuid, title, content, contentImage, createDate, isImportant, isUrgent, isFinished)
    }

}
