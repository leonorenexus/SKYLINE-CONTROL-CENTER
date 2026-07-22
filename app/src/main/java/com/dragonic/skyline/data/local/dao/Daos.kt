package com.dragonic.skyline.data.local.dao

import androidx.room.*
import com.dragonic.skyline.data.local.entities.*
import kotlinx.coroutines.flow.Flow

// ── USER DAO ─────────────────────────────────────────────────

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = 1 LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?
}

// ── DEVICE DAO ───────────────────────────────────────────────

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices ORDER BY lastSeen DESC")
    fun getAllDevices(): Flow<List<DeviceEntity>>

    @Upsert
    suspend fun upsertDevice(device: DeviceEntity)

    @Delete
    suspend fun deleteDevice(device: DeviceEntity)

    @Query("SELECT COUNT(*) FROM devices WHERE isOnline = 1")
    fun getOnlineCount(): Flow<Int>
}

// ── IOT DEVICE DAO ───────────────────────────────────────────

@Dao
interface IoTDeviceDao {
    @Query("SELECT * FROM iot_devices ORDER BY id ASC")
    fun getAllIoTDevices(): Flow<List<IoTDeviceEntity>>

    @Upsert
    suspend fun upsertIoTDevice(device: IoTDeviceEntity)

    @Query("UPDATE iot_devices SET isOn = :isOn WHERE id = :id")
    suspend fun setDeviceState(id: Int, isOn: Boolean)

    @Delete
    suspend fun deleteIoTDevice(device: IoTDeviceEntity)
}

// ── ACTIVITY LOG DAO ─────────────────────────────────────────

@Dao
interface ActivityLogDao {
    @Query("SELECT * FROM activity_log ORDER BY timestamp DESC LIMIT 100")
    fun getRecentActivity(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(log: ActivityLogEntity)

    @Query("DELETE FROM activity_log WHERE timestamp < :cutoff")
    suspend fun pruneOldLogs(cutoff: Long)
}

// ── SETTINGS DAO ─────────────────────────────────────────────

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = 1 LIMIT 1")
    fun getSettings(): Flow<AppSettingsEntity?>

    @Upsert
    suspend fun upsertSettings(settings: AppSettingsEntity)
}
