# 게시판 만들기

***

http://localhost:7777/

***

# 기술

- IDE : Intellij
- DBMS : MySQL
- WAS : Apache Tomcat
- Frontend : JSP (HTML, CSS, JS)
- Backend : JAVA (JPA)

***

# 기능
- 게시글 CRUD (게시판 Editer 사용가능, 이미지도 업로드 가능 BASE64 이용)
- 댓글 CRUD 
- 로그인 (KAKAO OAuth 로그인 가능), 회원가입
- 회원정보에서 PassWord 수정가능
- 게시판 Paging
- 게시글 작성 24시간 미만일 경우 new 마크
- 파일 업로드 (파일 선택 후 업로드시 "C: A_UPLOAD" 폴더에 저장)
> 구현중인 기능 : 게시판 검색, 파일 다운로드
***

# 카카오 OAuth
- https://developers.kakao.com/docs/latest/ko/kakaologin/common

***

# 게시판 화면

![스크린샷 2023-10-09 180058](https://github.com/KHYUN28/CRUDboard3/assets/121412134/e4b1c0e7-1e3a-4de1-ad66-49da68efafad)


***

# 게시판 Detail 화면

![스크린샷 2023-10-09 1441655](https://github.com/KHYUN28/CRUDboard3/assets/121412134/21890c77-181b-438a-aed7-c8b86d5bc497)

- 위 사진은 base64로 저장한 방식
***

# Board Mysql

![스크린샷 2023-10-09 175753](https://github.com/KHYUN28/CRUDboard3/assets/121412134/ac588cdf-0ff3-4609-a720-e5745cea951f)


***

# User Mysql

![스크린샷 2023-10-09 175735](https://github.com/KHYUN28/CRUDboard3/assets/121412134/18ba9a24-8a2f-4ea8-98cb-e3ee04cd5f12)

***

# reply Mysql

![스크린샷 2023-10-09 175814](https://github.com/KHYUN28/CRUDboard3/assets/121412134/23ec6527-dbeb-4602-b8cb-04029b404a32)

***

# Upload Mysql

![스크린샷 2023-10-09 175933](https://github.com/KHYUN28/CRUDboard3/assets/121412134/f161dfe4-b2e6-4031-9a9d-c544386c4c6e)

![스크린샷 2023-10-09 175911](https://github.com/KHYUN28/CRUDboard3/assets/121412134/4199c14a-f8a6-423a-8c5b-1d99f11747f4)

- 원본 파일명과 저장 파일명을 따로 저장
