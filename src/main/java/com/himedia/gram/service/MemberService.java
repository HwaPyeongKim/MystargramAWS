package com.himedia.gram.service;

import com.himedia.gram.dao.IMemberDao;
import com.himedia.gram.dao.IPostDao;
import com.himedia.gram.dto.FollowDto;
import com.himedia.gram.dto.MemberDto;
import com.himedia.gram.dto.PostDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Service
public class MemberService {

    @Autowired
    IMemberDao mdao;

    @Autowired
    IPostDao pdao;

    public MemberDto getMember(String email) {
        return mdao.getMember(email);
    }

    public void insert(MemberDto mdto) {
        mdao.insert(mdto);
    }

    public MemberDto getMemberSns(String snsid) {
        return mdao.getMemberSns(snsid);
    }

    public ArrayList<String> selectFollowers(int id) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> ids = mdao.selectFollowers(id);
        for (Integer userid : ids) {
            MemberDto mdto = mdao.getMemberId(userid);
            list.add(mdto.getNickname());
        }
        return list;
    }

    public ArrayList<String> selectFollowings(int id) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> ids = mdao.selectFollowings(id);
        for (Integer userid : ids) {
            MemberDto mdto = mdao.getMemberId(userid);
            list.add(mdto.getNickname());
        }
        return list;
    }

    public ArrayList<PostDto> selectPost(int writer) {
        ArrayList<PostDto> list = pdao.selectPost(writer);
        return list;
    }

    public void editProfile(MemberDto memberdto) {
        mdao.editProfile(memberdto);
    }

    public String checkNickname(@RequestParam String nickname) {
        return mdao.checkNickname(nickname);
    }
}