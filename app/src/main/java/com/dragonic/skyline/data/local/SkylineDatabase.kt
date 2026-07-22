package com.dragonic.skyline.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dragonic.skyline.data.local.dao.*
import com.dragonic.skyline.data.local.entities.*

@Database(
    entities = [
        UserEntity::class,
        DeviceEntity::class,
        IoTDeviceEntity::class,
        ActivityLogEntity::class,
        AppSettingsEntity::class
    ],
    version  = 1,
    exportSchema = true
)
abstract class SkylineDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deviceDao(): DeviceDao
    abstract fun ioTDeviceDao(): IoTDeviceDao
    abstract fun activityLogDao(): ActivityLogDao
    abstract fun appSettingsDao(): AppSettingsDao

    companion object {
        const val DATABASE_NAME = "skyline_control_center.db"
    }
}
