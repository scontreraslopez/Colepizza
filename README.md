# Colepizza 🍕

Es una aplicación inspirada en la del comedor de los niños de Colevisa.
Una excusa para practicar navegación, estado y MVVM.

## ¿De qué va?

Algunos colegios usan la app de **Colevisa**, la empresa que gestiona su comedor escolar, para
contar a las familias **qué tal ha comido cada niño**. Para cada día se ve el menú y, en cada
plato, una valoración sencilla: si se lo ha comido bien, regular o mal.

Colepizza reproduce esa idea a pequeña escala: una lista de días de la semana y, para cada día,
los platos del menú valorados con caritas.

> Proyecto educativo y personal, sin relación con Colevisa. Solo toma la idea como punto de
> partida para aprender desarrollo Android moderno.

## Qué hace ahora mismo

- **Días de la semana** (`MainScreen`): lista de lunes a viernes. Todavía no navega a ningún sitio.
- **Menú del día** (`ColepizzaScreen`): cinco platos (agua, entrante, primer plato, segundo
  plato y postre). Cada uno se valora con un selector de tres caritas.
  - `SmileyRating` se comporta como un grupo de *radio buttons* accesible
    (`selectableGroup` + `Role.RadioButton`).
  - La valoración es un `enum class Rating` (`HAPPY`, `NEUTRAL`, `SAD`) que guarda su propio
    icono y su texto para TalkBack.
  - El estado está elevado a la pantalla (*state hoisting*): los componentes no guardan estado propio.
- **Iconos pixel art** estilo RPG para cada plato, dibujados como VectorDrawables de 16×16 en
  `res/drawable/menu_*.xml`. Cada entrada del `enum class MenuItem` guarda su nombre y su icono.

## Hoja de ruta

Partido a partido 🙂. Iremos añadiendo piezas según las vayamos aprendiendo:

- [x] Componentes Compose reutilizables y estado elevado
- [x] Modelar las opciones con `enum` en lugar de `Int`
- [x] Asociar cada plato a su icono (`enum class MenuItem`)
- [ ] **Navegación** con Navigation Compose: lista de días → menú del día
- [ ] **MVVM**: sacar el estado a un `ViewModel` con `StateFlow`, que también sobrevive a la rotación
- [ ] **Room**, quizá: guardar las valoraciones de forma persistente
- [ ] Lo que vaya surgiendo…

## Stack

- Kotlin 2.4
- Jetpack Compose (BOM 2026.09) + Material 3
- minSdk 26 · targetSdk 36

## Estructura

```
app/src/main/java/net/iessochoa/sergiocontreras/colepizza/
├── MainActivity.kt
├── ColePizzaApp.kt          # Raíz de la UI: tema + Scaffold
└── ui/
    ├── components/          # MenuRow, SmileyRating, SmileySelectable, Rating, MenuItem
    ├── screens/             # MainScreen, ColepizzaScreen
    └── theme/               # Colores, tipografía y tema
```

## Cómo ejecutarla

1. Abre el proyecto en Android Studio.
2. Espera a que termine la sincronización de Gradle.
3. Ejecuta la configuración `app` en un emulador o en un dispositivo con Android 8.0 (API 26) o superior.
