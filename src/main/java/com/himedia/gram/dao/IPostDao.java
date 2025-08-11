package com.himedia.gram.dao;

import com.himedia.gram.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface IPostDao {
    int insert(PostDto postdto);
    HashDto getHashtag(String tag);
    int insertHash(String tag);
    void insertHashPost(int postid, int hashid);
    int getAllCountPost();
    ArrayList<PostDto> select(Paging paging);

    LikeDto selectLike(int memberid, int postid);
    void addLike(int memberid, int postid);
    void deleteLike(int memberid, int postid);

    FollowDto selectFollow(int memberid, int writer);
    void addFollow(int memberid, int writer);
    void deleteFollow(int memberid, int writer);

    ArrayList<Integer> selectPostid(int hashid);

    PostDto getPost(Integer postid);

    ArrayList<PostDto> selectPost(int writer);
}