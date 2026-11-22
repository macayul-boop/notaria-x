# Notaria-X 📄

Aplicación móvil Android desarrollada en Kotlin, orientada a la gestión y seguimiento de trámites notariales. La plataforma permite conectar a clientes y notarios mediante dos tipos de interfaces, facilitando la solicitud, administración y seguimiento del estado de los trámites.

## 📌 Descripción del proyecto

Notaria-X busca digitalizar parte del proceso de gestión notarial, ofreciendo una plataforma donde los clientes puedan solicitar distintos tipos de trámites y consultar su progreso, mientras que los notarios pueden gestionar las solicitudes recibidas y mantener actualizado su estado.

La aplicación cuenta con dos perfiles de usuario:

- **Cliente:** puede registrarse, iniciar sesión, seleccionar trámites, adjuntar documentos y consultar el estado de sus solicitudes.
- **Notario:** puede visualizar solicitudes pendientes, tomar trámites para su gestión, revisar su información y actualizar sus estados.

Uno de los principales objetivos del proyecto es proporcionar un seguimiento organizado de cada trámite mediante un historial de eventos que permite visualizar las distintas etapas de su procesamiento.

## 🚀 Funcionalidades principales

### 👤 Módulo Cliente

- Registro de nuevos usuarios.
- Inicio y cierre de sesión.
- Visualización y edición del perfil.
- Consulta de los tipos de trámites disponibles.
- Selección y solicitud de trámites.
- Selección de archivos desde el dispositivo.
- Captura de fotografías mediante la cámara del dispositivo.
- Visualización de los trámites solicitados.
- Consulta del progreso e historial de cada trámite.

### ⚖️ Módulo Notario

- Inicio de sesión con perfil de notario.
- Visualización de trámites pendientes de asignación.
- Asignación de trámites al notario.
- Consulta de los trámites asignados.
- Actualización del estado de los trámites.
- Registro de eventos en el historial de seguimiento.

### 📊 Seguimiento de trámites

El sistema contempla diferentes eventos para representar el avance de una solicitud:

| Evento | Descripción |
|---|---|
| Solicitado | Se registra una nueva solicitud. |
| En revisión | El notario comienza a revisar el trámite. |
| Documento faltante | Se identifica documentación pendiente. |
| Finalizado | El trámite ha sido completado. |
| Rechazado | El trámite ha sido rechazado. |

Estos eventos permiten representar el ciclo de vida de las solicitudes y proporcionar información sobre su evolución.

## 🛠️ Tecnologías utilizadas

Las principales tecnologías y herramientas utilizadas en el desarrollo son:

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje principal de programación. |
| Android SDK | Desarrollo de la aplicación Android. |
| Jetpack Compose | Construcción de interfaces de usuario. |
| Material Design 3 | Componentes y diseño visual. |
| Navigation Compose | Navegación entre pantallas. |
| ViewModel | Gestión de la lógica de presentación. |
| Kotlin Coroutines | Ejecución de operaciones asíncronas. |
| StateFlow | Gestión y observación de estados. |
| Retrofit | Consumo de servicios REST. |
| Gson | Conversión de respuestas JSON. |
| Xano | Backend y API REST. |
| SharedPreferences | Persistencia de la sesión del usuario. |
| Gradle Kotlin DSL | Configuración y gestión del proyecto. |
| KSP | Procesamiento de código para Room. |

## 🏗️ Arquitectura del proyecto

El proyecto utiliza una organización por capas, separando las responsabilidades de presentación, lógica de negocio y acceso a datos.

### Estructura de paquetes

```text
com.duoc.notariax/
│
├── model/
│   ├── DTO
│   ├── Entidades
│   └── Modelos de datos
│
├── remote/
│   ├── ApiService.kt
│   └── RetrofitInstance.kt
│
├── repository/
│   ├── UsuarioRepository.kt
│   ├── TramiteRepository.kt
│   ├── TramitesUsuariosRepository.kt
│   ├── HistorialTramiteRepository.kt
│   └── ArchivosRepository.kt
│
├── ui/
│   ├── Pantallas de la aplicación
│   └── theme/
│
├── utils/
│   ├── SessionManager.kt
│   └── crearUriImagenTemporal.kt
│
├── viewmodel/
│   └── ViewModels de cada funcionalidad
│
└── MainActivity.kt
```

### Responsabilidad de cada capa

**1. Model**

Contiene las estructuras de datos utilizadas por la aplicación, incluyendo usuarios, trámites, estados, eventos de historial y objetos DTO para intercambiar información con la API.

**2. Remote**

Centraliza la configuración de comunicación con el backend.

- `RetrofitInstance`: instancia de Retrofit y configuración de la URL base.
- `ApiService`: define los endpoints HTTP utilizados por la aplicación.

**3. Repository**

Actúa como intermediario entre los ViewModels y las fuentes de datos. Gestiona las operaciones relacionadas con usuarios, trámites, asignaciones e historial.

**4. ViewModel**

Administra los estados de las pantallas y coordina las operaciones solicitadas por la interfaz. Utiliza Kotlin Coroutines y StateFlow para gestionar las operaciones asíncronas y actualizar la información presentada.

**5. UI**

Contiene las pantallas y componentes visuales desarrollados con Jetpack Compose. Se organiza según las funcionalidades de los perfiles cliente y notario.

**6. Utils**

Contiene herramientas auxiliares, como la administración de sesiones y la generación de URI temporales para las fotografías capturadas mediante la cámara.

## 🔌 Integración con el backend

La aplicación utiliza **Xano** como plataforma backend, consumiendo sus servicios mediante una API REST implementada con Retrofit.

Entre las operaciones disponibles desde `ApiService` se encuentran:

| Método HTTP | Recurso | Operación |
|---|---|---|
| POST | `usuarios` | Registro de usuarios. |
| POST | `login` | Inicio de sesión. |
| GET | `usuarios/{id}` | Obtener información de un usuario. |
| PATCH | `usuarios/{id}` | Actualizar información del usuario. |
| GET | `tramites` | Obtener trámites disponibles. |
| GET | `tramites/{id}` | Obtener un trámite específico. |
| POST | `tramites_usuarios` | Crear una solicitud de trámite. |
| GET | `tramites_usuarios/usuario/{id}` | Consultar trámites de un cliente. |
| GET | `tramites_sin_notario` | Consultar trámites pendientes de asignación. |
| PATCH | `tramites_usuarios/{id}/tomar` | Asignar un trámite a un notario. |
| PATCH | `tramites_usuarios/{id}/estado` | Actualizar el estado de un trámite. |
| POST | `historial_tramites` | Registrar eventos en el historial. |

## 📷 Uso de cámara y archivos

La aplicación incorpora funcionalidades de acceso a recursos del dispositivo Android.

**Selección de archivos:**

Se utiliza `ActivityResultContracts.GetMultipleContents()` para permitir que el usuario seleccione uno o varios archivos desde el dispositivo.

**Captura de fotografías:**

Se utiliza `ActivityResultContracts.TakePicture()` junto con `FileProvider` para generar una URI temporal y almacenar la fotografía capturada en la caché de la aplicación.

Estas funcionalidades permiten seleccionar los documentos necesarios durante el proceso de solicitud de un trámite.

> Nota: En la implementación actual, los archivos seleccionados se administran temporalmente mediante `ArchivosRepository`. Su carga y almacenamiento definitivo en el backend no forman parte todavía del flujo implementado.

## ⚙️ Requisitos de instalación

Para ejecutar el proyecto se necesitan las siguientes herramientas:

- Android Studio.
- JDK 21.
- Android SDK 37.
- Gradle 9.4.1 (incluido mediante Gradle Wrapper).
- Un emulador Android o dispositivo físico con Android 7.0 (API 24) o superior.

**Configuración técnica:**

| Parámetro | Versión / Valor |
|---|---|
| Lenguaje | Kotlin 2.2.10 |
| Android Gradle Plugin | 9.2.1 |
| Gradle | 9.4.1 |
| JDK | 21 |
| compileSdk | 37 (minor API level 1) |
| minSdk | 24 |
| targetSdk | 36 |
| applicationId | `com.duoc.notariax` |

## 📥 Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/macayul-boop/notaria-x.git
```

### 2. Abrir el proyecto

Abrir Android Studio y seleccionar la carpeta raíz del proyecto `notaria-x`.

Esperar a que Gradle sincronice las dependencias necesarias.

### 3. Configuración del backend

La conexión con Xano se configura en:

```text
app/src/main/java/com/duoc/notariax/remote/RetrofitInstance.kt
```

En este archivo se encuentra definida la URL base utilizada por Retrofit.

Si se desea utilizar otra instancia de Xano, será necesario actualizar esta configuración con la URL correspondiente.

Para un despliegue o distribución de producción, se recomienda externalizar esta configuración en lugar de mantenerla directamente en el código fuente.

### 4. Ejecutar la aplicación

Desde Android Studio:

1. Seleccionar un emulador Android o conectar un dispositivo físico.
2. Esperar la sincronización de Gradle.
3. Seleccionar la configuración de ejecución `app`.
4. Presionar **Run** para compilar e iniciar la aplicación.

También se puede utilizar Gradle Wrapper desde una terminal:

**Windows:**

```bash
gradlew.bat assembleDebug
```

**Linux / macOS:**

```bash
./gradlew assembleDebug
```

## 🧪 Pruebas

El proyecto incorpora pruebas unitarias utilizando JUnit y Kotlin Coroutines Test.

Para ejecutar las pruebas unitarias:

**Windows:**

```bash
gradlew.bat test
```

**Linux / macOS:**

```bash
./gradlew test
```

## 📱 Permisos de Android

La aplicación declara los siguientes permisos y características en `AndroidManifest.xml`:

| Permiso / Característica | Propósito |
|---|---|
| INTERNET | Comunicación con el backend mediante servicios web. |
| CAMERA | Acceso a la cámara del dispositivo. |
| FileProvider | Compartir URI de archivos temporales con la aplicación de cámara. |

La cámara se declara como característica opcional para permitir la instalación en dispositivos que no dispongan de ella.

## 📌 Consideraciones del proyecto

- La aplicación requiere conexión a Internet para realizar las operaciones que dependen de Xano.
- La sesión del usuario se mantiene mediante SharedPreferences.
- El acceso a las funcionalidades se determina según el tipo de usuario autenticado.
- La gestión definitiva de archivos adjuntos en el servidor se encuentra pendiente de implementación.

## 👨‍💻 Autoría

**Notaria-X**

Proyecto de desarrollo de aplicación móvil Android.

Repositorio: [github.com/macayul-boop/notaria-x](https://github.com/macayul-boop/notaria-x)

Tecnologías principales: Kotlin, Android, Jetpack Compose y Xano.
