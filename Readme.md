# 프로젝트 소개
Spring Security는 Spring 기반의 애플리케이션에서 보안(인증과 권한, 인가)를 처리해주는 Spring 하위 프레임워크이다. 

Spring Security에 대해서 이해하고 각 구성요소를 기본적인 예시를 통해 사용해보고자 진행한 프로젝트이다. Spring Security 프레임워크를 사용해 기본적인 JWT 토큰 인증 방식을 사용한 로그인 시스템을 구축한 프로젝트이다.
<br></br>

![1](https://user-images.githubusercontent.com/38906956/120091475-71707900-c146-11eb-9e44-e3f0ced5f2f3.png)

위 이미지를 참고해 Spring Security를 사용했을 때 인증과정이 어떻게 진행되는지 살펴보자.
1. Filter의 attemptAuthentication에 HTTP Request가 온다.
2. 전달받은 HTTP Request에서 인증에 사용될 데이터를 DTO 객체에 담는다.
3. DTO 객체를 사용해 인증요청 객체를 생성한다.
4. 인증요청 객체를 Provider Manager에 전달한다.
5. Provider Manager는 전달받은 인증요청 객체에 맞는 AuthenticationProvider를 선택한다.
6. 선택된 Provider에 의해서 인증과정을 진행한 후, 예외 혹은 인증후 객체를 반환한다.
7. Provider에 의해서 반환된 예외 혹은 인증후 객체는 Filter의 successfulAuthentication, unsuccessfulAuthentication 메서드에 전달된다.
8. 사전에 생성자를 통해 전달받은 successHandler, failureHandler를 호출해 클라이언트에게 HTTP Response를 보낸다.
<br></br>

# 인증과정
1. 사용자는 ID/Password를 통해 로그인한다.
2. 서버는 인증과정을 통해 올바른 ID/Password인지 확인한다.
3. 인증에 성공한다면 해당 사용자가 가진 권한을 포함시켜 JWT 토큰을 Response Body에 포함해 사용자에게 전달한다.
4. 이렇게 전달받은 토큰은 Http Only 헤더에 보관하며 서버에 요청시 포함시켜 요청한다.
5. 이후 요청시 서버는 인증과정을 통해 Request 패킷에 올바른 JWT 토큰이 포함되어있는지 확인한다.
6. 인증에 성공한다면 해당 요청엥 대한 응답을 보낸다.
<br></br>

# 구성요소
## Filter Chain
<b>HTTP Request가 서버로 왔을 때 WebSecurityConfig를 상속받은 SecurityConfig에서 추가되어있는 Filter가 있다면 해당 Filter에 의해서 인증과정</b>을 거치게 된다.

여러 Filter가 Chain모양으로 순차적으로 묶여있기 때문에 Filter Chain이라고 부른다. 어렵게 생각할 것 없이 여러가지 인증을 동시에 적용할 수 있고 이러한 <b>하나의 단위를 Filter</b>라고 생각하면 될듯하다. 조금 더 그럴싸하게 말하면 `인증의 의미(Flow)단위별로 Filter를 구현`하면 된다.

Filter Chain에 추가할 수 있는 Filter에는 기본적으로 Spring Security에서 제공하는 다양한 Filter들(위 이미지 참고)이 있고, 사용자가 커스터마이징한 Filter를 생성해 사용할 수도 있다.

![2](https://user-images.githubusercontent.com/38906956/120091780-328ff280-c149-11eb-84c1-bb8db8acccc2.png)

3가지 메서드를 Override하면 되는데 `attemptAuthentication` 메서드는 HTTP Request가 처음으로 들어오는 메서드이고, `successfulAuthentication/unsuccessfulAuthentication` 메서드는 Provider에 의해서 인증과정을 거치고 인증의 성공/실패 여부에 따라서 호출되는 메서드이다.
<br><br>

## Authentication Object
<b>인증객체란 Authentication 인터페이스를 구현한 모든 클래스의 객체</b>를 말한다.

일반적으로 Authentication 인터페이스를 직접 구현하는 것이 아니라 Spring Security에서 이미 구현해놓은 클래스(`UsernamePasswordAuthenticationToken`)를 상속받아 사용하는 것 같다. 왜냐하면, Authentication 인터페이스에는 구현해야할 6개의 메서드가 있는데 이를 다 직접 구현하는건 번거롭기 때문이다.

1. Collection<? extends GrantedAuthority> getAuthorities()  // 사용자의 권한 목록
2. Object getCredentials()  // 주로 Password
3. Object getDetails()
4. Object getPrincipal()  // 주로 ID
5. boolean isAuthenticated()  // 인증여부
6. void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException
<br><br>

## Provider Manager
Provider Manager는 <b>Spring Security에서 제공하는 `AuthenticationManager` 인터페이스를 구현한 클래스</b>이다. 그냥 이거 쓰면된다. 따로 구현하기엔 개발 코스트가 너무 높다고한다.

이 클래스가 하는 역할은 <b>AuthenticationProvider들을 Collection에 모아놨다가 인증요청 객체가 넘어오면 그에 맞는 Provider를 선택해 인증과정을 거치고 인증된 객체를 반환</b>하도록 도와주는 것이다. `Provider에 접근하기 위한 유일한 객체`이다.

(실제로 코드를 뜯어보면 반복문을 통해 Provider들을 모아놓은 Collection을 순환하며 일치하는 것을 찾는다)
<br><br>

## Authentication Provider
<b>Authentication Provider는 실제인증이 일어나는 곳</b>이다. 다르게 생각하면 다른 곳에 인증코드가 들어가지 않도록 주의해야 한다. <b>Provider는 인증요청 객체를 받아 인증과정을 거친 후 예외 혹은 인증후 객체를 반환</b>한다.

![3](https://user-images.githubusercontent.com/38906956/120091975-aed70580-c14a-11eb-82a2-31eba74032a8.png)

1. 해당 클래스는 <b>Component 어노테이션을 추가</b>해야한다!
왜냐하면 SecurityConfig에서 AuthenticationManagerBuilder 객체에 해당 Provider를 추가해줘야 하기 때문이다.
(의존성 주입을 받아 처리해야 하기때문에 컴포넌트로 만들어주는 것이다.)
2. <b>실제 인증과정이 구현될 메서드는 `authenticate()`</b> 이다.
해당 메서드의 인자로 인증요청 객체가 넘어오고, 에러 혹은 인증후 객체를 뿌려줘야 한다.
3. <b>해당 Provider를 사용할 Authentication 객체의 종류를 supports() 메서드에서 지정</b>하게 된다.
음.. 좀더 쉽게 말하면 어떤 Authentication 객체가 넘어왔을 때 이 Provider를 사용할지 매핑시켜주는 역할이다.
<br><br>

## Context
사실 Context 의 역할을 정확히 뭐라고 설명해야 좋을지 모르겠다. <b>인증된 객체 정보를 저장하기 위한 공간 + 이를 처리하기 위한 다양한 메서드..?</b> 정도로 생각하면 좋을 듯 하다.

내가 만든 예제에서는 Provider에서 인증과정을 거치고 <b>정상적으로 인증이 완료되었다면, 넘어온 데이터를 통해 AccountContext 객체를 생성</b>한다. 그리고 해당객체를 통해 PostAuthorizationToken(인증후 객체)를 생성해 반환해주게 된다.

![4](https://user-images.githubusercontent.com/38906956/120091990-c7472000-c14a-11eb-921a-cb065978fe04.png)
<br><br>

## Authentication Handler
인증 핸들러는 Filter에서 인증 성공/실패 여부에 따라서 호출되는 `successfulAuthentication, unsuccessfulAuthentication`에서 호출하는 함수를 그냥 내맘대로 표현한 것이다. 각각 핸들러는 `AuthenticationSuccessHandler, AuthenticationFailureHandler` 인터페이스를 구현해 만들면 된다.

![5](https://user-images.githubusercontent.com/38906956/120092021-070e0780-c14b-11eb-8ede-5d82d997947f.png)

![6](https://user-images.githubusercontent.com/38906956/120092035-15f4ba00-c14b-11eb-91d3-20688a1edd68.png)

<b>인증결과가 성공면 successHandler를 호출하고, 실패면 failureHandler를 호출하게 된다.</b>

<i>failureHandler에서는 사용자가 만든 에러를 뿌려주는게 가장 좋은 방법이지만, 예제코드에서는 그냥 log.error를 사용해 간단한 정보만 뿌려주는걸로 정리를 한다.</i>
<br><br>

## SecurityConfig
위에서 만든 구성요소들을 SecurityConfig 즉, WebSecurityConfigureAdapter를 상속받은 Configuration 클래스에서 추가해줘야 컨트롤러 호출전에 해당 구성요소들이 자리를 잡을 수 있다.

![7](https://user-images.githubusercontent.com/38906956/120092063-463c5880-c14b-11eb-99bb-832797cec7e1.png)
![8](https://user-images.githubusercontent.com/38906956/120092067-4d636680-c14b-11eb-888a-0a31f254463e.png)
<br><br>

