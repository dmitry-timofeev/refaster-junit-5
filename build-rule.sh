#!/usr/bin/env bash
# Builds a Refaster rule from a Java template file.
#
# Usage: ./build-rule.sh path/to/InputRule.java output-rule-name.refaster

# Fail immediately in case of errors and/or unset variables
set -eu -o pipefail

CLASSPATH_FILE="target/rules-classpath.txt"
if [ ! -f "${CLASSPATH_FILE}" ]; then
  echo "[INFO] No rules-classpath file, building the project."
  mvn generate-sources --quiet
fi
CLASSPATH="$(cat ${CLASSPATH_FILE})"

INPUT_FILE_PATH="$1"
OUTPUT_FILENAME="$2"

echo "[INFO] Building the Refaster rule from ${INPUT_FILE_PATH}"
javac \
    -d target/classes \
    -classpath "${CLASSPATH}" \
    "-Xplugin:RefasterRuleCompiler --out $(pwd)/${OUTPUT_FILENAME}" \
    ${INPUT_FILE_PATH}
