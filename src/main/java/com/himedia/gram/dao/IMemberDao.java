package com.himedia.gram.dao;

import com.himedia.gram.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {

    MemberDto getMember(String email);
    void insert(MemberDto mdto);
    MemberDto getMemberSns(String snsid);


}