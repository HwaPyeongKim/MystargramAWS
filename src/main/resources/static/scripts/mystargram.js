function like(postid, memberid) {
    location.href = "like?postid="+postid+"&memberid="+memberid;
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