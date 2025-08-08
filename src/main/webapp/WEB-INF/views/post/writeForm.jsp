<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<div class="postWrite">
  <form method="post" action="writePost" name="writeFrm" id="writeFrm" class="form" enctype="multipart/form-data">
    <input type="hidden" name="image" id="image" />
    <input type="hidden" name="writer" value="${loginUser.id}" />

    <h2>Post Write From</h2>

    <div class="field">
      <label for="content">content</label>
      <textarea name="content" id="content" cols="30" rows="10"></textarea>
    </div>

    <div class="field">
      <label for="imgPrevBtn">Image</label>
      <div>
        <div id="imgPrev">
          <c:if test="${not empty image}">
            <img src="${image}" />
          </c:if>
        </div>
        <input type="file" name="imgPrev" id="imgPrevBtn" value="이미지 선택" />
      </div>
    </div>

    <p class="warn">${msg}</p>

    <div class="btns">
      <input type="submit" value="저장" />
      <input type="button" value="메인으로" onclick="location.href='main'" />
    </div>

  </form>
</div>

<%@ include file="../footer.jsp" %>