#!/usr/bin/env bash


# Fail immediately in case of errors and/or unset variables
set -eu -o pipefail

ERROR_PRONE_VERSION=2.3.1
JAVAC_ARTEFACT_JAR="javac-9-dev-r4023-3.jar"
REFASTER_ARTEFACT_JAR="error_prone_refaster-${ERROR_PRONE_VERSION}.jar"

# Uncomment to download
# wget "http://repo1.maven.org/maven2/com/google/errorprone/javac/9-dev-r4023-3/\
# ${JAVAC_ARTEFACT_JAR}"
# wget "http://repo1.maven.org/maven2/com/google/errorprone/error_prone_refaster/\
# ${ERROR_PRONE_VERSION}/${REFASTER_ARTEFACT_JAR}"

CLASSPATH="${REFASTER_ARTEFACT_JAR}:$(cat target/rules-classpath.txt)"

INPUT_FILENAME="$1"
OUTPUT_FILENAME="$2"

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