# 🐉 Bestiario RPG – Android + SQLite

Aplicación Android tipo **bestiario de un RPG**, desarrollada como entrega de un feedback/práctica de la asignatura.  
El objetivo principal del proyecto es aplicar los conceptos vistos en clase de forma **clara, funcional y sin sobrecomplicar**, cumpliendo todos los requisitos mínimos solicitados.

La app está pensada para ejecutarse en dispositivos con **Android 7.x o superior**.

---

## 🎯 Objetivo del proyecto

Desarrollar una aplicación Android completa que incluya:

- Varias pantallas (Activities)
- Uso de distintos controles visuales
- Persistencia de datos con **SQLite**
- Navegación mediante **menús / ActionBar**
- Uso correcto de **diálogos**
- Código claro y estructurado

Todo ello aplicado a un caso práctico sencillo: un **bestiario de monstruos**.

---

## 🧩 Funcionalidades principales

- 📋 **Listado de monstruos** en un `ListView`
- ➕ **Añadir** nuevos monstruos
- ✏️ **Editar** monstruos existentes
- 🔍 **Ver detalle** de cada monstruo
- ❌ **Eliminar** monstruos (con confirmación)
- ☑️ Marcar monstruos como **derrotados**
- 🔎 **Filtrar** el listado para mostrar solo los derrotados
- 🖼️ Uso de **imágenes** asociadas al tipo de monstruo
- ℹ️ Diálogo **“Acerca de”** accesible desde todas las pantallas

---

## 🖥️ Pantallas (Activities)

La aplicación cuenta con **3 Activities**, tal y como se pide en el enunciado:

### 1️⃣ MainActivity
Pantalla principal de la aplicación.

- Muestra el listado de monstruos en un `ListView`
- Permite:
    - Añadir nuevos monstruos
    - Filtrar por monstruos derrotados
    - Eliminar un monstruo mediante pulsación larga
- Incluye menú con acciones y diálogo **Acerca de**

### 2️⃣ DetailActivity
Pantalla de detalle de un monstruo concreto.

- Muestra:
    - Imagen del monstruo (`ImageView`)
    - Nombre y nivel (`TextView`)
    - Estado de derrotado (`CheckBox`)
- Acciones disponibles desde el menú:
    - Editar monstruo
    - Eliminar monstruo (con confirmación)
    - Acerca de

### 3️⃣ EditMonsterActivity
Pantalla para añadir o editar monstruos.

- Permite introducir:
    - Nombre
    - Nivel
    - Tipo
    - Estado derrotado
- Reutiliza la misma pantalla tanto para **alta** como para **modificación**
- Incluye menú con acceso a **Acerca de**

---

## 🧱 Controles utilizados

Tal y como exige el feedback, se utilizan los siguientes controles:

- `ListView` → listado principal
- `TextView` → textos y datos
- `ImageView` → imagen del monstruo
- `Button` → acciones (guardar / editar)
- `CheckBox` → estado derrotado
- (`EditText` y `Spinner` como controles adicionales)

---

## 🗄️ Base de datos (SQLite)

Se utiliza **SQLite** mediante `SQLiteOpenHelper`.

### Tabla `monsters`
Campos:
- `_id` → clave primaria autoincremental
- `name` → nombre del monstruo
- `level` → nivel
- `defeated` → 0 / 1
- `type` → tipo del monstruo

### Operaciones implementadas
- Alta (`addMonster`)
- Modificación (`updateMonster`)
- Borrado (`deleteMonster`)
- Consulta individual (`getMonster`)
- Listado completo (`getAll`)

La base de datos incluye **datos de ejemplo** al crearse para que la aplicación tenga contenido desde el primer arranque.

---

## 📂 Menú y ActionBar

La aplicación utiliza un **menú común** que se adapta según la pantalla:

- En la pantalla principal:
    - Añadir
    - Filtrar
    - Acerca de
- En el detalle:
    - Editar
    - Eliminar
    - Acerca de
- En la edición:
    - Acerca de

De esta forma se cumple el requisito de **acciones accesibles desde la ActionBar** en cada Activity.

---

## 💬 Uso de diálogos

Se utilizan diálogos (`AlertDialog`) cuando es necesario:

- Confirmación antes de eliminar un monstruo
- Diálogo **Acerca de** con información del proyecto

---

## ▶️ Cómo usar la aplicación

1. Al abrir la app se muestra el listado de monstruos.
2. Pulsa un monstruo para ver su detalle.
3. Desde el detalle puedes:
    - Editarlo
    - Eliminarlo (con confirmación)
4. Desde el menú principal puedes:
    - Añadir un nuevo monstruo
    - Filtrar los derrotados
5. También puedes eliminar directamente desde la lista con una pulsación larga.

---

## 📦 APK y entrega

El proyecto incluye un **APK instalable** generado desde Android Studio para facilitar la prueba de la aplicación sin necesidad de compilar el código.

---

## 🚀 Posibles mejoras futuras

- Uso de imágenes propias
- Búsqueda por nombre
- Validaciones más avanzadas
- Mejoras visuales en layouts
- Separar menús específicos por Activity

---

## 👤 Autor

JuanMaTB  
