package com.msp.friend.service;
import java.util.List;

public interface FriendRequestService {
    List<List<String>> findFriendRequestByRequestId(String requestId);

    List<List<String>> findFriendRequestByRequestedId(String requestedId);
}
