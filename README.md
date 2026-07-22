# SKYLINE CONTROL CENTER
### DRAGONIC // Leonore Tech Team

> Dashboard kontrol futuristik dengan tema Sakura Pink Cyber dan reaktor neon yang berputar.

---

## Screenshot Preview

```
┌─────────────────────────────────┐
│   SKYLINE CONTROL CENTER        │
│   ▓▓▓ Nebula Background ▓▓▓     │
│   ✦ ✦ Sakura Particles ✦ ✦      │
│   ┼─┼─┼ Cyber Grid ┼─┼─┼        │
│                                 │
│      ╔═══════════════╗           │
│      ║  ◌ Ring 6    ║           │
│      ║ ◌ Ring 1  ◌  ║           │
│      ║  ◌ Ring 2 ◌  ║           │
│      ║   ◉─── L ───◉  ║         │
│      ║  ◌ Ring 5 ◌  ║           │
│      ╚═══════════════╝           │
│                                 │
│  [UserCard]    [ClockWidget]    │
│  [──── Hero Banner ────────]    │
│  [Dev] [Net] [WiFi] [Spd]...   │
│                                 │
│ 🏠 📱 📶 🔌 📊 👤 ⚙️            │
└─────────────────────────────────┘
```

---

## Tech Stack

| Layer | Library |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt 2.53 |
| Database | Room 2.6 |
| Preferences | DataStore |
| Video | Media3 ExoPlayer |
| Images | Coil 2.7 |
| Animation | Lottie 6.6 |
| Network | Retrofit + OkHttp |
| Security | Biometric API |

---

## Fitur Utama

- 🔴 **Reactor Logo** – Logo "L" di pusat dengan 6 ring neon independen (rotasi CW/CCW, scanner, hologram, pulse, energy)
- 🌸 **Sakura Particles** – Partikel sakura mengambang di seluruh layar
- 🌌 **Nebula Background** – Latar belakang nebula animasi dengan warna pink/cyan/ungu
- 🔲 **Cyber Grid** – Grid bergerak futuristik
- 🪟 **Glassmorphism** – Semua card menggunakan glass effect + neon border
- 🎬 **Video Background** – Support MP4 via ExoPlayer di Login + Home
- 📡 **WiFi Monitor** – SSID, IP, DNS, sinyal, speed realtime
- 🏠 **IoT Dashboard** – Kontrol smart lamp, plug, relay, sensor
- 📊 **Analytics** – Chart, timeline, statistik harian/mingguan
- 🔒 **Security** – Fingerprint biometric + PIN lock
- ⏱ **Realtime Clock** – Jam WIB update setiap detik

---

## Build Setup

### Prerequisites
- JDK 17
- Android Studio Ladybug atau lebih baru
- Android SDK 35

### Font yang diperlukan
Download dan letakkan di `app/src/main/res/font/`:
- **Orbitron** (Google Fonts): `orbitron_regular.ttf`, `orbitron_medium.ttf`, `orbitron_semibold.ttf`, `orbitron_bold.ttf`, `orbitron_extrabold.ttf`, `orbitron_black.ttf`
- **Rajdhani** (Google Fonts): `rajdhani_regular.ttf`, `rajdhani_medium.ttf`, `rajdhani_semibold.ttf`, `rajdhani_bold.ttf`

### Build via Android Studio
```bash
./gradlew assembleDebug
```

### Build via GitHub Actions
Push ke branch `main` → Actions otomatis build Debug + Release APK.

---

## Struktur Proyek

```
app/src/main/java/com/dragonic/skyline/
├── core/
│   ├── di/          # Hilt modules
│   ├── navigation/  # NavGraph + Screen routes
│   ├── service/     # Background foreground service
│   └── theme/       # Colors, Typography, Theme
├── data/
│   └── local/
│       ├── dao/     # Room DAOs
│       └── entities/# Room entities
├── presentation/
│   ├── components/  # GlassCard, ReactorLogo, ClockWidget, BottomNav...
│   ├── splash/      # Splash screen
│   ├── login/       # Login screen + ViewModel
│   ├── home/        # Home dashboard + ViewModel
│   ├── devices/     # Devices list
│   ├── wifi/        # WiFi monitor + ViewModel
│   ├── iot/         # IoT dashboard + ViewModel
│   ├── analytics/   # Analytics + charts
│   ├── profile/     # Profile page
│   └── settings/    # Settings + ViewModel
├── MainActivity.kt
└── SkylineApp.kt
```

---

## Warna Tema

| Name | Hex |
|---|---|
| Sakura Pink | `#FF4DB8` |
| Sakura Pink Light | `#FF66CC` |
| Sakura Pink Pale | `#FF99DD` |
| Deep Void | `#0A0810` |
| Deep Purple | `#0F0A17` |
| Dark Plum | `#1A1026` |
| Mid Plum | `#2B143D` |
| Cyber Cyan | `#00F5FF` |
| Neon Violet | `#BF5FFF` |

---

*DRAGONIC © 2025 — Leonore Tech Team — leonore.web.id*
