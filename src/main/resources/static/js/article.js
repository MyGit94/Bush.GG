$(document).ready(function () {
    $(".writing").submit(function (e) {
        e.preventDefault();

        $.ajax({
            type: "POST",
            url: "/board/register",
            data: $(this).serialize(),
            success: function (data) {
                if (data === "success") {
                    window.location.href = "/board";
                } else if (data === "error") {
                    alert("관리자만 공지사항을 작성할 수 있습니다.");
                }
            }
        });
    });
});


$(document).ready(function() {
    $(".writeBtn").click(function(e) {
        var isLoggedIn = $(this).data("loggedin");

        if (!isLoggedIn) {
            e.preventDefault();
            alert("로그인이 필요합니다.");
            window.location.href = "/member/login"
        }
    });
});