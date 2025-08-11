package com.himedia.gram.controller;

import com.himedia.gram.dto.PostDto;
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
    public String like(@RequestParam("postid") int postid, @RequestParam("memberid") int memberid){
        ps.like(postid, memberid);
        return "redirect:/main";
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
}