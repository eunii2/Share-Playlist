package com.msp.membership.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.msp.membership.repository.FriendListRepository;
import com.msp.membership.dto.FriendListDTO;
import com.msp.membership.entity.MemberEntity;
import com.msp.membership.entity.FriendListEntity;
import com.msp.membership.service.FriendListServiceImpl;

@RestController
@RequestMapping("friendlist") // 컨트롤러의 기본 URL 설정
public class FriendListController {
    @Autowired
    private FriendListServiceImpl friendListService;

    @GetMapping("findAll")
    public ArrayList<FriendListDTO> findAll() {
        System.out.println("friendlist/findAll() 실행");
        ArrayList<FriendListDTO> result = null;
        result = friendListService.findAll();
        return result;
    }

    @GetMapping("findFriendListId1")
    public List<List<String>> findFriendListId1(String id1) { // 주어진 ID를 가진 사용자의 친구목록을 가져옴
        System.out.println("friendlist/findFriendListId1() 실행");
        List<List<String>> result = null;
        result = friendListService.findFriendListId1(id1);
        return result;
    }

    @DeleteMapping("delete") // 친구 관계를 삭제하는 메소드
    public String delete(String id1,String id2) {
        System.out.println("friendlist/delete() 실행");
        String result = null;
        result = friendListService.delete(id1,id2); // 서비스에 두 사용자의 친구 관계 삭제를 요청하고, 결과를 반환
        return result;
    }

    @PostMapping("post")
    public String post(String id1, String id2) {
        System.out.println("friendlist/post() 실행");
        String result = null;
        result = friendListService.post(id1,id2); // 서비스에 친구 요청을 전달하고, 결과를 반환
        if (result.equals("친구상태")) {
            return "이미 친구 목록에 존재합니다";
        }else if(result.equals("친구신청 이미보냄")) {
            return "이미 친구신청을 보냈습니다";
        }return "친구요청 성공";
    }
}

