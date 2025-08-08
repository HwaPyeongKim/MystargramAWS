package com.himedia.gram.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberDto {
    private int id;
//    @NotNull @NotEmpty
    private String email;
//    @NotNull @NotEmpty
    private String pwd;
//    @NotNull @NotEmpty
    private String nickname;
//    @NotNull @NotEmpty
    private String phone;
    private String provider;
    private String snsid;
    private String profileimg;
    private String profilemsg;
}