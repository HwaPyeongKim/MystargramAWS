package com.himedia.gram.controller;

import com.himedia.gram.dto.PostDto;
import com.himedia.gram.dto.ReplyDto;
import com.himedia.gram.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class PostController {

    @Autowired
    PostService ps;

    @GetMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        HashMap<String,Object> result = ps.select(request);
        model.addAttribute("list", result.get("list"));
        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("key", result.get("key"));
        return "main";
    }

    @GetMapping("/writeForm")
    public String writeForm(){
        return "post/writeForm";
    }

    @PostMapping("/writePost")
    public String writePost(@ModelAttribute("pto") PostDto postdto, BindingResult result, Model model){

        if (postdto.getContent().equals("")) {
            model.addAttribute("msg", "내용을 입력해주세요.");
        } else {
            ps.insert(postdto);
        }
        return "redirect:/main";
    }

    @GetMapping("/like")
    public String like(@RequestParam("postid") int postid, @RequestParam("memberid") int memberid, @RequestParam(value = "returnUrl", required = false, defaultValue = "") String returnUrl){
        ps.like(postid, memberid);
        String url = "redirect:/main";
        if (!returnUrl.equals("")) {
            url = "redirect:/"+returnUrl+"?id="+postid;
        }
        return url;
    }

    @GetMapping("/follow")
    public String follow(HttpServletRequest request, @RequestParam("end") int end){
        ps.follow(request, end);
        return "redirect:/main";
    }

    @GetMapping("/search")
    public String search(HttpServletRequest request, Model model){
        HashMap<String,Object> result = ps.search(request);
        if (result.get("result").equals("1")) {
            request.setAttribute("list", result.get("list"));
            request.setAttribute("key", result.get("key"));
            return "main2";
        } else {
            return "redirect:/main";
        }
    }

    @GetMapping("/postView")
    public String postView(HttpServletRequest request, @RequestParam("id") int id, Model model){
        HashMap<String, Object> result = ps.getPost(request, id);
        model.addAttribute("item", result.get("post"));
        model.addAttribute("replyList", result.get("replyList"));
        return "post/postView";
    }

    @PostMapping("/writeReply")
    public String writeReply(@ModelAttribute("dto") ReplyDto replydto, BindingResult result, Model model){
        if (replydto.getReply().equals("")) {
            model.addAttribute("msg", "내용을 입력해주세요");
        } else {
            ps.writeReply(replydto);
        }

        return "redirect:/postView?id="+replydto.getPostid();
    }

    @GetMapping("/deleteReply")
    public String deleteReply(HttpServletRequest request, @RequestParam("postid") int postid, @RequestParam("replyid") int replyid){
        ps.deleteReply(replyid);
        return "redirect:/postView?id="+postid;
    }
}