# Kobalttown

익명 게시판.

## 레이아웃

- [`account/`](./account) : 계정 기능과 관련있는 모듈 모음.
- [`config/`](./config) : 외부 설정 파일 모음.
- [`configuration/`](./configuration) : 재사용 가능한 설정 모듈 모음.
- [`db/`](./db) : DB
- `gradle/` : Gradle wrapper
- [`macOS.`](./macOS) : macOS 개발환경용 파일.
- [`page/`](./page) : 웹 페이지 설정용 모듈 모음.
- [`runner/`](./runner) : 실행 파일 생성용 모듈 모음.

## 개발 환경 설정

1. `front`란 이름으로 [`kobalttown-front`](https://github.com/JustBurrow/kobalttown-front)에 접근할 수 있어야 한다.
    - `kobalttown-front`를 로컬에 클론한 후, 심볼릭 링크 생성. `ln -s ~/JustBurrow/kobalttown-front front`
    - 심볼릭 링크를 만들 수 없는 경우 프로젝트 루트에서 `git clone https://github.com/JustBurrow/kobalttown-front.git front` 실행.
