<<<<<<< HEAD
package com.msp.membership.controller;
=======
package com.MusicSharing.member.controller;
>>>>>>> 6269fa28bc8a7b9bd05f1810657f8226645249e9

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본 페이지 요청 메서드
    @GetMapping("/")    //기본 주소 요청이 오면 아래의 매소드가 온다라는 뜻
    public String index(){
        //간단한 코드 작성만으로도 모두 실행됨
        return "index"; // -> templates 폴더의 index.html을 찾아감
    }
}
