#!/bin/bash

echo "🚀 Setting up Compar-Car Development Environment"

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "✅ Docker and Docker Compose are installed"

# Create necessary directories if they don't exist
mkdir -p backend/src/main/java/com/comparcar
mkdir -p frontend/src

echo "📁 Project structure created"

# Set up frontend dependencies
echo "📦 Installing frontend dependencies..."
cd frontend
if [ -f "package.json" ]; then
    npm install
else
    echo "⚠️  Frontend package.json not found. Please run this script from the project root."
    exit 1
fi
cd ..

# Set up backend dependencies
echo "📦 Setting up backend dependencies..."
cd backend
if [ -f "build.gradle" ]; then
    # Check if Gradle is installed
    if command -v gradle &> /dev/null; then
        gradle build -x test
    else
        echo "⚠️  Gradle not found. Backend dependencies will be resolved during Docker build."
    fi
else
    echo "⚠️  Backend build.gradle not found. Please run this script from the project root."
    exit 1
fi
cd ..

echo "✅ Development environment setup complete!"
echo ""
echo "🎯 Next steps:"
echo "1. Start the application: docker-compose up -d"
echo "2. Access the frontend: http://localhost:3000"
echo "3. Access the backend API: http://localhost:8080"
echo "4. Access the database: localhost:5432"
echo ""
echo "🛠️  Development commands:"
echo "- View logs: docker-compose logs -f"
echo "- Stop services: docker-compose down"
echo "- Rebuild: docker-compose up --build"
echo "- Clean up: docker-compose down -v"
echo ""
echo "🔧 Backend Development:"
echo "- Run with Gradle: cd backend && ./gradlew bootRun"
echo "- Build: cd backend && ./gradlew build"
echo "- Test: cd backend && ./gradlew test" 