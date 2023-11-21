package com.MusicSharing.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.MusicSharing.member.entity.MemberEntity;
import com.MusicSharing.member.repository.MemberRepository;
import com.MusicSharing.member.repository.FriendListRepository;
import com.MusicSharing.member.repository.FriendRequestRepository;
import com.MusicSharing.member.dto.MemberDTO;
import com.MusicSharing.member.dto.FriendListDTO;
import com.MusicSharing.member.entity.FriendListEntity;
import com.MusicSharing.member.entity.FriendRequestEntity;



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
            List<FriendRequestEntity> aaa = friendRequestRepository.findByRequestIdUseridAndRequestedIdUserid(id2, id1);
            if (aaa.size()!=0) {
                friendRequestRepository.deleteById(Math.toIntExact(aaa.get(0).getId()));
            }

            /* 수락 */
            Optional<MemberEntity> result1 = memberRepository.findById(id1);
            Optional<MemberEntity> result2 = memberRepository.findById(id2);
            MemberDTO result3 = null;
            MemberDTO result4 = null;
            if (result1.isPresent()) {
                result3 = modelMapper.map(result1.get(), MemberDTO.class);
            }
            if (result2.isPresent()) {
                result4 = modelMapper.map(result2.get(), MemberDTO.class);
            }
            FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
            friendListRepository.save(modelMapper.map(result, FriendListEntity.class));
            FriendListDTO resultt = FriendListDTO.builder().id2(result3).id1(result4).build();
            friendListRepository.save(modelMapper.map(resultt, FriendListEntity.class));
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
