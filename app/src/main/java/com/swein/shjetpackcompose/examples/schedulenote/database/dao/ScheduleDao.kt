package com.swein.shjetpackcompose.examples.schedulenote.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scheduleEntity: ScheduleEntity): Long

    @Query("DELETE FROM SCHEDULE_TABLE")
    suspend fun deleteAll(): Long

    @Query("SELECT * FROM SCHEDULE_TABLE WHERE UUID = :uuid")
    suspend fun load(uuid: String): ScheduleEntity?

}