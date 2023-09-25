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