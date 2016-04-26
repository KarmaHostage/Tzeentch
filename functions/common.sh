#/bin/bash
. ./functions/crashcheck.sh
function add_example_file {
  echo "adding reference file"
  adb push reference_format/input.$format $sd_location/tzeench/reference.$format
}

function start_example_file {
  echo "executing reference file"
  adb shell am start -a android.intent.action.VIEW -t $filetype -d file:/$encoded_sd_location//tzeench//reference.$format
}

# Check if gallery application is still running or if a crash occurred
function check_gallery {
    i=$(adb shell ps | grep com.android.gallery3d)
    id=$(echo $i | cut -d " " -f 2)

    if [ "$id" != "" ] ;then
	       if [ "$id" != "$id_gal" ] ; then
            echo $id_gal
            echo "Gallery restarted with pid $id"
            restarted=1
         fi;
    else
        echo "Gallery not running\n Restarting....\n"
        start_example_file
        adb shell input keyevent 4
        i=$(adb shell ps | grep com.android.gallery3d)
        id=$(echo $i | cut -d " " -f 2)
	      restarted=1
        echo "Gallery restarted with pid $id...\n"
        sleep 2
    fi;
    id_gal=$id
}

# Check the state of the process and wait until sleeping is reached
function check_state {
    for i in `seq 0 10`; do
        state=$(adb shell cat /proc/$id_gal/status | grep State)
        get_state=$(echo $state | cut -d " " -f 2)
        if [ "$get_state" != "S" ]; then
            echo "Not finished yet... "
            sleep $1
        fi;
    done;
}

function wait_for_crash {
    for i in `seq 1 10`;
    do
    	p=$(adb shell ps | grep gal)
    	echo $p
    	if [ "$p" == "" ]; then
    	    print $p
    	    return
    	fi;
    	sleep 1
    done;
    i=$(adb shell ps | grep com.android.gallery3d)
    id=$(echo $i | cut -d " " -f 2)
    adb shell su -c kill -9 $id
}

function initialize {
  mkdir -p "./fuzzed/$format"
  mkdir -p "./crashes/$format"
  id_gal=""
  add_example_file
  start_example_file
  check_gallery
  restarted=0
}

function fuzz {
  while [ 1 ] ;
  do
      radamsa learning/$format/* > ./fuzzed/$format/fuzzed.$format
      adb push ./fuzzed/$format/fuzzed.$format $sd_location/tzeench/fuzzed.$format
      adb shell logcat -c
      adb shell am start -a android.intent.action.VIEW -t $filetype -d file:/$encoded_sd_location//tzeench//fuzzed.$format
      sleep 0.2
      check_state 1
      check_gallery
      sigFound=$(errorFound "SIG")
      if [ "$sigFound" -eq 1 ] || [ "$restarted" -eq 1 ]; then
      	 echo "--------> Crash detected ($format)!"
  	     cp ./fuzzed/$format/fuzzed.$format ./crashes/$format/$c.$format
  	     adb shell "logcat -d > /sdcard/crash-$format.log"
  	     adb pull /sdcard/crash-$format.log ./crashes/$format/$c.log
  	    c=$(($c+1))
  	    restarted=0
      fi;
    adb shell input keyevent 4
    sleep 0.2
  done
}

format="undefined"
filetype="undefined"
c=0
