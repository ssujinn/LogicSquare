# Embedded System Software Project <Logic Square>
## Introduction
Square Logic(네모 로직)은 일본에서 개발된 퍼즐로, 영어로는 Nonograms, Picross, Griddlers 등으로 불린다. 규칙은 X×Y 크기(주로 5x5 단위)의 직사각형에 각각 적혀있는 숫자를 보고 숨어있는 숫자를 예측해서 지우고 그려 나가면서 그림을 만들어가는 게임이다. 네모 로직의 규칙은 아래와 같다.
-	쓰인 숫자만큼의 연속된 칸을 칠해야 한다.
-	숫자와 숫자 사이에는 적어도 한칸을 비워야 한다.
-	숫자의 순서와 칠해진 칸의 순서는 일치해야 한다.

## Functions
- 유저 정보 저장
- 단계별 게임 (1 to 5)
- 터치 스크린을 통한 게임 진행
- 스코어 계산 

## Flow
-	먼저 게임의 시작 화면에서 유저 정보를 입력 받는다.
-	저장된 정보에 유저 정보가 있으면 해당 레벨의 게임을 로드한다.
-	저장된 정보가 없으면 새로 정보를 추가하고 레벨 1부터 시작한다.
-	게임을 클리어하면 스코어를 출력한다.
- 레벨 클리어 후 메인 또는 다음 레벨로 이동할 수 있다.

## Developments
### Android Programming & JNI
#### MainActivity
-	사용자 정보 입력 후 user-info.txt 읽기
-	파일 정보 유저 클레스 어레이에 저장
-	입력된 정보에 따라 게임 로드
-	Intent -> GameActivity
-	Intent data: user name, user level

### System call
### Device Driver (Module Programming)
