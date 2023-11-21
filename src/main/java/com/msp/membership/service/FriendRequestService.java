package com.MusicSharing.member.service;
import java.util.ArrayList;
import java.util.List;

import com.MusicSharing.member.dto.MemberDTO;
import com.MusicSharing.member.dto.FriendListDTO;

public interface FriendRequestService {
    List<List<String>> findFriendRequestByRequestId(String requestId);

    List<List<String>> findFriendRequestByRequestedId(String requestedId);
}
