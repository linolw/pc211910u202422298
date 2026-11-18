#!/bin/sh
# Maven Wrapper for Unix (only-script style; no wrapper JAR).
set -eu
PROJECT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
PROPS="$PROJECT_DIR/.mvn/wrapper/maven-wrapper.properties"
if [ ! -f "$PROPS" ]; then
  echo 'ERROR: missing .mvn/wrapper/maven-wrapper.properties' >&2
  exit 1
fi
DISTRIBUTION_URL=$(sed -n 's/^distributionUrl=//p' "$PROPS" | head -n 1)
ARCHIVE_NAME="${DISTRIBUTION_URL##*/}"
FOLDER_NAME="${ARCHIVE_NAME%-bin.zip}"
if [ -z "$DISTRIBUTION_URL" ] || [ "$ARCHIVE_NAME" = "$FOLDER_NAME" ]; then
  echo 'ERROR: invalid distributionUrl in Maven wrapper properties' >&2
  exit 1
fi
MAVEN_USER_HOME="${MAVEN_USER_HOME:-$HOME/.m2}"
MAVEN_HOME="$MAVEN_USER_HOME/wrapper/dists/$FOLDER_NAME"
MAVEN_BIN="$MAVEN_HOME/bin/mvn"
if [ ! -x "$MAVEN_BIN" ]; then
  TMP_DIR=$(mktemp -d)
  trap 'rm -rf "$TMP_DIR"' EXIT HUP INT TERM
  printf 'Downloading Apache Maven from %s\n' "$DISTRIBUTION_URL"
  if command -v curl >/dev/null 2>&1; then
    curl --fail --location --silent --show-error "$DISTRIBUTION_URL" --output "$TMP_DIR/$ARCHIVE_NAME"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DISTRIBUTION_URL" -O "$TMP_DIR/$ARCHIVE_NAME"
  else
    echo 'ERROR: curl or wget required on first launch' >&2; exit 1
  fi
  mkdir -p "$TMP_DIR/expanded" "$(dirname "$MAVEN_HOME")"
  unzip -q "$TMP_DIR/$ARCHIVE_NAME" -d "$TMP_DIR/expanded"
  mv "$TMP_DIR/expanded/$FOLDER_NAME" "$MAVEN_HOME"
  rm -rf "$TMP_DIR"
  trap - EXIT HUP INT TERM
fi
exec "$MAVEN_BIN" "$@"
