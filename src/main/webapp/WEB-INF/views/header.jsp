<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/mystargram.css">
    <link rel="stylesheet" type="text/css" href="/css/write.css">
    <script src="https://code.jquery.com/jquery-3.7.1.slim.js" integrity="sha256-UgvvN8vBkgO0luPSUl2s8TIlOSYRoGFAX4jlCIm9Adc=" crossorigin="anonymous"></script>
    <script src="scripts/mystargram.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){

            $("#imgPrevBtn").change(function(){
              var form = $("form")[0];
              var formData = new FormData(form);
              $.ajax({
                  url: "<%=request.getContextPath() %>/fileup",
                  type: "POST",
                  enctype: "multipart/form-data",
                  data: formData,
                  timeout: 10000,
                  contentType: false,
                  processData: false,
                  success: function(response) {
                    $("#imgPrev").html("<img src='"+response.url+"' />");
                    $("#image").val(response.url);
                  },
                  error: function() {
                    alert("이미지 업로드에 실패했습니다.");
                  }
              })
           });

           $("#emailChk").click(function(){
              if (document.joinFrm.email.value == "") {
                  alert("이메일을 입력하세요");
                  return;
              }

               var form = $("#joinFrm")[0];
               var formData = new FormData(form);

               $.ajax({
                   url: "<%=request.getContextPath() %>/emailCheck",
                   type: "POST",
                   async: false,
                   data: formData,
                   timeout: 10000,
                   contentType: false,
                   processData: false,
                   success: function(response) {
                       if (response.result == 1) {
                           $("#emailMsg").text("사용가능한 아이디입니다.");
                           $("#reemail").val(response.email);
                       } else {
                           $("#emailMsg").text("사용중인 아이디입니다.");
                           $("#reemail").val("");
                       }
                   },
                   error: function() {
                       alert("이메일 중복체크중 오류가 발생했습니다.");
                   }
               })
           });
        });
    </script>
</head>
<body>

<div id="wrap">

    <c:if test="${not empty loginUser}">
        <div class="topmenu">
            <a href="main"><img src="/images/home.png" /></a>
            <a href="writeForm"><img src="/images/write.png" /></a>
            <a href="#" class="btn_searchbox"><img src="/images/search.png" /></a>
            <a href="mypage"><img src="${loginUser.profileimg}" /></a>
            <a href="logout"><img src="/images/logout.png" /></a>
        </div>
    </c:if>
