package com.swein.shjetpackcompose.examples.schedulenote.database.model

import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity

interface IScheduleModel {

    fun parsingEntity(scheduleEntity: ScheduleEntity)

    fun toEntity(): ScheduleEntity
}