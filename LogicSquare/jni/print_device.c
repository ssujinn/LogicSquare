#include <jni.h>
#include "android/log.h"
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <asm/ioctl.h>
#include <sys/syscall.h>

#define LOG_TAG "MyTag"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAB< __VA_ARGS__)

#define RUN_DOT _IOW(270, 0, char*)
#define RUN_FND _IOW(270, 1, char*)

JNIEXPORT void JNICALL Java_com_example_logicsquare_GameActivity_printDot
  (JNIEnv *env, jobject this, jintArray square_status) {
	unsigned char map[10];
	int x, y;
	int i;
	memset(map, 0, 10);

	int fd = open("/dev/fpga_device", O_WRONLY);

	if (fd < 0){
		printf("File open error\n");
		exit(1);
	}

	jint *Arr = (*env)->GetIntArrayElements(env, square_status, NULL);
	jsize length = (*env)->GetArrayLength(env, square_status);

	for (i = 0; i < length; i++){
		x = i % 5;
		y = i / 5;
		if (Arr[i] == 1)
			map[y] ^= (0x40 >> x);
	}

	ioctl(fd, RUN_DOT, map);
	(*env)->ReleaseIntArrayElements(env, square_status, Arr, 0);

	close(fd);
	
}

JNIEXPORT void JNICALL Java_com_example_logicsquare_GameActivity_printFnd
  (JNIEnv *env, jobject this, jint lv){
	  unsigned char data[10];
	  memset(data, 0, 10);
	int fd = open("/dev/fpga_device", O_WRONLY);

	if (fd < 0){
		printf("File open error\n");
		exit(1);
	}

	data[3] = (unsigned char)lv;
	
	ioctl(fd, RUN_FND, data);

	close(fd);

 }

JNIEXPORT jint JNICALL Java_com_example_logicsquare_EndActivity_nowScore
  (JNIEnv *env, jobject this, jint lv){
	  jint score;

	  score = syscall(379, lv);

	  return score;
  }
