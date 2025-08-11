package com.himedia.gram.dao;

import com.himedia.gram.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface IMemberDao {

    MemberDto getMember(String email);
    void insert(MemberDto mdto);
    MemberDto getMemberSns(String snsid);
    MemberDto getMemberId(Integer userid);

    ArrayList<Integer> selectFollowers(int id);
    ArrayList<Integer> selectFollowings(int id);

}