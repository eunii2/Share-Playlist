document.querySelector('.login__input').addEventListener('submit', function (e) {
    e.preventDefault();
    sendit();
});

const sendit = () => {
    const userid = document.querySelector('input[name="userid"]');
    const userpw = document.querySelector('input[name="userpw"]');
    const userpw_ch = document.querySelector('input[name="userpw_ch"]');
    const username = document.querySelector('input[name="username"]');
    const userphone = document.querySelector('input[name="userphone"]');
    const useremail = document.querySelector('input[name="useremail"]');
    const usertage = document.querySelector('input[name="usertage"]');

    if (userid.value == '') {
        alert('아이디를 입력해주세요.');
        userid.focus();
        return false;
    }

    if (userid.value.length < 4 || userid.value.length > 12) {
        alert("아이디는 4자 이상 12자 이하로 입력해주세요.");
        userid.focus();
        return false;
    }

    if (userpw.value == '') {
        alert('비밀번호를 입력해주세요.');
        userpw.focus();
        return false;
    }

    if (userpw_ch.value == '') {
        alert('비밀번호 확인을 입력해주세요.');
        userpw_ch.focus();
        return false;
    }

    if (userpw.value.length < 6 || userpw.value.length > 20) {
        alert("비밀번호는 6자 이상 20자 이하로 입력해주세요.");
        userpw.focus();
        return false;
    }

    if (userpw.value != userpw_ch.value) {
        alert('비밀번호가 다릅니다. 다시 입력해주세요.');
        userpw_ch.focus();
        return false;
    }

    if (username.value == '') {
        alert('이름을 입력해주세요.');
        username.focus();
        return false;
    }

    const expNameText = /[가-힣]+$/;
    if (!expNameText.test(username.value)) {
        alert("이름 형식이 맞지않습니다. 형식에 맞게 입력해주세요.");
        username.focus();
        return false;
    }

    if (userphone.value == '') {
        alert('핸드폰 번호를 입력해주세요.');
        userphone.focus();
        return false;
    }

    if (userphone.value == '') {
        alert('핸드폰 번호를 입력해주세요.');
        userphone.focus();
        return false;
    }

    const expHpText = /^\d{3}-\d{3,4}-\d{4}$/;
    if (!expHpText.test(userphone.value)) {
        alert('핸드폰 번호 형식이 맞지 않습니다. 형식에 맞게 입력해주세요.');
        userphone.focus();
        return false;
    }

    var data = {
        userid: userid.value,
        userpw: userpw.value,
        username: username.value,
        userphone: userphone.value,
        useremail: useremail.value,
        usertage: usertage.value
    };

    fetch('/join', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(function (response) {
        if (response.ok) {
            console.log('회원가입 성공');
            window.location.href = '/index';
        } else {
            console.log('회원가입 실패');
            alert('회원가입 실패: ' + data.message);
        }
    }).catch(function (error) {
        console.log('Error:', error);
    });
    return false;
}