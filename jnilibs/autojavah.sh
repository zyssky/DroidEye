#!/usr/bin/env bash
export ProjectPath=$(cd "../$(dirname "$1")"; pwd)
export TargetClassName="com.example.administrator.droideye.Utils.JniUtils"

export SourceFile="${ProjectPath}/app/src/main/java"
export TargetPath="${ProjectPath}/jnilibs/src/main/jni"

cd "${SourceFile}"
javah -d ${TargetPath} -classpath "${SourceFile}" "${TargetClassName}"
echo -d ${TargetPath} -classpath "${SourceFile}" "${TargetClassName}"