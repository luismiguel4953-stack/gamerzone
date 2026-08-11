# GamerZone — Información del APK

## ¿Qué es?
GamerZone es una aplicación Android orientada a una comunidad gamer. La versión actual incluye una interfaz de perfil, catálogo de videojuegos, clips, ranking, misiones, torneos, notificaciones y chat entre amigos.

## Identificación
- Aplicación: GamerZone
- ID de aplicación: `com.aistudio.gamerzone.app`
- Versión: 1.0
- Versión de código: 1
- Android mínimo: API 24 (Android 7.0)
- Android objetivo: API 36
- Tipo de compilación: Debug / pruebas

## Funciones incluidas
- Perfil de jugador, nivel, XP, monedas y rango.
- Catálogo de juegos con búsqueda, géneros y favoritos.
- Clips gaming con likes, comentarios y seguimiento.
- Ranking de jugadores.
- Misiones y recompensas.
- Torneos y registro.
- Notificaciones.
- Lista de amigos y chat.
- Persistencia local mediante Room.

## Estado actual
Esta versión es una compilación de prueba. Algunas funciones todavía usan datos locales o demostrativos y no deben considerarse servicios online reales hasta conectar autenticación, base de datos remota, almacenamiento de archivos, backend y permisos Android correspondientes.

## APK
El APK generado por el proyecto se publica mediante GitHub Actions como artefacto de compilación. Para obtener la versión más reciente, abre la pestaña **Actions/Acciones**, entra en la ejecución exitosa de **Build APK** y descarga el artefacto `GamerZone-debug-apk`.

## Nota de seguridad
No se deben añadir permisos sensibles solamente para que aparezcan en la aplicación. Cada permiso debe corresponder a una función real y solicitarse al usuario en tiempo de ejecución cuando Android lo requiera.
