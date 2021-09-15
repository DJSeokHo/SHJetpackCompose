package com.swein.shjetpackcompose.examples.schedulenote.edit.service

import com.swein.shjetpackcompose.examples.schedulenote.database.DatabaseManager
import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity
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

    suspend fun load(offset: Int, limit: Int) = withContext(Dispatchers.IO) {

        val resultList = mutableListOf<ScheduleModel>()

        DatabaseManager.load(offset, limit)?.let { list ->
            var scheduleModel: ScheduleModel
            for (scheduleEntity in list) {
                scheduleModel = ScheduleModel()
                scheduleModel.parsingEntity(scheduleEntity)
                resultList.add(scheduleModel)
            }
        }

        return@withContext resultList
    }

    suspend fun loadAll() = withContext(Dispatchers.IO) {

        val resultList = mutableListOf<ScheduleModel>()

        DatabaseManager.loadAll()?.let { list ->
            var scheduleModel: ScheduleModel
            for (scheduleEntity in list) {
                scheduleModel = ScheduleModel()
                scheduleModel.parsingEntity(scheduleEntity)
                resultList.add(scheduleModel)
            }
        }

        return@withContext resultList
    }

    suspend fun clean() = withContext(Dispatchers.IO) {
        return@withContext DatabaseManager.clean()
    }

}