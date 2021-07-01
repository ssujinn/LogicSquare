# Embedded System Software Project <Logic Square>
## Target Board Environment
- Freescale i.MX 6 Quadcore (Cortex-A9 based, 1.2GHz)
- Linux 3.10.17
- Android 4.3 Jellybean
  
## Introduction
Square Logic(네모 로직)은 일본에서 개발된 퍼즐로, 영어로는 Nonograms, Picross, Griddlers 등으로 불린다. 규칙은 X×Y 크기(주로 5x5 단위)의 직사각형에 각각 적혀있는 숫자를 보고 숨어있는 숫자를 예측해서 지우고 그려 나가면서 그림을 만들어가는 게임이다. 네모 로직의 규칙은 아래와 같다.
-	쓰인 숫자만큼의 연속된 칸을 칠해야 한다.
-	숫자와 숫자 사이에는 적어도 한칸을 비워야 한다.
-	숫자의 순서와 칠해진 칸의 순서는 일치해야 한다.

### Functions
- 유저 정보 저장
- 단계별 게임 (1 to 5)
- 터치 스크린을 통한 게임 진행
- 스코어 계산 

### Flow
-	먼저 게임의 시작 화면에서 유저 정보를 입력 받는다.
-	저장된 정보에 유저 정보가 있으면 해당 레벨의 게임을 로드한다.
-	저장된 정보가 없으면 새로 정보를 추가하고 레벨 1부터 시작한다.
-	게임을 클리어하면 스코어를 출력한다.
- 레벨 클리어 후 메인 또는 다음 레벨로 이동할 수 있다.

## Developments
### Android Programming
#### MainActivity
-	사용자 정보 입력 후 user-info.txt 읽기
-	파일 정보 유저 클레스 어레이에 저장
-	입력된 정보에 따라 게임 로드
-	Intent -> GameActivity
-	Intent data: user name, user level

#### GameActivity
-	Intent로 받아온 level에 따라 game 로드 하고 레벨과 유저 이름을 화면에 출력
-	5x5의 25개 버튼으로 구성, 각 버튼의 onClickListener는 클릭 시 버튼의 색깔을 Black <-> White로 전환한다.
-	버튼 클릭 마다 현재 상태를 array 형태로 클래스에 저장한다.
-	Answer array와 현재 상태가 동일하면 게임 클리어로 간주하고 EndActivity로 전환한다.
-	현재 레벨을 FND에 띄워주고 현재 퍼즐 상태를 Dot Matrix에 출력한다.
-	Intent: EndActivity
-	Intent data: {user name, user level}
  
#### EndActivity
-	유저 레벨을 파일에 업데이트
-	시스템콜로부터 계산된 점수를 Score에 출력
-	NextLevel -> Intent(GameActivity), Intent data: {user name, user level}
-	Main -> Intent(EndActivity), Intent data: X
  
### JNI
#### Dot Matrix 출력
- 자바함수<br>
native void printDot(int[] square_status)
-	C 함수<br>
JNIEXPORT void JNICALL Java_com_example_logicsquare_GameActivity_printDot(JNIEnv *env, jobject this, jintArray square_status)
-	Parameter<br>
jintArray -> 퍼즐의 현재 상태
-	역할<br>
디바이스 드라이버 호출하여 현재 퍼즐 상태를 Dot matrix에 출력

#### FND 출력
-	자바 함수<br>
native void printFnd(int lv)
-	C 함수<br>
JNIEXPORT void JNICALL Java_com_example_logicsquare_GameActivity_printFnd(JNIEnv *env, jobject this, jint lv)
-	Parameter<br>
Jint -> 사용자의 레벨을 FND에 출력
-	역할<br>
사용자의 레벨을 받아서 디바이스 드라이버를 통해 FND에 레벨 출력
  
#### System Call 호출
-	자바 함수<br>
native int nowScore(int lv)
-	C 함수<br>
JNIEXPORT void JNICALL Java_com_example_logicsquare_EndActivity_nowScore (JNIEnv *env, jobject this, jint lv)
-	Parameter<br>
Jint -> 사용자의 레벨을 통해 score 계산
- 역할<br>
새로 만든 시스템콜(syscall number: 379)을 호출하여 레벨을 전달하고 레벨에 따른 score를 계산하여 반환

### System call
-	System call number: 379
-	System call name: logiccall
-	역할: 사용자로부터 레벨 데이터를 전달받아 스코어를 계산 후 다시 사용자에게 return 한다.

### Device Driver (Module Programming)
-	디바이스 이름: “/dev/fpga_device”
-	Major number: 270
-	FND와 dot matrix 2가지 디바이스를 제어해야 하므로 Ioctl을 사용하였으며 ioctl의 command는 dot matrix에 출력하는 RUN_DOT(0)과 FND를 출력하는 RUN_FND(1) 2가지이다.
-	디바이스는 모듈 init 시 ioremap을 통해 매핑하여 사용하고, 모듈 exit할 때 iounmap을 통해 언매핑한다.
