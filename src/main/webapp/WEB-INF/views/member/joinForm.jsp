<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<div class="form">
  <form method="post" action="join" name="joinFrm" id="joinFrm" class="imageForm" enctype="multipart/form-data">
    <input type="hidden" name="profileimg" id="image" value="${dto.profileimg}" />
    <input type="hidden" name="reemail" id="reemail" value="${reemail}" required />
    <input type="hidden" name="provider" value="none" />

    <h2>Member Join</h2>
    <div class="field">
      <label for="email">Email</label>
      <div class="inputEmail">
        <input type="email" name="email" id="email" value="${dto.email}" required />
        <input type="button" value="중복체크" id="emailChk" />
        <div id="emailMsg"></div>
      </div>
    </div>
    <div class="field">
      <label for="pwd">Password</label>
      <input type="password" name="pwd" id="pwd" required />
    </div>
    <div class="field">
      <label for="pwdChk">Retype Password</label>
      <input type="password" name="pwdChk" id="pwdChk" required />
    </div>
    <div class="field">
      <label for="nickname">Nickname</label>
      <input type="text" name="nickname" id="nickname" value="${dto.nickname}" required />
    </div>
    <div class="field">
      <label for="phone">Phone</label>
      <input type="text" name="phone" id="phone" value="${dto.phone}" required />
    </div>
    <div class="field">
      <label for="profilemsg">Intro</label>
      <input type="text" name="profilemsg" id="profilemsg" value="${dto.profilemsg}" />
    </div>
    <div class="field">
      <label for="imgPrevBtn">Image</label>
      <div>
        <div id="imgPrev">
          <c:if test="${not empty dto.profileimg}">
            <img src="${dto.profileimg}" />
          </c:if>
        </div>
        <input type="file" name="imgPrev" id="imgPrevBtn" value="이미지 선택" />
      </div>
    </div>
    <p class="warn">${msg}</p>
    <div class="btns">
      <input type="submit" value="Join" />
      <input type="button" value="Back" onclick="history.back()" />
    </div>
  </form>
</div>

<!--
<div class="form" id="selectImg">
  <form method="post" enctype="multipart/form-data" id="selectImg">
    <div class="field">
      <label for="profileimg">Image</label>
      <input type="file" name="profileimg" id="profileimg" value="이미지 선택" />
    </div>
  </form>
</div>
-->

<%@ include file="../footer.jsp" %>