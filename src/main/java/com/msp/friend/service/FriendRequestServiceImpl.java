package com.msp.friend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import com.msp.friend.repository.FriendListRepository;
import com.msp.friend.repository.FriendRequestRepository;
import com.msp.membership.dto.MemberDTO;
import com.msp.friend.dto.FriendListDTO;
import com.msp.friend.entity.FriendList;
import com.msp.friend.entity.FriendRequest;



@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private FriendRequestRepository friendRequestRepository;
    private FriendListRepository friendListRepository;
    private MemberRepository memberRepository;

    private static ModelMapper modelMapper = new ModelMapper();

    public List<List<String>> findFriendRequestByRequestId(String requestId) {
        List<List<String>> result = null;
        result = friendRequestRepository.findFriendRequestByRequestId(requestId);
        return result;
    }

    public List<List<String>> findFriendRequestByRequestedId(String requestedId) {
        List<List<String>> result = null;
        result = friendRequestRepository.findFriendRequestByRequestedId(requestedId);
        return result;
    }

    public String accept(String id, String id1, String id2) {
        try {
            Integer a = null;
            a = Integer.parseInt(id);
            friendRequestRepository.deleteById(a);
            List<FriendRequest> aaa = friendRequestRepository.findByRequestIdUseridAndRequestedIdUserid(id2, id1);
            if (aaa.size()!=0) {
                friendRequestRepository.deleteById(Math.toIntExact(aaa.get(0).getId()));
            }

            /* 수락 */
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
            FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
            friendListRepository.save(modelMapper.map(result, FriendList.class));
            FriendListDTO resultt = FriendListDTO.builder().id2(result3).id1(result4).build();
            friendListRepository.save(modelMapper.map(resultt, FriendList.class));
            return "수락요청";

        } catch (Exception e) {
            e.printStackTrace();
            return "수락하는 과정 중 오류가 발생했습니다";
        }
    }

    public String delete(String id) {
        try {
            Integer id2 = null;
            id2 = Integer.parseInt(id);
            friendRequestRepository.deleteById(id2);
            return "수락삭제완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "수락삭제하는 과정 중 오류가 발생했습니다";
        }
    }
}
