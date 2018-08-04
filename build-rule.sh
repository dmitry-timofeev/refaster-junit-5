#!/usr/bin/env bash


# Fail immediately in case of errors and/or unset variables
set -eu -o pipefail

CLASSPATH_FILE="target/rules-classpath.txt"
if [ ! -f "${CLASSPATH_FILE}" ]; then
  echo "No rules-classpath file, building the project."
  mvn generate-sources --quiet
fi
CLASSPATH="$(cat ${CLASSPATH_FILE})"

INPUT_FILENAME="$1"
OUTPUT_FILENAME="$2"

echo "Building the Refaster rule from ${INPUT_FILENAME}"
javac \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED \
    -J--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED \
    -J--add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED \
    -classpath "${CLASSPATH}" \
    "-Xplugin:RefasterRuleCompiler --out $(pwd)/${OUTPUT_FILENAME}" \
    ${INPUT_FILENAME}
