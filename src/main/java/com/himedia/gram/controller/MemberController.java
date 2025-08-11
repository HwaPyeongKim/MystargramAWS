package com.himedia.gram.controller;

import com.google.gson.Gson;
import com.himedia.gram.dto.FollowDto;
import com.himedia.gram.dto.KakaoProfile;
import com.himedia.gram.dto.MemberDto;
import com.himedia.gram.dto.OAuthToken;
import com.himedia.gram.service.MemberService;
import com.himedia.gram.service.S3UploadService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @Autowired
    S3UploadService sus;

    @GetMapping("/")
    public String index() {
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("dto") @Valid MemberDto memberdto, BindingResult result, HttpSession session, Model model) {
        String url = "member/loginForm";

        if (result.hasFieldErrors("email")) {
            model.addAttribute("msg", "아이디를 입력해주세요.");
        } else if (result.hasFieldErrors("pwd")) {
            model.addAttribute("msg", "비밀번호를 입력해주세요.");
        } else {
            MemberDto mdto = ms.getMember(memberdto.getEmail());
            if (mdto == null) {
                model.addAttribute("msg", "이메일과 패스워드를 확인해주세요.");
            } else if (!mdto.getPwd().equals(memberdto.getPwd())) {
                model.addAttribute("msg", "이메일과 패스워드를 확인해주세요.");
            } else {
                session.setAttribute("loginUser", mdto);
                url = "redirect:/main";
            }
        }
        return url;
    }

    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String exeCode = "<script type='text/javascript'>"
            + "location.href='https://kauth.kakao.com/oauth/authorize?"
            + "client_id="+client_id
            + "&redirect_uri="+redirect_uri
            + "&response_type=code'"
            + "</script>";

        return exeCode;
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLogin(@RequestParam("code") String code , HttpSession session) throws IOException, IOException {
        String endpoint="https://kauth.kakao.com/oauth/token";
        URL url =new URL(endpoint);
        String bodyData="grant_type=authorization_code";
        bodyData += "&client_id=" + client_id;
        bodyData += "&redirect_uri=" + redirect_uri;
        bodyData += "&code="+code;
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        BufferedWriter bw=new BufferedWriter(
                new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
        );
        bw.write(bodyData);
        bw.flush();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );
        String input="";
        StringBuilder sb=new StringBuilder();
        while((input=br.readLine())!=null){
            sb.append(input);
        }

        Gson gson=new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);

        endpoint="https://kapi.kakao.com/v2/user/me";
        url =new URL(endpoint);
        conn=(HttpsURLConnection)url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer "+oAuthToken.getAccess_token());
        conn.setDoOutput(true);
        br=new BufferedReader(
                new InputStreamReader(conn.getInputStream(),"UTF-8")
        );

        input="";
        sb=new StringBuilder();
        while((input=br.readLine())!=null) {
            sb.append(input);
        }

        KakaoProfile kakaoProfile=gson.fromJson(sb.toString(), KakaoProfile.class);

        String snsid = kakaoProfile.getId();
        KakaoProfile.KakaoAccount ac = kakaoProfile.getKakao_account();
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        String nickname = pf.getNickname();

        MemberDto mdto = ms.getMemberSns(snsid);
        if(mdto == null){
            mdto = new MemberDto();
            mdto.setNickname(nickname);
            mdto.setSnsid(snsid);
            mdto.setEmail(nickname);
            mdto.setProvider("Kakao");
            mdto.setProfileimg(pf.getProfile_image_url());
            ms.insert(mdto);
        }
        session.setAttribute("loginUser", mdto);
        return "redirect:/main";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @PostMapping("/fileup")
    public @ResponseBody HashMap<String, Object> fileUp(@RequestParam("imgPrev") MultipartFile file) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String uploadPathName = null;
        try {
            uploadPathName = sus.saveFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.put("url", uploadPathName);
        return result;
    }

    @PostMapping("/emailCheck")
    public @ResponseBody HashMap<String, Object> emailCheck(@RequestParam("email") String email) {
        HashMap<String, Object> result = new HashMap<>();
        MemberDto mdto = ms.getMember(email);
        if (mdto == null){
            result.put("result", 1);
        } else {
            result.put("result", 0);
        }
        result.put("email", email);
        return result;
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("dto") MemberDto memberdto, @RequestParam(value="reemail", required = false, defaultValue = "") String reemail, @RequestParam(value="pwdChk", required = false, defaultValue = "") String pwdChk, BindingResult result, Model model) {
        String url = "member/joinForm";
        model.addAttribute("reemail", reemail);

        if (memberdto.getEmail().equals("")) {
            model.addAttribute("msg", "이메일을 입력해주세요");
        } else if (memberdto.getPwd().equals("")) {
            model.addAttribute("msg", "패스워드를 입력해주세요");
        } else if (!memberdto.getPwd().equals(pwdChk)) {
            model.addAttribute("msg", "패스워드가 일치하지 않습니다.");
        } else if (memberdto.getNickname().equals("")) {
            model.addAttribute("msg", "닉네임을 입력해주세요");
        } else if (!memberdto.getEmail().equals(reemail)) {
            model.addAttribute("msg", "이메일 중복체크를 해주세요.");
        } else {
            ms.insert(memberdto);
            model.addAttribute("msg", "회원가입이 완료되었습니다.");
            url = "member/loginForm";
        }
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        MemberDto mdto = (MemberDto) session.getAttribute("loginUser");
        String url = "redirect:/";
        if (mdto != null) {
            ArrayList<String> followers = ms.selectFollowers(mdto.getId());
            ArrayList<String> followings = ms.selectFollowings(mdto.getId());
            model.addAttribute("followers", followers);
            model.addAttribute("followings", followings);
            model.addAttribute("list", ms.selectPost(mdto.getId()));
            url = "member/mypage";
        }
        return url;
    }

    @GetMapping("/editProfileForm")
    public String updateMemberForm(HttpSession session, Model model) {
        MemberDto mdto = (MemberDto) session.getAttribute("loginUser");
        String url = "member/loginForm";
        if (mdto != null) {
            model.addAttribute("dto", mdto);
            url = "member/editProfile";
        }
        return url;
    }
}