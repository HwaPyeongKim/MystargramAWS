package com.himedia.gram.controller;

import com.himedia.gram.dto.PostDto;
import com.himedia.gram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @Autowired
    PostService ps;

    @GetMapping("/main")
    public String main(){


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
}