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

    private boolean like;
    private boolean follow;
}