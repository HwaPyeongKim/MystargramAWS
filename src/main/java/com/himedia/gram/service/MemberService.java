package com.himedia.gram.service;

import com.himedia.gram.dao.IMemberDao;
import com.himedia.gram.dto.MemberDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    IMemberDao mdao;

    public MemberDto getMember(String email) {
        return mdao.getMember(email);
    }

    public void insert(MemberDto mdto) {
        mdao.insert(mdto);
    }

    public MemberDto getMemberSns(String snsid) {
        return mdao.getMemberSns(snsid);
    }
}