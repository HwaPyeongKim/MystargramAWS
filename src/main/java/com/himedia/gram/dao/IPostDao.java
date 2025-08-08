package com.himedia.gram.dao;

import com.himedia.gram.dto.HashDto;
import com.himedia.gram.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPostDao {
    int insert(PostDto postdto);
    HashDto getHashtag(String tag);
    int insertHash(String tag);
    void insertHashPost(int postid, int hashid);
}