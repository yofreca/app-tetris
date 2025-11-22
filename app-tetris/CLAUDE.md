# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Android Tetris game built with Jetpack Compose and Kotlin. Single-module application using Material Design 3.

- **Min SDK:** 24 (Android 7.0) | **Target SDK:** 36 (Android 15)
- **Package:** `com.example.app_tetris`

## Build Commands

```bash
./gradlew build                    # Full build
./gradlew assembleDebug            # Debug APK
./gradlew assembleRelease          # Release APK
./gradlew installDebug             # Install to device

# Testing
./gradlew test                     # Unit tests
./gradlew connectedAndroidTest     # Instrumented tests (requires device/emulator)

# Utilities
./gradlew clean                    # Clean build artifacts
./gradlew dependencies             # View dependency tree
```

## Architecture

- **Pattern:** Single-activity Compose application
- **Entry Point:** `MainActivity.kt` - uses edge-to-edge UI with `enableEdgeToEdge()`
- **UI Framework:** 100% Jetpack Compose (no XML layouts)
- **Theme System:** Material 3 with dynamic colors (Android 12+), dark/light support

### Code Organization

```
app/src/main/java/com/example/app_tetris/
├── MainActivity.kt              # App entry point
└── ui/theme/
    ├── Color.kt                 # Color palette (Purple, PurpleGrey, Pink variants)
    ├── Theme.kt                 # Theme composition with dynamic colors
    └── Type.kt                  # Typography definitions
```

## Key Technologies

- **Build:** Gradle 8.13 with Kotlin DSL, AGP 8.13.1
- **Kotlin:** 2.0.21 (JVM 11 target)
- **Compose:** BOM 2024.09.00 with Material3
- **Testing:** JUnit 4, Espresso, Compose UI testing

## Version Management

All dependency versions are centralized in `gradle/libs.versions.toml`.

## Current State

This is a boilerplate project with minimal Tetris game logic. The actual game mechanics (piece movement, rotation, collision detection, scoring) need to be implemented.
