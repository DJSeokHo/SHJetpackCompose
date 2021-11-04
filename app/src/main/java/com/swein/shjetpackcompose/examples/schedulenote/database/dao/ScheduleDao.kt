package com.swein.shjetpackcompose.examples.schedulenote.database.dao

import androidx.room.*
import com.swein.shjetpackcompose.examples.schedulenote.database.entity.ScheduleEntity

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scheduleEntity: ScheduleEntity): Long

    @Update
    fun update(scheduleEntity: ScheduleEntity): Int

    @Query("DELETE FROM SCHEDULE_TABLE WHERE UUID = :uuid")
    fun delete(uuid: String): Int

    @Query("DELETE FROM SCHEDULE_TABLE")
    fun clean(): Int // DELETE query methods must either return void or int (the number of deleted rows)

    @Query("SELECT * FROM SCHEDULE_TABLE WHERE UUID = :uuid")
    fun load(uuid: String): ScheduleEntity?

    @Query("SELECT * FROM SCHEDULE_TABLE ORDER BY CREATE_DATE DESC")
    fun loadAll(): List<ScheduleEntity>?

    @Query("SELECT * FROM SCHEDULE_TABLE ORDER BY CREATE_DATE DESC LIMIT :limit OFFSET :offset")
    fun load(offset: Int, limit: Int): List<ScheduleEntity>?

}