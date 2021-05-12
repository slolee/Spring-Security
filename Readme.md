#### Account 객체 요구사항
* 기본적인 유저 정보
    * 아이디, 비밀번호, 이름, 프로필 사진 링크(profileHref)
    * 서비스상에서 유저에게 부여하고 싶은 권한
    * 소셜 로그인인 사용자의 경우, 소셜 서비스가 부여한 ID 코드 (로그인 아이디와 별도)
    

* 유저 정보를 인증과정에서 처리하는 방식
  * 유저 모델을 그대로 사용
  * 유저디테일즈 구현체를 사용 (Spring Security 에서 사용하는 방식)