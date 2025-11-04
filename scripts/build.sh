#!/bin/bash

echo "🔧 Limpiando proyecto Maven..."
mvn clean

echo "📦 Descargando dependencias..."
mvn install -DskipTests

echo "✅ Proyecto compilado exitosamente"
echo "💡 Ahora puedes ejecutar: mvn spring-boot:run"
