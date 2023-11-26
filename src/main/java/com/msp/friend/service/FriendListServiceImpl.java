package com.msp.friend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msp.membership.repository.MemberRepository;
import com.msp.friend.repository.FriendListRepository;
import com.msp.friend.repository.FriendRequestRepository;
import com.msp.membership.dto.MemberDTO;
import com.msp.friend.dto.FriendListDTO;
import com.msp.friend.dto.FriendRequestDTO;
import com.msp.membership.entity.Member;
import com.msp.friend.entity.FriendList;
import com.msp.friend.entity.FriendRequest;

import org.modelmapper.ModelMapper;

@Service
public class FriendListServiceImpl implements FriendListService {
    private FriendListRepository friendListRepository;
    private MemberRepository memberRepository;
    private FriendRequestRepository friendRequestRepository;

    private static ModelMapper modelMapper = new ModelMapper();
    private static final Logger log = LoggerFactory.getLogger(FriendListServiceImpl.class); // log 출력을 위함
    public ArrayList<FriendListDTO> findAll() { // 모든 친구목록 조회
        Iterable<FriendList> result = friendListRepository.findAll(); // Repository로부터 모든 친구 목록 Entity를 가져옴
        ArrayList<FriendListDTO> result2 = new ArrayList<FriendListDTO>();

        for (FriendList friend : result) { // 각 친구 목록 Entity를 DTO로 변환하고, ArrayList에 추가
            FriendListDTO friend2 = modelMapper.map(friend, FriendListDTO.class);   // modelMapper : 객체를 다른 객체로 변환(매핑)
            result2.add(friend2);
            log.info("Friend2: {}", friend2);
        }
        return result2;
    }

    public List<List<String>> findFriendListId1(String id1) { // 주어진 ID를 가진 사용자의 친구 목록을 조회하는 메소드
        List<List<String>> result = null;
        result = friendListRepository.findId2ById1Userid(id1);   // Repository로부터 해당 사용자의 친구 목록을 가져옴
        log.info("Result: {}", result);
        return result;
    }

    public String delete(String id1, String id2) {
        try { //예외처리
            Optional<FriendList> result = friendListRepository.findMyFunction(id1, id2);
            Optional<FriendList> result2 = friendListRepository.findMyFunction(id2, id1);
            Integer list1 = null;
            Integer list2 = null;
            if (result.isPresent()) {
                list1 = Math.toIntExact(result.get().getId());
            }
            if (result2.isPresent()) {
                list2 = Math.toIntExact(result2.get().getId());
            }
            friendListRepository.deleteById(list1);
            friendListRepository.deleteById(list2);

            return "삭제에 성공했습니다";
        } catch (Exception e) {
            e.printStackTrace();
            return "삭제 중 오류가 발생했습니다";
        }
    }

    public String post(String id1, String id2) {
        /* 아이디로 해당 유저 검색 */
        Optional<Member> result1 = memberRepository.findById(id1);
        Optional<Member> result2 = memberRepository.findById(id2);
        MemberDTO result3 = null;
        MemberDTO result4 = null;
        if (result1.isPresent()) {
            result3 = modelMapper.map(result1.get(), MemberDTO.class);
        }
        if (result2.isPresent()) {
            result4 = modelMapper.map(result2.get(), MemberDTO.class);
        }
        Optional<FriendList> result5 = friendListRepository.findMyFunction(id1, id2);
        log.info("result5: {}", result5);
        if (result5.isPresent()) {
            return "친구상태";
        } else {
            FriendRequestDTO result = FriendRequestDTO.builder().requestId(result3).requestedId(result4).build();
            try {
                friendRequestRepository.save(modelMapper.map(result, FriendRequest.class));
            } catch (Exception e) {
                e.printStackTrace();
                return "친구신청 이미보냄";
            }
        }
        return "친구요청 성공";
    }

}
