# 🎾 Knowlly
각자 잘아는 분야의 지식들을 
서로 공유 할 수 있는 지식교환 플랫폼

![image](https://user-images.githubusercontent.com/55652627/234002289-c3ed62db-62ba-4e4f-ab13-cbd19aae32b5.png)

### 기획 의도

- 새로운 분야의 지식을 배우고 싶은데 금전적 여유가 없다
→ 내가 아는 지식을 공유하고 새로운 지식을 배우자


### **Tech Stack**

- Backend
    - Java 11, Spring Boot, Spring Data JPA, MySQL
    - Spring Security, JWT, OAuth 2.0
    
- Infra
    - Docker, K8S

ERD : https://www.erdcloud.com/d/upxjamqGs5qPfYyXC

## git convention
- 형식 : `<업무명>: <작업 내용> (#이슈번호)`

|업무명|내용|
| :-----------------------------------: | :---------------------------------------: |
| CHORE | 빌드 업무 수정, 패키지 매니저 수정 |
| **ADD** |   코드나 테스트, 예제, 문서 등의 추가   |
| **FIX** | 올바르지 않은 동작을 고친 경우 |
| **REMOVE** |   코드의 삭제가 있을 때   |
| **UPDATE** |   문서나 리소스, 라이브러리등의 수정, 추가, 보완   |
| **FEAT** |  새로운 기능 추가   |
| **CORRECT** | 주로 문법의 오류나 타입의 변경, 이름 변경 등   |
| REFACTOR |   코드의 전면적인 수정   |
| DOCS |  문서의 개정   |
| RENAME | 파일의 이름 변경 |
| TEST | TEST 코드 관련   |

### 브랜치 전략

- main : 메인 작업 및 운영 환경의 형상 브랜치
- feature : 작업 브랜치
  - 네이밍
    - feature/ISSUE-{git issue 번호}
    - ex) feature/ISSUE-23
