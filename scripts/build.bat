@echo off
echo 🔧 Limpiando proyecto Maven...
call mvn clean

echo 📦 Descargando dependencias...
call mvn install -DskipTests

echo ✅ Proyecto compilado exitosamente
echo 💡 Ahora puedes ejecutar: mvn spring-boot:run
pause
