package com.swein.shjetpackcompose.examples.schedulenote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.swein.shjetpackcompose.examples.schedulenote.database.dao.ScheduleDao
import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity

@Database(entities = [ScheduleEntity::class], version = 1)
abstract class ScheduleBase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}

object DatabaseManager {

    lateinit var database: ScheduleBase
    fun init(context: Context, name: String) {
        database = Room.databaseBuilder(context, ScheduleBase::class.java, name).build()
    }

    suspend fun insert(scheduleEntity: ScheduleEntity): Long {
        return database.scheduleDao().insert(scheduleEntity)
    }

    suspend fun update(scheduleEntity: ScheduleEntity): Int {
        return database.scheduleDao().update(scheduleEntity)
    }

    suspend fun delete(uuid: String): Int {
        return database.scheduleDao().delete(uuid)
    }

    suspend fun clean(): Int {
        return database.scheduleDao().clean()
    }

    suspend fun load(uuid: String): ScheduleEntity? {
        return database.scheduleDao().load(uuid)
    }

    suspend fun load(offset: Int, limit: Int): List<ScheduleEntity>? {
        return database.scheduleDao().load(offset, limit)
    }

    suspend fun loadAll(): List<ScheduleEntity>? {
        return database.scheduleDao().loadAll()
    }

}