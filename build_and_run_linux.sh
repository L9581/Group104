#!/usr/bin/env bash
# Linux Build and Run Script for Group104 TA Recruitment System

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$SCRIPT_DIR/codes/src"
BUILD_DIR="$SCRIPT_DIR/codes/build"

echo "--- Group104 Linux Build Process ---"

if ! command -v java >/dev/null 2>&1 || ! java -version 2>&1 | grep -q '"21'; then
    echo "Error: Java 21 not found. Please install JDK 21 and ensure it is in your PATH." >&2
    exit 1
fi

if [ -d "$BUILD_DIR" ]; then
    rm -rf "$BUILD_DIR"
fi
mkdir -p "$BUILD_DIR"

SOURCES_FILE="$(mktemp)"
trap 'rm -f "$SOURCES_FILE"' EXIT

find "$SRC_DIR" -name "*.java" > "$SOURCES_FILE"

if [ ! -s "$SOURCES_FILE" ]; then
    echo "Error: No Java source files found in $SRC_DIR" >&2
    exit 1
fi

echo "Compiling source files to $BUILD_DIR..."
javac -d "$BUILD_DIR" @"$SOURCES_FILE"

echo "Starting app.Main..."
java -cp "$BUILD_DIR" app.Main
