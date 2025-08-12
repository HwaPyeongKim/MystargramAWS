<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<div class="mypage">
    <div class="userinfo">
        <div class="img">
            <img src="${user.profileimg}" />
        </div>
        <div class="profile">
            <div class="field">
                <label>Email</label>
                <div>${user.email}</div>
            </div>
            <div class="field">
                <label>Nickname</label>
                <div>${user.nickname}</div>
            </div>
            <div class="field">
                <label>Followers</label>
                <div>${followers.size()}</div>
            </div>
            <div class="field">
                <label>Followings</label>
                <div>${followings.size()}</div>
            </div>
            <div class="field">
                <div>${user.profilemsg}</div>
            </div>
        </div>
    </div>

    <div class="btns">
        <a href="editProfileForm">Edit Profile</a>
        <a href="#">Post Write</a>
    </div>

    <div class="userpost">
        <c:forEach items="${list}" var="item">
            <a href="postView?id=${item.id}">
                <img src="${item.image}" />
            </a>
        </c:forEach>
    </div>

</div>

<%@ include file="../footer.jsp" %>