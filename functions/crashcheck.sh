#!/bin/sh

function errorFound() {
  foundSigError=$(adb shell logcat -d | grep "$1")
  if [ "$foundSigError" != "" ]; then
    echo 1
  else
    echo 0
  fi
}
