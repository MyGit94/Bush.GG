$(document).ready(function() {
    $("#loginId").keyup(function() {
        var loginId = $(this).val();
        var regex = /^[a-z0-9]{6,12}$/; // 영어 소문자와 숫자로 이루어진 6~12자 사이

        // 최소 6자 이상인 경우에만 AJAX 요청을 보냅니다.
        if (loginId.length >= 2) {
            if (!regex.test(loginId)) {
                // 아이디가 허용된 문자 이외의 문자를 포함하고 있을 때 메시지 출력
                $("#loginIdError").text("영어 소문자와 숫자로 이루어진 6~12자 사이여야 합니다.");
            } else {
                // AJAX를 사용하여 서버에 아이디 중복 체크 요청
                $.ajax({
                    url: '/member/api/check-duplicate-loginid/' + loginId,
                    type: 'GET',
                    success: function(data) {
                        if (data.loginIdCheck) {
                            // 아이디 중복일 경우 메시지 출력
                            $("#loginIdError").text("이미 사용 중인 아이디입니다.");
                        } else {
                            // 아이디 중복이 아닐 경우 메시지 출력
                            $("#loginIdError").text("사용 가능한 아이디입니다.");
                        }
                    },
                    error: function() {
                        alert("치명적 오류 발생");
                    }
                });
            }
        } else {
            // 아이디가 2자 미만일 경우 메시지를 지웁니다.
            $("#loginIdError").text("");
        }
    });
});


    $("#nickName").keyup(function() {
    var nickName = $(this).val();
    var regex = /^([가-힣]{2,8}|[a-zA-Z0-9가-힣]{4,10})$/; // 한글 2~8글자 또는 한글, 영어 소문자, 대문자, 숫자 조합 4~12글자 조건에 맞는 정규 표현식
    // 최소 2글자 이상인 경우에만 AJAX 요청을 보냅니다.
    if (nickName.length >= 2) {
    if (!regex.test(nickName)) {
    // 닉네임이 조건에 맞지 않을 때 메시지 출력
    $("#nickNameError").text("닉네임은 한글 2~8글자, 영어 소문자 4~12글자,대문자 2~8글자 이하로만 입력하세요.");
} else {
    // AJAX를 사용하여 서버에 닉네임 중복 체크 요청
    $.ajax({
    url: '/member/api/check-duplicate-nickname/' + nickName, // 닉네임 중복 체크 API 주소 (변경 필요)
    type: 'GET',
    success: function(data) {
    if (data.nickNameCheck) {
    // 닉네임 중복일 경우 메시지 출력
    $("#nickNameError").text("이미 사용 중인 닉네임입니다.");
} else {
    // 닉네임 중복이 아닐 경우 메시지 출력
    $("#nickNameError").text("사용 가능한 닉네임입니다.");
}
},
    error: function() {
    alert("치명적 오류 발생");
}
});
}
} else {
    // 닉네임이 2글자 미만일 경우 메시지를 지웁니다.
    $("#nickNameError").text("");
}
});

    //이메일 주소 중복 체크 (이메일 입력란의 id를 사용)
    $("#email").keyup(function() {
    var email = $(this).val();

    // '@' 문자로 이메일 주소를 분리
    var parts = email.split('@');

    // 사용자 이름과 도메인 부분 가져오기
    var loginId = parts[0]; // 사용자 이름을 loginId로 변경
    var domain = parts[1];

    // 사용자 이름(loginId)과 도메인이 각각 최소 6글자 이상이어야 함
    if (loginId.length >= 6 && domain.length >= 6) {
    // AJAX 요청을 통해 이메일 중복 체크
    $.ajax({
    url: '/member/api/check-duplicate-email/' + email, // 이메일 중복 체크 API 주소 (변경 필요)
    type: 'GET',
    success: function(data) {
    if (data.EmailCheck) { // 수정: 변수 이름을 EmailCheck로 변경
    // 이메일 중복일 경우 메시지 출력
    $("#emailError").text("이미 사용 중인 이메일 주소입니다.");
} else {
    // 이메일 중복이 아닐 경우 메시지 출력
    $("#emailError").text("사용 가능한 이메일 주소입니다.");
}
},
    error: function() {
    alert("치명적 오류 발생");
}
});
} else {
    // 이메일이 최소 글자 수를 충족하지 못할 경우 메시지를 지웁니다.
    $("#emailError").text("");
}
});