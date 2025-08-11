<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<div class="mypage">
    <div class="userinfo">
        <div class="img">
            <img src="${loginUser.profileimg}" />
        </div>
        <div class="profile">
            <div class="field">
                <label>Email</label>
                <div>${loginUser.email}</div>
            </div>
            <div class="field">
                <label>Nickname</label>
                <div>${loginUser.nickname}</div>
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
                <div>${loginUser.profilemsg}</div>
            </div>
        </div>
    </div>

    <div class="btns">
        <a href="editProfileForm">Edit Profile</a>
        <a href="#">Post Write</a>
    </div>

    <div class="userpost">
        <c:forEach items="${list}" var="item">
            <div>
                <img src="${item.image}" />
            </div>
        </c:forEach>
    </div>

</div>

<%@ include file="../footer.jsp" %>