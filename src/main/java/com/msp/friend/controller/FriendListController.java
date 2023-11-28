package com.msp.friend.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msp.friend.dto.FriendListDTO;
import com.msp.friend.service.FriendListServiceImpl;

@RestController
@RequestMapping("/friendlist") // 컨트롤러의 기본 URL 설정
public class FriendListController {
    @Autowired  // 의존성 자동 주입
    private FriendListServiceImpl friendListService;
    private static final Logger log = LoggerFactory.getLogger(FriendListController.class); // log 출력을 위함
    @GetMapping("findAll")  //friendlist/findAll로 오는 GET 요청을 처리하는 메서드
    public ArrayList<FriendListDTO> findAll() { // 친구 목록을 모두 가져오는 메서드
        log.info("friendlist/findAll() 실행");
        ArrayList<FriendListDTO> result = null; // 결과를 저장할 ArrayList 선언
        result = friendListService.findAll();   // 모든 친구 목록을 가져와서 result에 저장
        return result;
    }

    @GetMapping("findFriendListId1")    // /friendlist/findFriendListId1로 오는 GET 요청을 처리하는 메서드
    public List<List<String>> findFriendListId1(String id1) { // 주어진 ID를 가진 사용자의 친구목록을 가져옴
        log.info("friendlist/findFriendListId1() 실행");
        List<List<String>> result = null;   // 결과를 저장할 List 선언
        result = friendListService.findFriendListId1(id1);  // 주어진 ID를 가진 사용자의 친구목록을 가져와서 result에 저장
        return result;
    }

    @DeleteMapping("delete") // 친구 관계를 삭제하는 메소드
    public String delete(String id1,String id2) {
        log.info("friendlist/delete() 실행");
        String result = null;   //결과를 저장할 string 선언
        result = friendListService.delete(id1,id2); // 서비스에 두 사용자의 친구 관계 삭제를 요청하고, 결과를 반환
        return result;
    }

    @PostMapping("post")    // /friendlist/post로 오는 POST 요청을 처리하는 메서드
    public String post(String id1, String id2) { // 친구 요청을 전달하는 메서드
        log.info("friendlist/post() 실행");
        String result = null;
        result = friendListService.addFriend(id1,id2); // 서비스에 친구 요청을 전달하고, 결과를 반환
        if (result.equals("친구상태")) {    // 만약 결과가 "친구 상태라면"
            return "이미 친구 상태입니다.";
        }
        return "친구요청 성공";
    }
}

