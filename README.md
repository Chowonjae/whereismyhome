# whereismyhome_20221012_final

- 회원정보 등록, 수정, 조회, 탈퇴 및 공지사항 등록, 수정, 조회, 삭제는 whereishome_final 폴더에서의 기능과 동일하다.

- 다운받아서 실행하고자 할 경우 resources의 파일들을 활용한다.(sql 파일은 ssafyweb 먼저 실행해야 한다.)

- **알고리즘 3개를 추가했으며 기획서는 resources/paper 안에 있다.**

## 1012_Update

### 알고리즘 #1 (초성 검색)

<img src="images/cho.PNG" alt="img14" width="700px"></img>

### 알고리즘 #2 (선별진료소 목록 최적화)

<img src="images/covied.png" alt="img14" width="700px"></img>

### 알고리즘 #3 (비밀번호 해싱)

<img src="images/hash.PNG" alt="img14" width="700px"></img>

-------------------------------------------------------

## 초기상태

- 공지사항 비어있음.
- (ssafy,1234) 또는 (admin,1234)로 로그인이 가능하다.
- 관심지역 비어있음.

## 메인페이지

<img src="/images/1.png" alt="img1" width="700px"></img>

### 메인페이지에서 검색

<img src="/images/2.png" alt="img2" width="700px"></img>

- 선택 후 검색버튼을 클릭하면
  아파트 검색 페이지로 이동
- 상단의 HomeSearch 버튼을 클릭해도 아파트 검색 페이지로 이동한다.

## 아파트 검색 페이지

### 초기화면

<img src="images/8.png" alt="img8" width="700px"></img>

### 매매정보 가져오기 버튼 클릭 시

- 동까지 입력이 되었을 때
- 매매월까지 다 입력이 되었을 때

이 두가지의 경우로 매매정보를 가져올 수 있다.

<img src="images/3.png" alt="img3" width="700px"></img>

### 아파트 리스트에서 선택시

- 그 아파트 중심으로 확대되며 그 아파트의 이름과 주소, 건축년도가 나타난다.

  <img src="images/4.png" alt="img4" width="700px"></img>

### 관심지역 설정

- 시도, 구군, 동까지 필수입력 후 관심지역등록 버튼을 클릭시 관심지역으로 등록이되며 중간부분에 관심지역 버튼이 생겨나며 그 지역으로 검색이 이루어진다.

  <img src="images/5.png" alt="img5" width="700px"></img>
  <img src="images/6.png" alt="img6" width="700px"></img>

### 관심지역 삭제

- 관심지역 버튼의 X를 누르면 삭제가 이루어지며 초기화면으로 돌아간다.

  <img src="images/7.png" alt="img7" width="700px"></img>
  <img src="images/8.png" alt="img8" width="700px"></img>

### 관심지역 클릭

- 관심지역 버튼 클릭 시 그 주소의 모든 매매내역이 검색된다.

  <img src="images/3.png" alt="img3" width="700px"></img>

## 코로나 선별진료소 검색 페이지

- 상단의 Corona 버튼 클릭 선별진료소 검색 페이지로 이동한다.

### 초기화면

<img src="images/11.png" alt="img11" width="700px"></img>

### 선별진료소 정보 가져오기 버튼 클릭 시

- 동까지 입력이 되었을 때 선별진료소 정보를 가져올 수 있다.
- 리스트에는 선별진료소 이름, 주소, 요일별 운영시간, 전화번호가 표시된다.

<img src="images/12.png" alt="img12" width="700px"></img>

<img src="images/13.png" alt="img13" width="700px"></img>

## 안심진료병원 페이지

- 상단의 Hospital 버튼 클릭 선별진료소 검색 페이지로 이동한다.

### 초기화면

<img src="images/14.png" alt="img14" width="700px"></img>

### 안심진료병원 정보 가져오기 버튼 클릭 시

- 동까지 입력이 되었을 때 진료병원 정보를 가져올 수 있다.
- 리스트에는 병원의 이름, 주소, 진료 유형, 전화번호가 표시된다.

<img src="images/15.png" alt="img15" width="700px"></img>

<img src="images/16.png" alt="img16" width="700px"></img>


