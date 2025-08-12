function like(postid, memberid, returnUrl) {
    var url = "like?postid="+postid+"&memberid="+memberid;
    if (returnUrl != undefined) {
        url += "&returnUrl="+returnUrl;
    }
    location.href = url;
}

function deleteReply(postid,replyid) {
    if (confirm("댓글을 삭제하시겠습니까?")) {
        location.href = "deleteReply?postid="+postid+"&replyid="+replyid;
    }
}


$(function (){
    $(".btn_searchbox").on("click",function(e){
        e.preventDefault();
        $(this).toggleClass("on");
        if ($(this).hasClass("on")) {
            $(this).find("img").attr("src","/images/search_on.png")
        } else {
            $(this).find("img").attr("src","/images/search.png")
        }
        $(".searchbox").slideToggle();
    });
})