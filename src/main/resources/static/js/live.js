// 1초마다 실행되는 함수
setInterval(function() {
    // .live_info_match_time 요소의 현재 값을 가져옴
    let value = document.querySelector('.live_info_match_time');
    let change = parseInt(value.textContent);

    let count = change + 1;
    let time = count.toString();
   value.textContent = time;
}, 1000); // 1000 밀리초(1초)마다 실행
