<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<div class="post">
    <div class="writer">
        <div>${item.nickname}</div>
    </div>
    <img src="${item.image}" />
    <div class="btn_post">
        <button class="btn_like">
            <img src="/images/<c:if test="${item.like == 'Y'}">de</c:if>like.png" onclick="like('${item.id}','${loginUser.id}','postView')" />
            <span>${item.likecount}</span>
        </button>
        <button class="btn_reply">
            <img src="/images/reply.png" />
            <span>${item.replycount}</span>
        </button>
    </div>
    <pre>${item.content}</pre>
</div>

<div class="replies">
    <form method="post" action="writeReply">
        <input type="hidden" name="postid" value="${item.id}" />
        <input type="hidden" name="memberid" value="${loginUser.id}" />
        <div class="write">
            <input type="text" name="reply" value="${dto.reply}" />
            <input type="submit" value="추가" />
        </div>
        <p class="warn">${msg}</p>
    </form>

    <c:forEach items="${replyList}" var="reply">
        <div class="reply">
            <a href="mypage?email=${reply.email}">${reply.nickname}</a>
            <div>
                <p>${reply.reply}</p>
                <c:if test="${reply.memberid == loginUser.id}">
                    <button onclick="deleteReply('${item.id}','${reply.id}')">삭제</button>
                </c:if>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="../footer.jsp" %>