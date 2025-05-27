##  Descripción
Este proyecto es un reproductor de música desarrollado en *Java* que permite la reproducción de canciones obtenidas desde un backend mediante *Retrofit. Incluye una interfaz gráfica que ofrece una experiencia fluida y estética, con un disco que **gira* mientras suena la música.  

---

##  *Tecnologías utilizadas*
### 🔹 *Backend*
- *Java Spring Boot* – API para gestionar y proporcionar canciones.  
- *Retrofit* – Cliente HTTP para consumir el servicio de canciones.  
- *HTTPURLConnection* – Manejo de descargas seguras desde el servidor.  
- *JSON* – Formato de respuesta del backend.
- *JLayer (javazoom)* – Biblioteca para reproducción de archivos MP3.  

### 🔹 *Frontend*
- *Java Swing* – Interfaz gráfica del reproductor.  
- *GridBagLayout* – Alineación responsiva de los elementos.  
- *Timer* – Animación del disco giratorio.  
- *BufferedImage y Graphics2D* – Manipulación de imágenes para la animación del disco.  

---

## *Funcionalidad*
### 🔹 *Backend (API de Canciones)*
✅ *Obtener lista de canciones* del servidor.  
✅ *Descargar archivos MP3* desde el backend.  
✅ *Reproducir, pausar y detener canciones*.  

### 🔹 *Frontend (Interfaz del Reproductor)*
✅ *Interfaz intuitiva* con botones de reproducción.  
✅ *Lista desplegable para seleccionar canciones*. 
✅ *Pausa de la animación* cuando se pausa o detiene la canción.  

---

## *Cómo instalar y ejecutar*
### 🔹 *Requisitos previos*
1️⃣ *Java JDK 8+ instalado*.  
2️⃣ *Dependencias de Retrofit y JLayer* en el pom.xml si usas Maven.  
3️⃣ *Servidor backend activo* para proveer las canciones.  

### 🔹 *Instrucciones*
```bash
# Clonar el repositorio
git clone https://github.com/usuario/reproductor-musica.git

# Navegar al directorio del proyecto
cd reproductor-musica

# Ejecutar el backend (Spring Boot)
mvn spring-boot:run

# Ejecutar la aplicación Java Swing
java -jar ReproductorMusica.jar