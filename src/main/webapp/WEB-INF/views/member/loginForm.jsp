<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<div class="form">
    <form method="post" action="login" name="loginFrm">
        <div class="field">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" value="${dto.email}" />
        </div>
        <div class="field">
            <label for="pwd">Password</label>
            <input type="password" name="pwd" id="pwd" />
        </div>
        <p class="warn">${msg}</p>
        <div class="btns">
            <input type="submit" value="Login" />
            <input type="button" value="Join" onclick="location.href='joinForm'" />
        </div>
        <div class="snslogin">
            <input type="button" value="Kakao" class="kakao" onclick="location.href='kakaostart'" />
            <input type="button" value="Naver" class="naver" onclick="" />
            <input type="button" value="Google" class="google" onclick="" />
            <input type="button" value="Facebook" class="facebook" onclick="" />
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>