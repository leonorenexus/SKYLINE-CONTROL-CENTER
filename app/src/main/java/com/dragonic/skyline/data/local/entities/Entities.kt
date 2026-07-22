package com.dragonic.skyline.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// ── USER ENTITY ──────────────────────────────────────────────

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val username: String,
    val passwordHash: String,
    val rememberMe: Boolean = false,
    val membershipType: String = "PRO",
    val joinDate: Long = System.currentTimeMillis(),
    val deviceCount: Int = 0
)

// ── DEVICE ENTITY ────────────────────────────────────────────

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val ipAddress: String,
    val macAddress: String = "",
    val isOnline: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis()
)

// ── IOT DEVICE ENTITY ────────────────────────────────────────

@Entity(tableName = "iot_devices")
data class IoTDeviceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val isOn: Boolean = false,
    val lastValue: String = "",
    val scheduleJson: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

// ── ACTIVITY LOG ENTITY ──────────────────────────────────────

@Entity(tableName = "activity_log")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val event: String,
    val category: String = "SYSTEM",
    val timestamp: Long = System.currentTimeMillis()
)

// ── APP SETTINGS ENTITY ──────────────────────────────────────

@Entity(tableName = "app_settings")
data class AppSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val darkMode: Boolean = true,
    val animationEnabled: Boolean = true,
    val pushNotification: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val biometricEnabled: Boolean = false,
    val pinEnabled: Boolean = false,
    val bgUriString: String? = null,
    val bannerUriString: String? = null
)
