<img width="1680" alt="EVERYMATCH" src="https://github.com/user-attachments/assets/44201988-96ee-4425-bca1-949d7644ae0e">
<img width="1680" alt="Screenshot 2024-11-26 at 11 51 36 AM" src="https://github.com/user-attachments/assets/1749e5e5-b573-4c58-82b5-e25a04a64ef4">

# EVERYMATCH 프로젝트 결과 보고서

---

## 프로젝트 팀 구성
- **조장**: 이준환
- **조원**: 김서현
<table>
  <tr>
    <td align="center"><a href="https://github.com/ljh0401"><img src="https://github.com/user-attachments/assets/1356a98e-80b6-4780-bda5-777a7e4d9cc4" width="130px;" alt=""/><br /><sub><b>이준환</b></sub></a><br /><sub><b>조장(풀스택)</b><br>ERD</br>요구사항 정의서<br>UI 제작</br>Chat gpt API<br>FullCalendar</br></sub></td>          
    <td align="center"><a href="https://github.com/seohye-ki"><img src="https://github.com/user-attachments/assets/00f751b1-36fe-4811-831f-dd2643a70652" width="106px;" alt=""/><br /><sub><b>김서현</b></sub></a><br /><sub><b>조원(풀스택)</b><br>와이어 프레임</br>로고 제작<br>UML 작성</br>JWT 토큰 인증<br>BCrypt 비밀번호 암호화</br></sub></td
  </tr>
</table><br/>


---

## 목차
1. 프로젝트 개요 및 요구사항  
2. UseCase 다이어그램  
3. 데이터베이스 테이블 구조 (ERD)  
4. 클래스 다이어그램  
5. 사용된 라이브러리 및 프레임워크  
6. 프레임워크 선택 이유  
7. 결론 및 기대효과
8. 후기

---

## 1. 프로젝트 개요 및 요구사항

### 프로젝트 개요
EVERYMATCH 프로젝트는 좋아하는 팀들의 경기 일정을 손쉽게 확인할 수 있는 플랫폼입니다. 좋아하는 팀들의 경기 일정 데이터베이스를 불러와 캘린더를 통해 간편히 확인할 수 있으며 사용자는 경기 1시간 전 이메일을 통한 알림으로 경기가 있음을 확인할 수 있습니다.

### 요구사항 정의
#### 사용자 기능
1. **회원가입**: 사용자 정보를 입력받아 계정을 생성합니다.  
2. **로그인/로그아웃**: 인증을 통해 시스템에 접근하거나 종료합니다.  
3. **회원정보 수정**: 비밀번호 재인증 후 자신의 정보를 수정합니다.
4. **비밀번호 찾기**: 비밀번호를 잊어버렸을 경우 이메일을 통해 재발급합니다.
5. **회원 탈퇴**: 더 사용하기 싫다면 탈퇴할 수 있습니다.

#### 메인 기능
1. **캘린더**: 사용자가 좋아하는 팀으로 설정한 팀들의 경기들이 많을수록 진한색으로 표시됩니다.
2. **당일 경기 확인**: 캘린더 날짜를 클릭하면 해당일의 경기들이 오른편에 보입니다.
3.  **좋아하는 팀 추가/삭제**: 좋아하는 팀을 추가/삭제할 수 있습니다.

#### 보안 기능
1. **JWT 기반 인증**: 토큰을 통한 안전한 세션 관리를 제공합니다.  
2. **데이터 암호화**: Bcrypt를 통해 사용자 비밀번호를 안전하게 저장합니다.  

#### Chat gpt API 활용 기능
1. **좋아하는 팀 추천**: API를 통해 인공지능에게 좋아하는 팀을 추천받을 수 있습니다.
2. **파트너와 대화**: Main에서 오른쪽 하단의 버튼을 누르면 파트너와 대화할 수 있습니다.  

(API Key 별도 관리하여 실제 작동에는 .env의 APIKEY가 필요합니다.) 

---

## 2. UseCase 다이어그램

![Usecase Diagram](https://github.com/user-attachments/assets/5d138834-273a-443e-a863-8a857e08853e)

---

## 3. 데이터베이스 테이블 구조 (ERD)

![ERD-ERD ver3](https://github.com/user-attachments/assets/60b6446b-d760-4b5e-b744-796159857d27)

---

## 4. 클래스 다이어그램

![classdiagram](https://github.com/user-attachments/assets/e9bf8f4d-5318-4f02-bcdc-7e8ec83a4af5)

---

## 5. 사용된 라이브러리 및 프레임워크

### 프론트엔드
1. **Vue.js**: 사용자 인터페이스 개발  
2. **Vue Router**: 클라이언트 라우팅  
3. **Pinia**: 중앙 상태 관리  
4. **Axios**: 백엔드 API와의 통신 
5. **FullCalendar**: 캘린더 라이브러리

### 백엔드
1. **Spring Boot**: 백엔드 애플리케이션 개발  
2. **JBCrypt**: 비밀번호 해싱
3. **MyBatis**: 데이터베이스와 객체 간 매핑  
4. **JWT**: 로그인 인증


---

## 6. 프레임워크 선택 이유

### 프론트엔드
1. **Vue.js**:  
   - 가벼운 SPA(Single Page Application) 구축 가능.  
   - 코드 재사용성 및 확장성 제공.

2. **Vue Router**:  
   - 페이지 리로드 없이 빠른 라우팅.  

3. **Pinia**:  
   - 복잡한 데이터 흐름 관리 및 동기화.  

4. **Axios**:  
   - REST API와의 직관적인 통신 제공.  

### 백엔드
1. **Spring Boot**:  
   - 빠르고 간편한 설정 및 강력한 확장성.  

2. **JBCrypt**:  
   - 비밀번호 암호화로 보안 향상.

3. **MyBatis**:  
   - MySQL와의 데이터베이스 연동에 용이.  

4. **JWT**:  
   - 로그인 지속을 위한 인증.  

---

## 7. 결론 및 기대효과

### 결론
EVERYMATCH 프로젝트는 좋아하는 팀들의 경기 일정을 손쉽게 확인할 수 있는 플랫폼입니다. 좋아하는 팀들의 경기 일정을 캘린더를 통해 간편히 확인할 수 있으며, 사용자가 좋아하는 팀의 경기를 1시간 전 이메일을 통한 알림을 받을 수 있습니다.

### 기대효과
1. **사용자 경험 향상**  
   - 캘린더를 통해 좋아하는 팀의 경기 일정을 손쉽게 확인 가능.

2. **개발 효율성 증대**  
   - 최신 기술과 프레임워크를 활용하여 빠르고 안정적인 시스템 구축.  

3. **시장 경쟁력 강화**  
   - 경기 알림, 맞춤형 캘린더, 팀별 관리 기능 등을 제공함으로써 경쟁 플랫폼과 차별화.  

---

## 8. 후기

### 이준환
   - 재밌는 프로젝트
      
### 김서현
   - 즐거운 프로젝트
