#!/bin/bash

WAS_PORT=8080
JAR_PATH="./build/libs"
PROFILE=$1

if [ -z "$PROFILE" ]; then
  echo "1번째 매개변수로 프로파일이 필요합니다."
  exit 1
fi

function pull() {
  echo "저장소 pull 시작"
  git pull origin main
  echo "저장소 pull 완료"
}

function build() {
  echo "서버 빌드 시작"
  ./gradlew bootJar
  echo "서버 빌드 완료"
}

function findPid() {
  lsof -t -i:"$WAS_PORT"
}

function killProcess() {
  PID="$1"
  if [ -n "$PID" ]; then
    kill -15 "$PID"
    echo "$WAS_PORT 포트로 열린 프로세스를 종료합니다."
  else
    echo "$WAS_PORT 포트로 열린 프로세스가 없습니다."
  fi

}

function startServer() {
  PROFILE="$1"
  JAR_FILE=$(find "$JAR_PATH" -maxdepth 1 -name '*.jar' -print -quit)
  if [ -z "$JAR_FILE" ]; then
    echo "$JAR_PATH 경로에 JAR 파일이 없거나 2개 이상입니다."
    exit 1
  fi
  nohup java -jar -Duser.timezone=Asia/Seoul "$JAR_FILE" --spring.profiles.active="$PROFILE" 1> ./log/app.log 2>&1 &
}

pull
build
PID=$(findPid)
killProcess "$PID"
startServer "$PROFILE"
