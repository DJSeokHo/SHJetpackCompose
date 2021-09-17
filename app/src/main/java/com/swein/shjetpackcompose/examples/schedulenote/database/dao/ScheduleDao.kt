package com.swein.shjetpackcompose.examples.schedulenote.database.dao

import androidx.room.*
import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scheduleEntity: ScheduleEntity): Long

    @Update
    suspend fun update(scheduleEntity: ScheduleEntity): Int

    @Query("DELETE FROM SCHEDULE_TABLE WHERE UUID = :uuid")
    suspend fun delete(uuid: String): Int

    @Query("DELETE FROM SCHEDULE_TABLE")
    suspend fun clean(): Int // DELETE query methods must either return void or int (the number of deleted rows)

    @Query("SELECT * FROM SCHEDULE_TABLE WHERE UUID = :uuid")
    suspend fun load(uuid: String): ScheduleEntity?

    @Query("SELECT * FROM SCHEDULE_TABLE ORDER BY CREATE_DATE DESC")
    suspend fun loadAll(): List<ScheduleEntity>?

    @Query("SELECT * FROM SCHEDULE_TABLE ORDER BY CREATE_DATE DESC LIMIT :limit OFFSET :offset")
    suspend fun load(offset: Int, limit: Int): List<ScheduleEntity>?

}