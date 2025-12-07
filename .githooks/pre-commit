#!/bin/bash

echo "🚀 Starting Local CI..."

###############################################
# 1. Проверка форматирования (Spotless)
###############################################
echo "📝 Checking code format..."
./mvnw spotless:check
if [ $? -ne 0 ]; then
    echo "❌ Code format check failed. Run: ./mvnw spotless:apply"
    exit 1
fi

###############################################
# 2. Проверка линтера (Checkstyle)
###############################################
echo "🔍 Running linter (Checkstyle)..."
./mvnw checkstyle:check
if [ $? -ne 0 ]; then
    echo "❌ Checkstyle failed"
    exit 1
fi

###############################################
# 3. Запуск тестов
###############################################
echo "🧪 Running tests..."
./mvnw test
if [ $? -ne 0 ]; then
    echo "❌ Tests failed"
    exit 1
fi

###############################################
# 4. Сборка проекта
###############################################
echo "🔨 Building project..."
./mvnw clean compile -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Build failed"
    exit 1
fi

###############################################
# 5. Проверка совместимости Java (как в CI)
###############################################
echo "⚙️  Checking Java version compatibility..."
./mvnw compiler:testCompile "-Dmaven.compiler.release=21"
if [ $? -ne 0 ]; then
    echo "❌ Java compatibility check failed"
    exit 1
fi

###############################################
# 6. Сборка Docker image (аналог docker-build job)
###############################################
echo "🐳 Building Docker image..."

IMAGE_NAME="shortened-links:local"

docker build -t $IMAGE_NAME .
if [ $? -ne 0 ]; then
    echo "❌ Docker build failed"
    exit 1
fi

echo "📦 Docker image built: $IMAGE_NAME"

###############################################
# 7. (Опционально) Push в GHCR
###############################################
if [ "$1" == "--push" ]; then
    echo "📤 Pushing image to GHCR..."

    GHCR_IMAGE="ghcr.io/${USER,,}/shortened-links:local"

    docker tag $IMAGE_NAME $GHCR_IMAGE
    docker push $GHCR_IMAGE

    if [ $? -ne 0 ]; then
        echo "❌ Docker push failed"
        exit 1
    fi

    echo "✅ Image pushed: $GHCR_IMAGE"
fi

###############################################

echo "🎉 Local CI completed successfully!"
exit 0
