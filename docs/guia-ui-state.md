# Guía: pensar el UI state de Colepizza

Objetivo final:

- Cada día empieza **sin nada seleccionado**.
- En cada plato se puede elegir una carita y **también quitarla** (volver a pulsar la marcada).
- Cada carita vale puntos: 😀 `+1`, 😐 `0`, 😞 `-1`.
- Cuando un día está **completo** (los 5 platos valorados), su botón en la lista de días se pinta:
  verde si la suma es positiva, amarillo si es 0 y rojo si es negativa.
- Si el día no está completo, el botón se queda con el color normal.

Vamos partido a partido: un paso, un commit (`Paso N: ...`), como en Galaxy Tracker.

---

## Paso 0 · Pensar antes de teclear (en papel)

Antes de escribir nada, hay que responder a estas preguntas. Son las que luego evitan morir.

### 0.1 ¿Qué es fuente de verdad y qué se calcula?

| Dato | ¿Se guarda o se calcula? |
|---|---|
| Qué carita tiene cada plato de cada día | **Se guarda.** Es lo único que el usuario cambia. |
| Puntuación del día | Se calcula sumando las caritas. |
| Si el día está completo | Se calcula: ¿tienen valoración los 5 platos? |
| Resultado del día (bien / regular / mal / pendiente) | Se calcula a partir de lo anterior. |
| Color del botón | Se calcula en la UI a partir del resultado. |

Regla: **guarda lo mínimo y calcula el resto**. Si guardas la puntuación además de las caritas,
tarde o temprano se desincronizan (igual que el progreso en Galaxy Tracker, que nunca se guardaba).

### 0.2 ¿Cómo represento "no hay nada seleccionado"?

Ahora mismo el estado empieza en `Rating.NEUTRAL`: no hay forma de decir "sin valorar".

- **`Rating.NONE` en el enum:** mala idea. Saldría como cuarta carita en `Rating.entries` y no
  tiene puntuación.
- **`Rating?` con `null` = sin valorar:** ✅ es lo que usaremos. Kotlin te obliga a tratar el caso
  "no hay nada" en todos los sitios.

### 0.3 ¿Cómo organizo las valoraciones de la semana? (aquí está el debate)

Hay que guardar *5 días × 5 platos*. Hay varias formas razonables, y **ninguna es gratis**.

#### A. Listas por índice

```kotlin
val ratings: List<List<Rating?>>      // ratings[díaIndex][platoIndex]
```

| 👍 | 👎 |
|---|---|
| Lo más sencillo de entender y de escribir. | El significado está en el **orden**: `ratings[2][3]` es "miércoles, segundo plato" solo porque coincide con el orden de los enums. Si reordenas o añades un plato en medio, las valoraciones cambian de plato **sin ningún error**. |
| Es lo que ya tenemos (`selectionList[index]`). | Hay que construirlo con el tamaño justo; un índice mal puesto es un crash o, peor, un dato en el sitio equivocado. |
| | Actualizar es un `mapIndexed` dentro de otro `mapIndexed`: poco legible. |

Buena para un prototipo. Frágil en cuanto el proyecto crece.

#### B. Mapas indexados por enum

```kotlin
val ratings: Map<SchoolDay, Map<MenuItem, Rating>>   // si no está la clave → sin valorar
```

| 👍 | 👎 |
|---|---|
| La clave es el propio día/plato: **el orden da igual** y no pueden repetirse. | La lógica (puntuación, resultado) queda en **funciones sueltas**: `dayResultOf(ratings[day].orEmpty())`. |
| "Sin valorar" = la clave no está. Vaciar = `mapa - item`. Completo = `size == 5`. | Actualizar dos niveles es denso: `state.ratings + (day to (dayRatings + (item to rating)))`. |
| Se parece a una tabla de BD `(día, plato, carita)`: buena base para Room. | La UI tiene que recorrer `MenuItem.entries` y buscar en el mapa, no el mapa (si no, los platos sin valorar no salen). |

Robusta, pero cuesta más leerla.

#### C. Clases del modelo (la que seguimos en esta guía)

```kotlin
data class Dish(val item: MenuItem, val rating: Rating? = null)
data class DailyMenu(val day: SchoolDay, val dishes: List<Dish>)
// UiState: val days: List<DailyMenu>
```

| 👍 | 👎 |
|---|---|
| Se lee como el dominio: un día tiene platos, un plato tiene carita. | El tipo **no impide** estados raros: nada evita dos `WATER` o que falte un plato. Lo garantizamos construyéndolo siempre desde `MenuItem.entries`, no el compilador. |
| La lógica vive en la clase: `day.score`, `day.result`, `day.rate(...)`. Se prueba fácil. | Buscar un día es `days.first { it.day == day }` en vez de `ratings[day]`. |
| Mismo patrón que Galaxy Tracker: `map { if (...) it.copy(...) else it }`. | Hay más clases que escribir. |
| La UI recorre `day.dishes` tal cual. | |

#### ¿Y cuál es "la buena"?

Depende de para qué. Elegimos **C** porque el código queda más legible, la lógica se agrupa en el
modelo y repite lo de Galaxy Tracker. Pero **B es igual de válida** (y más estricta).
Existe también un híbrido: un mapa *escondido* dentro de una clase con métodos `rate()`, `score`...
Es lo mejor de los dos, pero para este proyecto es complicarse.

Lo que **no** depende del enfoque es lo siguiente.

### 0.4 El truco de las colecciones: inmutables siempre

Tanto `StateFlow` como `mutableStateOf` solo avisan cuando el valor nuevo es **distinto**
(`equals`) del anterior. Si cambias una colección mutable por dentro:

- El estado "viejo" y el "nuevo" apuntan **a la misma lista**, así que son iguales → no se avisa.
- Ojo: aunque hagas `copy()`, si la lista de dentro es la misma lista modificada, el estado nuevo
  sigue siendo igual al viejo.
- Compose no se entera → **la pantalla no se repinta**, y no sale ningún error.

Por eso: en el estado, siempre `List` / `Map` de **solo lectura**, y para cambiar algo se crea uno
nuevo (`map`, `+`, `-`, `copy()`). Da igual que uses A, B o C: esta regla no se negocia.

### 0.5 ¿Dónde va cada cosa?

```
colepizza/
├── model/          Rating, MenuItem, SchoolDay, Dish, DailyMenu, DayResult
│                   → existirían aunque no hubiera pantallas (Room, API...)
├── ui/             ColepizzaUiState, ColepizzaViewModel
│                   → existen porque hay una pantalla
├── ui/theme/       DayResult → colores (eso ya es UI)
└── navigation/     Claves de navegación
```

### 0.6 Esquema final

```
 ViewModel (fuente de verdad)            se calcula en el modelo         UI
 ┌──────────────────────────────┐
 │ days: List<DailyMenu>        │ ── day.dishes ──────────────────▶ caritas marcadas
 │   └ dishes: List<Dish>       │
 │       └ rating: Rating?      │ ── day.result: DayResult ──────▶ color del botón
 └──────────────────────────────┘
            ▲
            │ rate(day, item, rating?)   clearDay(day)
            └──────────── eventos de la UI
```

---

## Paso 1 · Modelo

Todo en `model/`.

**1.1** Añade la puntuación a `Rating`:

```kotlin
enum class Rating(
    @DrawableRes val smileyRes: Int,
    @StringRes val descriptionRes: Int,
    val score: Int
) {
    HAPPY(R.drawable.happy_face, R.string.smiley_happy, 1),
    NEUTRAL(R.drawable.neutral_face, R.string.smiley_neutral, 0),
    SAD(R.drawable.sad_face, R.string.smiley_sad, -1)
}
```

**1.2** Crea `enum class SchoolDay` (lunes a viernes) con un `@StringRes val labelRes`, igual que
`MenuItem`. Mueve "Lunes", "Martes"... a `strings.xml`. Así desaparece la lista de `String` fija de
`MainScreen`.

> Alternativa: `java.time.DayOfWeek` (minSdk 26 lo permite) con `entries.take(5)`. Pero con un enum
> propio sigues el mismo patrón que `Rating` y `MenuItem` y los textos salen de recursos.

**1.3** `enum class DayResult { PENDING, GOOD, NEUTRAL, BAD }`. Fíjate en que **no sabe nada de
colores**: el color es cosa de la UI (paso 4).

**1.4** `Dish` y `DailyMenu`:

```kotlin
data class Dish(
    val item: MenuItem,
    val rating: Rating? = null
)

data class DailyMenu(
    val day: SchoolDay,
    val dishes: List<Dish> = MenuItem.entries.map { Dish(it) }
) {
    val isComplete get() = dishes.all { it.rating != null }
    val score get() = dishes.sumOf { it.rating?.score ?: 0 }
    val result get() = when {
        !isComplete -> DayResult.PENDING
        score > 0 -> DayResult.GOOD
        score < 0 -> DayResult.BAD
        else -> DayResult.NEUTRAL
    }

    fun rate(item: MenuItem, rating: Rating?) =
        copy(dishes = dishes.map { if (it.item == item) it.copy(rating = rating) else it })

    fun clear() = copy(dishes = dishes.map { it.copy(rating = null) })
}
```

Cosas en las que fijarse:

- El valor por defecto de `dishes` crea **todos** los platos sin valorar. Así nunca falta ninguno
  (es la garantía que el tipo no da, ver 0.3 C).
- `isComplete`, `score` y `result` están en el **cuerpo** de la clase, no en el constructor:
  no se guardan, se calculan.
- `rate()` y `clear()` **no modifican** nada: devuelven un `DailyMenu` nuevo. Toda la
  inmutabilidad queda escondida aquí; quien la use no tiene que pensar en ella.

**1.5** Tests unitarios en `app/src/test/.../DailyMenuTest.kt`. Es lógica pura, sin emulador.
Un truco para montar los datos:

```kotlin
private fun menuWith(vararg ratings: Rating): DailyMenu =
    MenuItem.entries.zip(ratings).fold(DailyMenu(SchoolDay.MONDAY)) { menu, (item, rating) ->
        menu.rate(item, rating)
    }
// menuWith(HAPPY, HAPPY)                     → incompleto (2 de 5)
// menuWith(HAPPY, HAPPY, SAD, NEUTRAL, SAD)  → completo, suma 0
```

Casos mínimos:

- Recién creado → `PENDING`.
- Incompleto aunque sea todo 😀 → `PENDING`.
- Completo positivo → `GOOD`, cero → `NEUTRAL`, negativo → `BAD`.
- `rate(item, null)` sobre un día completo → vuelve a `PENDING`.
- `clear()` → todos los `rating` son `null`.
- `rate()` no cambia el original (compara el menú antes y después).

✅ **Comprueba:** los tests pasan en verde.

---

## Paso 2 · Componentes que aceptan "nada"

**2.1** En `SmileyRating` y `MenuRow`, cambia `selection: Rating` por `selection: Rating?`
y `onSelection: (Rating) -> Unit` por `onSelection: (Rating?) -> Unit`.

**2.2** Volver a pulsar la carita marcada la desmarca. Esto se decide en `SmileyRating`:

```kotlin
onClick = { onSelection(if (selection == rating) null else rating) }
```

`SmileySelectable` no cambia: sigue recibiendo `selected: Boolean`.

**2.3** Añade una `@Preview` con `selection = null` para ver la fila sin nada marcado.

✅ **Comprueba:** en las previews se ve una fila con carita marcada y otra sin ninguna.

---

## Paso 3 · `ColepizzaScreen` stateless (con estado temporal arriba)

**3.1** Saca el estado de la pantalla. Que reciba el día y avise hacia arriba:

```kotlin
@Composable
fun ColepizzaScreen(
    menu: DailyMenu,
    onRate: (MenuItem, Rating?) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
)
```

Dentro:

```kotlin
menu.dishes.forEach { dish ->
    MenuRow(
        item = dish.item,
        selection = dish.rating,               // null → nada marcado
        onSelection = { onRate(dish.item, it) }
    )
}
```

Añade un botón "Limpiar" que llame a `onClear`.

**3.2** Mientras no hay ViewModel, pon el estado temporalmente en `ColepizzaApp`:

```kotlin
var menu by remember { mutableStateOf(DailyMenu(SchoolDay.MONDAY)) }

ColepizzaScreen(
    menu = menu,
    onRate = { item, rating -> menu = menu.rate(item, rating) },
    onClear = { menu = menu.clear() },
    modifier = Modifier.padding(innerPadding)
)
```

**3.3 🧪 Experimento: rómpelo a propósito.** Cambia temporalmente `DailyMenu` para que use una
lista mutable y la modifique por dentro:

```kotlin
data class DailyMenu(
    val day: SchoolDay,
    val dishes: MutableList<Dish> = MenuItem.entries.map { Dish(it) }.toMutableList()
) {
    // ...
    fun rate(item: MenuItem, rating: Rating?): DailyMenu {
        val i = dishes.indexOfFirst { it.item == item }
        dishes[i] = dishes[i].copy(rating = rating)
        return copy()   // ¡parece un objeto nuevo!
    }
}
```

Ejecuta y pulsa caritas: **no pasa nada en pantalla**, aunque la lista sí cambia (míralo con un
`Log`). `copy()` crea otro `DailyMenu`, pero con *la misma* lista dentro, así que el nuevo es
`equals` al viejo y Compose no repinta. Es el problema del 0.4 en pequeño.
Deshaz el experimento.

✅ **Comprueba:** la app arranca sin nada marcado, se marca, se desmarca y "Limpiar" vacía todo.
Gira el móvil: **se pierde** (normal, `remember` no sobrevive al giro; lo arregla el paso 5).

---

## Paso 4 · `MainScreen` stateless y con colores

**4.1** Que `MainScreen` reciba los días y avise al pulsar:

```kotlin
@Composable
fun MainScreen(
    days: List<DailyMenu>,
    onDayClick: (SchoolDay) -> Unit,
    modifier: Modifier = Modifier
)
```

Recorre `days` y usa `day.result` para el color y `day.day.labelRes` para el texto.

**4.2** Define tres colores en `ui/theme/Color.kt` (bien, regular, mal) y traduce resultado → colores
en la capa de UI:

```kotlin
@Composable
fun DayResult.buttonColors(): ButtonColors = when (this) {
    DayResult.PENDING -> ButtonDefaults.buttonColors()
    DayResult.GOOD -> ButtonDefaults.buttonColors(containerColor = DayGood, contentColor = Color.White)
    DayResult.NEUTRAL -> ButtonDefaults.buttonColors(containerColor = DayNeutral, contentColor = Color.Black)
    DayResult.BAD -> ButtonDefaults.buttonColors(containerColor = DayBad, contentColor = Color.White)
}
```

Ojo con el amarillo: con texto blanco no se lee, por eso va en negro.

**4.3** Una `@Preview` con los cuatro casos a la vez. Reutiliza `rate()` para montar los datos
(o copia el `menuWith` de los tests):

```kotlin
val allHappy = MenuItem.entries.fold(DailyMenu(SchoolDay.MONDAY)) { m, item -> m.rate(item, Rating.HAPPY) }
```

✅ **Comprueba:** en la preview se ven verde, amarillo, rojo y botones normales.

> Extra de accesibilidad: el color no debería ser la única pista. Añade un icono o un
> `stateDescription` ("bien", "regular", "mal", "sin completar") para TalkBack.

---

## Paso 5 · UiState y ViewModel

**5.1** Dependencias (como en el paso 1 de Galaxy Tracker): `lifecycle-viewmodel-compose` y
`lifecycle-runtime-compose`, con la misma versión que `lifecycleRuntimeKtx`.

**5.2** `ui/ColepizzaUiState.kt`:

```kotlin
data class ColepizzaUiState(
    val days: List<DailyMenu> = SchoolDay.entries.map { DailyMenu(it) }
) {
    fun menuFor(day: SchoolDay): DailyMenu = days.first { it.day == day }
}
```

Igual que con los platos: el valor por defecto crea todos los días vacíos.

**5.3** `ui/ColepizzaViewModel.kt`:

```kotlin
class ColepizzaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ColepizzaUiState())
    val uiState: StateFlow<ColepizzaUiState> = _uiState.asStateFlow()

    fun rate(day: SchoolDay, item: MenuItem, rating: Rating?) =
        updateDay(day) { it.rate(item, rating) }

    fun clearDay(day: SchoolDay) =
        updateDay(day) { it.clear() }

    private fun updateDay(day: SchoolDay, change: (DailyMenu) -> DailyMenu) {
        _uiState.update { state ->
            state.copy(days = state.days.map { if (it.day == day) change(it) else it })
        }
    }
}
```

El ViewModel no sabe *cómo* se valora un plato (eso lo sabe `DailyMenu`); solo sabe *a qué día*
aplicarlo. Es el mismo `map { if (...) cambia else it }` del modelo, un nivel más arriba.

**5.4** Conecta `ColepizzaApp` al ViewModel. Todavía sin navegación, enseña un día fijo
(`uiState.menuFor(SchoolDay.MONDAY)`) y borra el estado temporal del paso 3.

✅ **Comprueba:** marca caritas y **gira el móvil**: ahora se mantienen.

---

## Paso 6 · Navegación: lista de días → menú del día

**6.1** Dependencias de Navigation 3 y el plugin de serialización (copiadas de Galaxy Tracker).

**6.2** Claves en `navigation/Keys.kt`:

```kotlin
@Serializable data object DayList : NavKey
@Serializable data class DayDetail(val day: SchoolDay) : NavKey
```

Los `enum` se serializan solos, así que el día puede viajar tal cual en la clave.
La clave lleva **solo el día**, no el `DailyMenu` entero: los datos se leen del ViewModel.

**6.3** En `ColepizzaApp`: `rememberNavBackStack(DayList)` + `NavDisplay` con dos `entry`:

- `entry<DayList>` → `MainScreen(days = uiState.days, onDayClick = { backStack.add(DayDetail(it)) })`.
- `entry<DayDetail>` → `ColepizzaScreen` con `uiState.menuFor(key.day)`,
  `viewModel.rate(key.day, ...)` y `viewModel.clearDay(key.day)`.

**6.4 ⚠️ Trampa:** el ViewModel se crea **una sola vez**, en `ColepizzaApp`, fuera del `NavDisplay`,
y se comparte entre las dos pantallas. Si haces `viewModel()` dentro de cada `entry` usando el
decorador de ViewModel de Navigation 3, cada pantalla tendrá su propio ViewModel: rellenarás el
lunes y al volver la lista seguirá sin color.

**6.5** `TopAppBar` con el nombre del día y flecha de volver, como en Galaxy Tracker.

✅ **Comprueba:** ver el checklist final.

---

## Checklist final

- [ ] Al abrir la app, ningún día tiene color y ningún plato tiene carita marcada.
- [ ] Pulsar una carita la marca; volver a pulsarla la desmarca.
- [ ] Con 4 de 5 platos valorados, al volver el botón sigue normal.
- [ ] Con 5 de 5: 😀😀😀😞😞 → verde · 😀😞😐😐😐 → amarillo · 😞😞😞😀😐 → rojo.
- [ ] Quitar una carita de un día completo hace que vuelva a su color normal.
- [ ] "Limpiar" deja el día como al principio.
- [ ] Rellenar el lunes no afecta al martes.
- [ ] Girar el móvil en cualquiera de las dos pantallas no pierde nada.
- [ ] Los tests de `DailyMenuTest` pasan.

## Retos opcionales

- Mostrar en cada botón "3/5" mientras el día no está completo (otra propiedad calculada en
  `DailyMenu`, no guardada).
- Un resumen semanal: cuántos días verdes, amarillos y rojos (`days.groupingBy { it.result }.eachCount()`).
- Rehacer el modelo con el enfoque **B** (mapas) sin tocar las pantallas más que en los parámetros.
  Si las pantallas cambian mucho, algo estaba mal separado.
- Que las valoraciones sobrevivan a cerrar la app (`SavedStateHandle` o Room, en el futuro).
