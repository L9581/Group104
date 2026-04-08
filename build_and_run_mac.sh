#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd -- "$(dirname -- "$0")" && pwd)"
src_dir="$script_dir/codes/src"
build_dir="$script_dir/codes/build"

rm -rf "$build_dir"
mkdir -p "$build_dir"

sources=()
while IFS= read -r source_file; do
    sources+=("$source_file")
done < <(find "$src_dir" -name "*.java" | sort)

if [ "${#sources[@]}" -eq 0 ]; then
    echo "No Java sources found in $src_dir" >&2
    exit 1
fi

javac -d "$build_dir" "${sources[@]}"
exec java -cp "$build_dir" app.RecruitmentApp
