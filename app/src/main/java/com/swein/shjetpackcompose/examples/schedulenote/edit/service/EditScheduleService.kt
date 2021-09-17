package com.swein.shjetpackcompose.examples.schedulenote.edit.service

import com.swein.shjetpackcompose.examples.schedulenote.database.DatabaseManager
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object EditScheduleService {

    suspend fun insert(scheduleModel: ScheduleModel): Long = withContext(Dispatchers.IO) {
        return@withContext DatabaseManager.insert(scheduleModel.toEntity())
    }

    suspend fun update(scheduleModel: ScheduleModel): Int = withContext(Dispatchers.IO) {
        return@withContext DatabaseManager.update(scheduleModel.toEntity())
    }

    suspend fun delete(uuid: String): Int = withContext(Dispatchers.IO) {
        return@withContext DatabaseManager.delete(uuid)
    }

    suspend fun load(uuid: String): ScheduleModel? = withContext(Dispatchers.IO) {

        var scheduleModel: ScheduleModel? = null
        DatabaseManager.load(uuid)?.let {
            scheduleModel = ScheduleModel()
            scheduleModel!!.parsingEntity(it)
        }

        return@withContext scheduleModel
    }

    suspend fun load(offset: Int, limit: Int): MutableList<ScheduleModel> = withContext(Dispatchers.IO) {

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

    suspend fun loadAll(): MutableList<ScheduleModel> = withContext(Dispatchers.IO) {

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