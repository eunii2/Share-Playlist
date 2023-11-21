package com.msp.membership.service;
import java.util.ArrayList;
import java.util.List;

import com.msp.membership.dto.MemberDTO;
import com.msp.membership.dto.FriendListDTO;

public interface FriendRequestService {
    List<List<String>> findFriendRequestByRequestId(String requestId);

    List<List<String>> findFriendRequestByRequestedId(String requestedId);
}
