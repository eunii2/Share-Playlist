package com.msp.friend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msp.membership.repository.MemberRepository;
import com.msp.friend.repository.FriendListRepository;
import com.msp.membership.dto.MemberDTO;
import com.msp.friend.dto.FriendListDTO;
import com.msp.membership.entity.Member;
import com.msp.friend.entity.FriendList;

import org.modelmapper.ModelMapper;

@Service
public class FriendListServiceImpl implements FriendListService {
    private FriendListRepository friendListRepository;
    private MemberRepository memberRepository;

    private static ModelMapper modelMapper = new ModelMapper(); // 객체 맵핑을 위한 인스턴스 생성, modelMapper : 객체를 다른 객체로 변환(매핑)
    private static final Logger log = LoggerFactory.getLogger(FriendListServiceImpl.class); // log 출력을 위함
    public ArrayList<FriendListDTO> findAll() { // 모든 친구목록 조회
        Iterable<FriendList> result = friendListRepository.findAll(); // Repository로부터 모든 친구 목록 Entity를 가져옴
        ArrayList<FriendListDTO> result2 = new ArrayList<FriendListDTO>(); // 결과를 저장할 ArrayList 선언

        for (FriendList friend : result) { // 각 친구 목록 Entity를 DTO로 변환하고, ArrayList에 추가
            FriendListDTO friend2 = modelMapper.map(friend, FriendListDTO.class); // modelMapper를 이용해 Entity를 DTO로 변환
            result2.add(friend2);   // 변환된 DTO를 ArrayList에 추가
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

    public String delete(String id1, String id2){   // 두 사용자의 친구관계를 삭제하는 메소드
        try { //예외처리
            Optional<FriendList> result = friendListRepository.findMyFunction(id1, id2);    // 두 사용자의 친구 관계를 찾음
            Optional<FriendList> result2 = friendListRepository.findMyFunction(id2, id1);
            Integer list1 = null;
            Integer list2 = null;
            if (result.isPresent()) {   // 결과가 존재하면
                list1 = Math.toIntExact(result.get().getId());  // 결과의 ID를 가져옴
            }
            if (result2.isPresent()) {  // 결과가 존재하면
                list2 = Math.toIntExact(result2.get().getId()); // 결과의 ID를 가져옴
            }
            friendListRepository.deleteById(list1); // 두 사용자의 친구 관계를 삭제
            friendListRepository.deleteById(list2);

            return "삭제에 성공했습니다";
        } catch (Exception e) { //예외 발생시
            e.printStackTrace();
            return "삭제 중 오류가 발생했습니다";
        }
    }

    public String addFriend(String id1, String id2) {
        try {
            Optional<Member> result1 = memberRepository.findById(id1);
            Optional<Member> result2 = memberRepository.findById(id2);

            if (result1.isPresent() && result2.isPresent()) {
                MemberDTO result3 = modelMapper.map(result1.get(), MemberDTO.class);
                MemberDTO result4 = modelMapper.map(result2.get(), MemberDTO.class);

                // 친구 관계 생성
                FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
                friendListRepository.save(modelMapper.map(result, FriendList.class));
                FriendListDTO resultt = FriendListDTO.builder().id2(result3).id1(result4).build();
                friendListRepository.save(modelMapper.map(resultt, FriendList.class));

                return "친구 추가 완료";
            } else {
                return "친구 추가에 실패하였습니다. 유효한 회원 정보가 아닙니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "친구 추가 중 오류가 발생했습니다";
        }
    }}
