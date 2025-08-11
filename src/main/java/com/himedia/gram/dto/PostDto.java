package com.himedia.gram.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDto {
    private int id;
    private String content;
    private int writer;
    private Timestamp writedate;
    private String image;
    private int mid;
    private String nickname;
    private String email;

    private String like;
    private String follow;
    private int likecount;
    private int replycount;
}