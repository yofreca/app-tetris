# Tetris Android - Neon Edition

Un juego de Tetris moderno desarrollado para Android con **Kotlin** y **Jetpack Compose**, presentando un espectacular tema visual **Neon Moderno** con efectos de glow, animaciones fluidas y controles táctiles intuitivos.

## Capturas de Pantalla

```
┌─────────────────────────────────┐
│         ╔═══════════╗           │
│         ║  TETRIS   ║           │
│         ╚═══════════╝           │
│  ┌──────────────┐  ┌────────┐   │
│  │  ■ ■         │  │  NEXT  │   │
│  │  ■ ■         │  │  ████  │   │
│  │              │  └────────┘   │
│  │      ████    │  ┌────────┐   │
│  │    ██████    │  │ SCORE  │   │
│  │  ██████████  │  │  1200  │   │
│  │  ████  ████  │  │ LEVEL  │   │
│  │  ████████    │  │   2    │   │
│  └──────────────┘  └────────┘   │
│    [◀] [▼] [▶] [↻] [⏬]        │
└─────────────────────────────────┘
```

## Características

### Jugabilidad
- **7 Tetrominoes clásicos**: I, O, T, S, Z, J, L
- **Sistema de rotación SRS** con wall kicks
- **Ghost piece**: Vista previa de dónde caerá la pieza
- **Hard drop**: Caída instantánea con tecla dedicada
- **Sistema de niveles**: Velocidad progresiva cada 10 líneas
- **Puntuación clásica**: 100/300/500/800 puntos por 1/2/3/4 líneas

### Tema Visual Neon
- **Colores vibrantes**: Cyan, Magenta, Verde, Rojo, Azul, Naranja
- **Efectos de glow**: Bordes luminosos en todos los elementos
- **Gradientes 3D**: Celdas con profundidad y brillos
- **Fondo degradado**: Atmósfera oscura con destellos

### Animaciones
- **Flash de líneas**: Destello blanco + glow cyan al limpiar filas
- **Impacto de hard drop**: Ondas expansivas y estelas verticales
- **Transiciones suaves**: Animaciones de 200-300ms con easing

### Controles
- **Botones táctiles**: Diseño circular con efecto de presión
- **Pausa/Reanudar**: Control del flujo del juego
- **Reiniciar**: Nueva partida en cualquier momento

## Tecnologías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Kotlin | 2.0.21 | Lenguaje principal |
| Jetpack Compose | BOM 2024.09.00 | UI declarativa |
| Material Design 3 | Latest | Componentes y temas |
| ViewModel | 2.9.4 | Gestión de estado |
| Coroutines | Built-in | Game loop y animaciones |
| Canvas API | Compose | Renderizado del tablero |

## Arquitectura

```
app/src/main/java/com/example/app_tetris/
│
├── MainActivity.kt                 # Punto de entrada
│
├── model/
│   ├── Tetromino.kt               # Enum de 7 piezas con rotaciones
│   └── GameState.kt               # Estado inmutable del juego
│
├── game/
│   └── TetrisGameLogic.kt         # Lógica pura (colisiones, rotación, scoring)
│
├── viewmodel/
│   └── TetrisViewModel.kt         # StateFlow + Coroutines game loop
│
└── ui/
    ├── theme/
    │   ├── Color.kt               # Paleta de colores neon
    │   ├── Theme.kt               # Configuración Material 3
    │   └── Type.kt                # Tipografía
    │
    └── game/
        ├── TetrisScreen.kt        # Pantalla principal
        ├── GameBoard.kt           # Canvas del tablero 10x20
        ├── NextPiecePreview.kt    # Vista previa siguiente pieza
        ├── ScorePanel.kt          # Puntuación, nivel, líneas
        └── GameControls.kt        # Botones táctiles
```

## Estructura del Estado

```kotlin
data class GameState(
    val board: List<List<Color?>>,      // Tablero 10x20
    val currentPiece: Tetromino,        // Pieza actual
    val nextPiece: Tetromino,           // Siguiente pieza
    val pieceX: Int, pieceY: Int,       // Posición
    val pieceRotation: Int,             // Rotación (0-3)
    val score: Int,                     // Puntuación
    val level: Int,                     // Nivel actual
    val linesCleared: Int,              // Total de líneas
    val isGameOver: Boolean,            // Fin del juego
    val isPaused: Boolean,              // Estado de pausa
    val lineClearAnimation: LineClearAnimation?,   // Animación activa
    val hardDropAnimation: HardDropAnimation?      // Animación activa
)
```

## Sistema de Puntuación

| Líneas | Puntos Base | Con Nivel 5 |
|--------|-------------|-------------|
| 1 (Single) | 100 | 500 |
| 2 (Double) | 300 | 1500 |
| 3 (Triple) | 500 | 2500 |
| 4 (Tetris) | 800 | 4000 |

**Fórmula**: `puntos = base × nivel`

## Velocidad por Nivel

| Nivel | Velocidad (ms) | Descripción |
|-------|----------------|-------------|
| 1 | 1000 | Inicial |
| 2 | 900 | +10 líneas |
| 3 | 800 | +20 líneas |
| ... | ... | ... |
| 10+ | 100 | Máxima |

**Fórmula**: `velocidad = max(100, 1000 - (nivel - 1) × 100)`

## Requisitos del Sistema

- **Android**: SDK 24+ (Android 7.0 Nougat)
- **Target**: SDK 36 (Android 15)
- **RAM**: 2GB mínimo recomendado
- **Pantalla**: Cualquier resolución (diseño responsive)

## Compilación

```bash
# Clonar repositorio
git clone <repository-url>
cd app-tetris

# Compilar APK de debug
./gradlew assembleDebug

# Compilar APK de release
./gradlew assembleRelease

# Instalar en dispositivo conectado
./gradlew installDebug

# Ejecutar tests
./gradlew test
```

## Estructura de Directorios

```
app-tetris/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/app_tetris/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                    # Unit tests
│   │   └── androidTest/             # Instrumented tests
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   └── libs.versions.toml           # Catálogo de versiones
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Controles del Juego

| Botón | Acción | Color |
|-------|--------|-------|
| ◀ | Mover izquierda | Azul |
| ▼ | Mover abajo | Azul |
| ▶ | Mover derecha | Azul |
| ↻ | Rotar pieza | Púrpura |
| ⏬ | Hard drop | Naranja |
| ▶/⏸ | Play/Pausa | Verde |
| ↺ | Reiniciar | Rojo |

## Paleta de Colores Neon

```
Fondo:          #0A0A1A (Negro profundo)
Tablero:        #0D0D2B (Azul muy oscuro)
Borde glow:     #00FFFF (Cyan brillante)

Tetrominoes:
  I - #00FFFF (Cyan)
  O - #FFFF00 (Amarillo)
  T - #FF00FF (Magenta)
  S - #00FF66 (Verde)
  Z - #FF0055 (Rosa/Rojo)
  J - #0066FF (Azul)
  L - #FF6600 (Naranja)
```

## Contribuir

1. Fork del repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'feat: agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## Créditos

- **Tetris** es una marca registrada de The Tetris Company
- Diseño original del juego por Alexey Pajitnov (1984)
- Implementación Android por el equipo de desarrollo

---

**Desarrollado con Kotlin y Jetpack Compose**
