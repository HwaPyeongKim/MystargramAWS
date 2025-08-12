<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<c:if test="${empty loginUser}">
    <script type="text/javascript">
        alert("로그인을 해주세요.");
        location.href="/";
    </script>
</c:if>

<div class="posts">

    <c:forEach items="${list}" var="item">
        <div class="post">
            <div class="writer">
                <div>${item.id}</div>
                <div>${item.nickname}</div>
                <div>
                    <c:if test="${item.writer != loginUser.id}">
                        <input type="button" value="<c:if test="${item.follow == 'Y'}">un</c:if>follow" onclick="location.href='follow?end=${item.writer}'" />
                    </c:if>
                </div>
            </div>
            <img src="${item.image}" />
            <div class="btn_post">
                <button class="btn_like">
                    <img src="/images/<c:if test="${item.like == 'Y'}">de</c:if>like.png" onclick="like('${item.id}','${loginUser.id}')" />
                    <span>${item.likecount}</span>
                </button>
                <button class="btn_reply" onclick="location.href='postView?id=${item.id}'">
                    <img src="/images/reply.png" />
                    <span>${item.replycount}</span>
                </button>
            </div>
            <pre>${item.content}</pre>
        </div>
    </c:forEach>

</div>

<div class="paging">
    <div>
        <c:if test="${paging.prev}"><a href="main?page=${paging.beginPage-1}">Prev</a></c:if>

        <c:forEach begin="${paging.beginPage}" end="${paging.endPage}" var="index">
            <a href="main?page=${index}"<c:if test="${index == paging.page}"> class="on"</c:if>>${index}</a>
        </c:forEach>

        <c:if test="${paging.next}"><a href="main?page=${paging.endPage+1}">Next</a></c:if>
    </div>
</div>

<%@ include file="footer.jsp" %>