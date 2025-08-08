<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<c:if test="${empty loginUser}">
    <script type="text/javascript">
        alert("로그인을 해주세요.");
        location.href="/";
    </script>
</c:if>

<div class="post">

</div>

<%@ include file="footer.jsp" %>