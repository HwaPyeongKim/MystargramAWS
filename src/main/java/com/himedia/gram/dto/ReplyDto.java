package com.himedia.gram.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReplyDto {
    private int id;
    private int memberid;
    private String nickname;
    private String email;
    private int postid;
    private String reply;
    private Timestamp writedate;
}