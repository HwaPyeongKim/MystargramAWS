package com.himedia.gram.dto;

import lombok.Data;

@Data
public class FollowDto {
    private int start;
    private String startNick;
    private int end;
    private String endNick;
}