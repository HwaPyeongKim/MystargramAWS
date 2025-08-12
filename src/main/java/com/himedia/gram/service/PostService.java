package com.himedia.gram.service;

import com.himedia.gram.dao.IPostDao;
import com.himedia.gram.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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

    public HashMap<String, Object> select(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDto mdto = (MemberDto) session.getAttribute("loginUser");

        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", page);
        } else if (session.getAttribute("page") != null) {
            page = (Integer) session.getAttribute("page");
        }

        Paging paging = new Paging();
        paging.setPage(page);
        paging.setDisplayPage(5);
        paging.setDisplayRow(3);
        int count = pdao.getAllCountPost();
        paging.setTotalCount(count);
        paging.calPaging();

        if (page > paging.getEndPage()) {
            paging.setPage(paging.getEndPage());
            paging.calPaging();
        }

        ArrayList<PostDto> list = pdao.select(paging);
        for  (PostDto postdto : list) {
            // loginUser가 현재 게시물에 "좋아요"를 눌렀는지 조회해서 PostDto 의 like 변수에 저장
            LikeDto ldto = pdao.selectLike(mdto.getId(), postdto.getId());
            if (ldto != null) {
                postdto.setLike("Y");
            } else {
                postdto.setLike("N");
            }

            FollowDto fdto = pdao.selectFollow(mdto.getId(), postdto.getWriter());
            if (fdto != null) {
                postdto.setFollow("Y");
            } else {
                postdto.setFollow("N");
            }
        }
        result.put("list", list);
        result.put("paging", paging);
        return result;
    }

    public void like(int postid, int memberid) {
        LikeDto ldto = pdao.selectLike(memberid, postid);
        if (ldto != null) {
            pdao.deleteLike(memberid, postid);
        } else {
            pdao.addLike(memberid, postid);
        }
    }

    public void follow(HttpServletRequest request, int writer) {
        HttpSession session = request.getSession();
        MemberDto mdto = (MemberDto) session.getAttribute("loginUser");
        FollowDto fdto = pdao.selectFollow(mdto.getId(), writer);
        if (fdto != null) {
            pdao.deleteFollow(mdto.getId(), writer);
        } else {
            pdao.addFollow(mdto.getId(), writer);
        }
    }

    public HashMap<String, Object> search(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        String key = request.getParameter("key");
        HashDto hdto = pdao.getHashtag(key);
        ArrayList<PostDto> list = new ArrayList<>();
        int hashid = 0;
        if (hdto != null) {
            hashid = hdto.getId();
            result.put("result", "1");
            ArrayList<Integer> postids = pdao.selectPostid(hashid);
            for (Integer postid : postids) {
                PostDto pdto = pdao.getPost(postid);
                list.add(pdto);
            }
            result.put("list", list);
            result.put("key", key);
        } else {
            result.put("result", "0");
        }

        return result;
    }

    public HashMap<String, Object> getPost(HttpServletRequest request, int id) {
        HashMap<String, Object> result = new HashMap<>();
        PostDto postdto = pdao.getPost(id);
        ArrayList<ReplyDto> list = pdao.selectReply(id);
        HttpSession session = request.getSession();
        MemberDto mdto = (MemberDto) session.getAttribute("loginUser");
        LikeDto ldto = pdao.selectLike(mdto.getId(), postdto.getId());
        if (ldto != null) {
            postdto.setLike("Y");
        } else {
            postdto.setLike("N");
        }

        result.put("post", postdto);
        result.put("replyList", list);
        return result;
    }

    public void writeReply(ReplyDto replydto) {
        pdao.writeReply(replydto);
    }

    public void deleteReply(int replyid) {
        pdao.deleteReply(replyid);
    }
}