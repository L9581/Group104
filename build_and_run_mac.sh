#!/usr/bin/env bash
# macOS Build and Run Script for Group104 TA Recruitment System

# Exit immediately if a command exits with a non-zero status
set -e

# Setup directory paths relative to the script location
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$SCRIPT_DIR/codes/src"
BUILD_DIR="$SCRIPT_DIR/codes/build"
DATA_DIR="$SCRIPT_DIR/codes/data"

echo "--- Group104 macOS Build Process ---"

# 1. Verify Java 21 is available
if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "21"; then
    echo "Error: Java 21 not found. Please install JDK 21 and ensure it is in your PATH." >&2
    exit 1
fi

# 2. Prepare Build Directory
# We only clear the build directory, leaving codes/data/ untouched as requested
if [ -d "$BUILD_DIR" ]; then
    rm -rf "$BUILD_DIR"
fi
mkdir -p "$BUILD_DIR"

# 3. Find all Java source files
# This mimics the recursive search used in the Windows .bat script
SOURCES_FILE=$(mktemp)
find "$SRC_DIR" -name "*.java" > "$SOURCES_FILE"

if [ ! -s "$SOURCES_FILE" ]; then
    echo "Error: No Java source files found in $SRC_DIR" >&2
    rm "$SOURCES_FILE"
    exit 1
fi

# 4. Compile
echo "Compiling source files to $BUILD_DIR..."
javac -d "$BUILD_DIR" @"$SOURCES_FILE"
rm "$SOURCES_FILE"

# 5. Run the application
echo "Starting app.Main..."
java -cp "$BUILD_DIR" app.Main