# Guia tecnica del proyecto Plantas

## 1) Objetivo del proyecto

Este proyecto es una aplicacion Android desarrollada con Kotlin + Jetpack Compose para mostrar informacion de plantas medicinales del sureste mexicano.  
Incluye catalogo, sintomas, remedios, favoritos y pantallas de detalle.

Ademas, ahora incorpora una segunda actividad (`Actividad2`) basada en interfaz XML + Fragment + ViewModel.

---

## 2) Stack tecnologico actual

- Lenguaje: `Kotlin`
- UI principal: `Jetpack Compose` (Material 3)
- Navegacion principal: `navigation-compose`
- Carga de imagenes: `Coil`
- Estado UI: `ViewModel` + `StateFlow`
- UI de `Actividad2`: XML + `FragmentContainerView` + `BottomNavigationView`
- Fragments: `androidx.fragment:fragment-ktx`

---

## 3) Estructura principal del codigo

### Entrada de la app

- `app/src/main/java/com/example/plantas/MainActivity.kt`
  - Inicia tema Compose y llama `AppNavigation()`.

### Navegacion Compose (Actividad1)

- `app/src/main/java/com/example/plantas/navigation/AppNavigation.kt`
  - Define rutas de pantallas (`Screen`).
  - Muestra/oculta barra de navegacion inferior.
- `app/src/main/java/com/example/plantas/components/BottomNavBar.kt`
  - Menu principal de navegacion.
  - Navega entre pantallas Compose.
  - Ahora incluye acceso a `Actividad2`.

### Estado global Compose

- `app/src/main/java/com/example/plantas/PlantasViewModel.kt`
  - Maneja planta/remedio/sintoma seleccionado.
  - Maneja favoritos en memoria (`StateFlow`).

### Pantallas Compose

- `app/src/main/java/com/example/plantas/screens/HomeScreen.kt`
- `app/src/main/java/com/example/plantas/screens/CatalogScreen.kt`
- `app/src/main/java/com/example/plantas/screens/PlantDetailScreen.kt`
- `app/src/main/java/com/example/plantas/screens/SymptomsScreen.kt`
- `app/src/main/java/com/example/plantas/screens/SymptomDetailScreen.kt`
- `app/src/main/java/com/example/plantas/screens/RemediesScreen.kt`
- `app/src/main/java/com/example/plantas/screens/RemedyDetailScreen.kt`
- `app/src/main/java/com/example/plantas/screens/FavoritesScreen.kt`

> Importante: gran parte de los datos de la app estan definidos en listas hardcodeadas dentro de archivos Kotlin.

### Actividad2 (XML + Fragment + ViewModel)

- `app/src/main/java/com/example/plantas/Actividad2.kt`
  - Actividad secundaria.
  - Renderiza `SoyUnFragmentoFragment`.
  - Tiene menu de navegacion inferior para volver a Actividad1.
- `app/src/main/java/com/example/plantas/Actividad2ViewModel.kt`
  - Estado de `Actividad2`.
  - Expone `fragmentText` como `StateFlow`.
- `app/src/main/java/com/example/plantas/SoyUnFragmentoFragment.kt`
  - Fragmento que observa el `ViewModel` y muestra:
  - `"soy un fragmento"`
- `app/src/main/res/layout/activity_actividad2.xml`
  - Interfaz de `Actividad2`.
  - Incluye titulo, contenedor de fragmento y `BottomNavigationView`.
- `app/src/main/res/layout/fragment_soy_un_fragmento.xml`
  - Interfaz del fragmento.
- `app/src/main/res/menu/menu_actividad2_navigation.xml`
  - Menu inferior con boton "Ir a Actividad1".

---

## 4) Flujo de navegacion actual

1. La app inicia en `MainActivity` (Actividad1).
2. Dentro de Compose, el usuario navega por Home/Catalogo/Sintomas/Remedios/Favoritas.
3. En la barra inferior aparece opcion `Actividad2`.
4. Al tocar `Actividad2`, se abre la actividad secundaria (`Actividad2`).
5. En `Actividad2`, el boton del menu inferior "Ir a Actividad1" vuelve a `MainActivity`.

---

## 5) Como ejecutar el proyecto

1. Abrir la carpeta raiz del proyecto en Android Studio:
  - `/Users/arturoc/AndroidStudioProjects/2actividades2/ProyectoDeSamuelyemir`
2. Verificar SDK y JDK compatibles en Android Studio.
3. Sincronizar Gradle.
4. Ejecutar `app` en emulador o dispositivo.

---

## 6) Estado tecnico actual (fortalezas y deuda tecnica)

### Fortalezas

- UI Compose avanzada y visualmente cuidada.
- Uso de `ViewModel` y `StateFlow`.
- Navegacion funcional entre varias pantallas.
- Separacion inicial entre actividad principal y `Actividad2`.

### Deuda tecnica

- Datos hardcodeados en varias capas (duplicacion de informacion).
- Sin persistencia real de favoritos (se pierde al cerrar app).
- No hay capa de datos/repositorio.
- No hay pruebas automatizadas (unitarias/UI).
- Mezcla de enfoque Compose y XML sin una estrategia clara a futuro.

---

## 7) Guia para mejorar Actividad2 (recomendado)

A continuacion, una ruta pragmatica para mejorar `Actividad2` sin romper el proyecto.

### Paso 1: definir un contrato de estado en el ViewModel

Crear una clase de estado para evitar variables sueltas:

- `Actividad2UiState(texto: String, cargando: Boolean, error: String?)`

Beneficio: escalabilidad y mantenimiento.

### Paso 2: mover logica del Fragment a un patron claro

En `SoyUnFragmentoFragment`:

- Mantener solo renderizado de UI.
- Delegar transformaciones de estado al `Actividad2ViewModel`.
- Evitar logica de negocio en el fragmento.

### Paso 3: estandarizar la navegacion entre actividades

En lugar de `Intent` directo en multiples lugares:

- Crear helper de navegacion (por ejemplo `Navigator` simple).
- Centralizar extras/flags de intents.
- Facilitar pruebas y cambios futuros.

### Paso 4: mejorar UI/UX de Actividad2

- Aplicar `MaterialToolbar` con titulo y opcion regresar.
- Mantener consistencia visual con la paleta de la app principal.
- Mostrar estado vacio/cargando/error segun el estado del ViewModel.

### Paso 5: preparar migracion progresiva a Compose

Si se desea unificar arquitectura:

- Migrar `Actividad2` a Compose gradualmente.
- O mantener XML+Fragment solo si existe requisito academico.
- Definir una sola estrategia de UI para todo el proyecto.

### Paso 6: agregar pruebas minimas

- Unit test para `Actividad2ViewModel`.
- Test de fragmento (instrumentado) que verifique:
  - Se muestra "soy un fragmento".
  - Boton "Ir a Actividad1" ejecuta la navegacion esperada.

---

## 8) Propuesta de arquitectura objetivo (mediano plazo)

Para mantener el proyecto mas ordenado:

- `ui/` -> pantallas, componentes, estados UI
- `domain/` -> casos de uso y modelos de negocio
- `data/` -> repositorios y fuentes de datos
- `core/` -> utilidades, constantes, navegacion, errores

Esto permitiria luego conectar API o base de datos sin reescribir la UI.

---

## 9) Checklist rapido de mejora para el equipo

- Eliminar duplicacion de rutas/dependencias.
- Centralizar datos de plantas/sintomas/remedios en repositorio.
- Persistir favoritos (Room o DataStore).
- Crear pruebas para ViewModels.
- Definir estrategia unica de UI (Compose total o hibrida documentada).
- Documentar decisiones en un `README.md`.

---

## 10) Siguiente paso sugerido

Primera mejora recomendada de alto impacto y bajo riesgo:

1. Crear `Actividad2UiState`.
2. Refactor de `Actividad2ViewModel` para exponer solo `uiState`.
3. Ajustar `SoyUnFragmentoFragment` para renderizar ese `uiState`.

Con eso quedara una base limpia para evolucionar `Actividad2` y para replicar el patron en otras pantallas.