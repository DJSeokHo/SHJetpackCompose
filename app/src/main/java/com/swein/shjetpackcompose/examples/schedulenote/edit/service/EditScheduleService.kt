package com.swein.shjetpackcompose.examples.schedulenote.edit.service

import com.swein.shjetpackcompose.examples.schedulenote.database.DatabaseManager
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object EditScheduleService {

    suspend fun insert(scheduleModel: ScheduleModel) = withContext(Dispatchers.IO) {
        return@withContext DatabaseManager.insert(scheduleModel.toEntity())
    }

    suspend fun load(uuid: String) = withContext(Dispatchers.IO) {

        DatabaseManager.load(uuid)?.let {
            val scheduleModel = ScheduleModel()
            scheduleModel.parsingEntity(it)
        }

        return@withContext
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        return@withContext DatabaseManager.deleteAll()
    }

}