#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd -- "$(dirname -- "$0")" && pwd)"
src_dir="$script_dir/codes/src"
build_dir="$script_dir/codes/build"

rm -rf "$build_dir"
mkdir -p "$build_dir"

javac -d "$build_dir" "$src_dir"/*.java
exec java -cp "$build_dir" RecruitmentApp
