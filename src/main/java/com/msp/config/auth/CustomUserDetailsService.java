package com.msp.config.auth;

import com.msp.membership.entity.Member;
import com.msp.membership.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        Member member  = memberRepository.findByUserid(userid)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다"));

        CustomUserDetails customUser = new CustomUserDetails();

        customUser.setId(member.getId()); // 수정된 부분
        customUser.setUserid(member.getUserid()); // 수정된 부분
        customUser.setUserpw(member.getUserpw()); // 수정된 부분

        return customUser;
    }
}
