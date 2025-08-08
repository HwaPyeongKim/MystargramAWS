package com.himedia.gram.service;

import com.himedia.gram.dao.IPostDao;
import com.himedia.gram.dto.HashDto;
import com.himedia.gram.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostService {

    @Autowired
    IPostDao pdao;

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insert(PostDto postdto) {
        // 1. post 테이블에 레코드 추가
        pdao.insert(postdto);
        int postid = postdto.getId();

        // 2. content 내용에서 해시대크들만 추출
        String content = postdto.getContent();
        Matcher matcher = Pattern.compile("#([0-9a-zA-Z가-힣]*)").matcher(content);
        ArrayList<String> tags = new ArrayList<>();
        while (matcher.find()) {
            tags.add(matcher.group(1));
        }
        
        // 3. 추출된 해시트그들을 hashtag 테이블에 추가 (이미 있는 단어는 추가 X)
        int hashid = 0;
        for (String tag : tags) {
            HashDto hdto = pdao.getHashtag(tag);
            if (hdto != null) {
                hashid = hdto.getId();
            } else {
                pdao.insertHash(tag);
                hdto = pdao.getHashtag(tag);
                hashid = hdto.getId();
            }
            // 4. post 테이블의 추가된 id와 hashtag의 id로 hashpost 테이블에 레코드를 추가
            pdao.insertHashPost(postid, hashid);
        }

    }
}