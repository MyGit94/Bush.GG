// 미디어 쿼리 매체 유형
const mediaQuery = window.matchMedia('(max-width: 1100px)'); // 화면 너비 768px 이하

// 초기화 함수 호출
checkMediaQuery(mediaQuery);

// 미디어 쿼리 변경 감지 시 호출되는 함수
function handleMediaQueryChange(e) {
    checkMediaQuery(e);
}

// 미디어 쿼리 검사 및 클래스 추가/제거 함수
function checkMediaQuery(mq) {
    const ulElement = document.getElementById('accordionSidebar'); // 대상 ul 요소

    if (mq.matches) {
        ulElement.classList.add('toggled'); // 클래스 추가
    } else {
        ulElement.classList.remove('toggled'); // 클래스 제거
    }
}

// 미디어 쿼리 변경 감지 이벤트 리스너 등록
mediaQuery.addEventListener('change', handleMediaQueryChange);
