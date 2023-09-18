let inputElement = document.querySelector(".input-group");
let loading = document.querySelector(".loading_wrap");
let searchBt = document.querySelector(".input-group button")

inputElement.addEventListener('keyup', function(event) {
    if (event.keyCode === 13) {
        loading.classList.toggle("on");
    }
});

searchBt.addEventListener("click" , ()=>{
    loading.classList.toggle("on");
});

// 뒤로 가기 버튼을 감지하여 스타일 초기화
window.addEventListener('popstate', function() {
    // 대상 div를 초기화하여 숨깁니다.
   loading.classList.remove('on');
});
