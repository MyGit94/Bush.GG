

// 상세보기 버튼 클릭시 상세보기 버튼이 180도 회전 , 상세보기 컨텐츠가 아래로 출력

let detailViews = document.querySelectorAll(".record_detail_bt");
let detail = document.querySelectorAll(".record_detail_wrap");

detailViews.forEach((view, index) => {
  view.addEventListener("click", () => {
    detail[index].classList.toggle("on");
    detailViews[index].classList.toggle("on");
  });
});



// 같이한 플레이어 :: 소환사 네임이 7자리가 넘으면 6자리만 표기하고 나머지는 ..으로 표기하는 기능

let userName = document.querySelectorAll(".user_name_text");

userName.forEach((element) => {
  let length = element.textContent.length;
  // 글자길이가 7자리 이상넘으면 
  if(length >= 7){
    // 글자를 6개까지만 표기하고 나머지는 ... 으로 표시
    element.textContent = element.textContent.substring(0,6) + "...";
  }
});





// 데미지차트에 마우스 호버시 가한데미지 , 받은데미지에 설명칸을 출력하는 기능

let dmgCharts = document.querySelectorAll(".push_chartbar");
let getCharts = document.querySelectorAll(".get_chartbar");
let dmgHovers = document.querySelectorAll(".put_dmg");
let getHovers = document.querySelectorAll(".get_chart_info");

// 상세보기 리스트 :: 데미지 막대 차트 부분을 호버시 설명문구가 나오게 출력
dmgCharts.forEach((chart, index) => {
  chart.addEventListener("mouseenter", () => {
    dmgHovers[index].style.display = "block";
  });

  // 마우스 호버 안했을때 사라지게
  chart.addEventListener("mouseleave", () => {
    dmgHovers[index].style.display = "none";
  });
});

// 받은 데미지 부분 :: 마우스 호버시 설명문구 출력
getCharts.forEach((chart, index) => {
  chart.addEventListener("mouseenter", () => {
    getHovers[index].style.display = "block";
  });

  // 받은 데미지 부분 :: 마우스 미호버시 설명문구 사라짐
  chart.addEventListener("mouseleave", () => {
    getHovers[index].style.display = "none";
  });
});



// 전적 상세보기 :: 인게임 분석 버튼을 눌렀을때 나오는 그래프를 호버했을때 나오는 설명

// 그래프
let ingameInfoGraph = document.querySelectorAll(".kill_bar_graph");
// 승리팀 :: 호버시 나오는 영역
let ingameInfoGraphHover = document.querySelectorAll(".kill_bar_graph_hover");

ingameInfoGraph.forEach((graph , index)=>{
  graph.addEventListener("mouseenter" , () =>{
    ingameInfoGraphHover[index].style.display = "block";
  })
})

ingameInfoGraph.forEach((graph , index)=>{
  graph.addEventListener("mouseleave" , () =>{
    ingameInfoGraphHover[index].style.display = "none";
  })
})


// 전적 상세정보를 펼쳤을때 종합정보 , 인게임분석 , 사용한 룬정보 버튼 클릭에 따라 

// 종합정보 버튼
let detailBt = document.querySelectorAll(".detail_information_bt");
// 인게임 버튼
let ingameBt = document.querySelectorAll(".detail_ingame_bt");
// 룬 정보 버튼
let runeBt = document.querySelectorAll(".detail_rune_info_bt");

// 상세정보 전체영역
let detailWrap = document.querySelectorAll(".detail_info_wrap");
// 인게임분석 전체영역
let ingameWrap = document.querySelectorAll(".detail_ingame_info_wrap");
// 룬정보 전체영역
let runeWrap = document.querySelectorAll(".detail_rune_info_wrap");

// 종합정보 버튼을 눌렀을때 액션 설정
detailBt.forEach((button , index)=>{
  button.addEventListener("click" , ()=> {
    // active 클래스가 붙으면 활성화 , 클래스가 떼어지면 안보이게
  detailWrap[index].classList.add("active");
  ingameWrap[index].classList.remove("active");
  runeWrap[index].classList.remove("active");
  });
});


// 인게임분석 버튼을 눌렀을때 액션 설정
ingameBt.forEach((button , index)=>{
  button.addEventListener("click" , ()=> {
    // active 클래스가 붙으면 활성화 , 클래스가 떼어지면 안보이게
    detailWrap[index].classList.remove("active");
    ingameWrap[index].classList.add("active");
    runeWrap[index].classList.remove("active");
  });
});


// 룬분석 버튼을 눌렀을때 액션 설정
runeBt.forEach((button , index)=>{
  button.addEventListener("click" , ()=> {
    // active 클래스가 붙으면 활성화 , 클래스가 떼어지면 안보이게
    detailWrap[index].classList.remove("active");
    ingameWrap[index].classList.remove("active");
    runeWrap[index].classList.add("active");
  });
});





