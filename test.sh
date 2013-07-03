#!/bin/sh
function echo_red {
   echo $'\e[31m'"$1"$'\e[00m'
}

function echo_green {
   echo $'\e[32m'"$1"$'\e[00m'
}

CASE_BASE_DIR="src/test/cases"
TARGET_BASE_DIR="target/cases"
PROJ_DIR=`pwd`
HOTCODE_AGENT_PATH="${PROJ_DIR}/target/hotcode.jar"
FAILED="false"
mkdir -p ${TARGET_BASE_DIR}
cd ${CASE_BASE_DIR}

for CASE in `find . -type d -d 1`; do
    CASE_SOURCE_DIR="${PROJ_DIR}/${CASE_BASE_DIR}/${CASE}"
    CASE_TARGET_DIR="${PROJ_DIR}/${TARGET_BASE_DIR}/${CASE}"
    rm -r ${CASE_TARGET_DIR}
    mkdir -p ${CASE_TARGET_DIR}
    cp "${CASE_SOURCE_DIR}/A.java" "${CASE_TARGET_DIR}/A.java"
    cd ${CASE_TARGET_DIR}
    javac A.java
    cp "${PROJ_DIR}/${CASE_BASE_DIR}/Base.java" "${CASE_TARGET_DIR}/Base.java"
    cd ${CASE_TARGET_DIR}
    javac Base.java
    `java -javaagent:${HOTCODE_AGENT_PATH} -noverify Base ${CASE} > result &`
    cp ${CASE_SOURCE_DIR}/A1.java ${CASE_TARGET_DIR}/A.java
    javac A.java
    sleep 0.5
    RESULT=`cat ${CASE_TARGET_DIR}/result`
    IS_SUCCESS=`echo ${RESULT} | grep success`
    if [ -z "${IS_SUCCESS}" ]; then
        echo_red "${RESULT}"
        FAILED="true"
    else
        echo_green "${RESULT}"
    fi
done

if [ "${FAILED}" == "true" ]; then
    exit 1
fi