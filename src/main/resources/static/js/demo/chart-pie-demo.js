// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

Chart.defaults.global.defaultFontFamily = 'Nunito, -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// 도넛차트
var ctx = document.querySelectorAll(".myPieChart");

ctx.forEach((element) => {
  var myPieChart = new Chart(element, {
    type: 'doughnut', // 차트 유형: 도넛 차트
    data: {
      labels: ["Win", "Lose"], // 도넛 차트 섹션 레이블
      datasets: [{
        data: [winCount, loseCount], // 섹션별 데이터 값
        backgroundColor: ['#156549', 'red'], // 도넛 차트 섹션 배경색
      }],
    },

    options: {
      maintainAspectRatio: false, // 차트의 가로세로 비율 유지 안 함
      tooltips: {
        backgroundColor: "#fff", // 툴팁 배경색
        bodyFontColor: "#858796", // 툴팁 내용 글자 색상
        borderColor: '#dddfeb', // 툴팁 테두리 색상
        borderWidth: 1, // 툴팁 테두리 두께
        xPadding: 20, // X 패딩
        yPadding: 20, // Y 패딩
        displayColors: false, // 툴팁에 색상 상자 표시 안 함
        caretPadding: 10, // 삼각 화살표 패딩
      },
      legend: {
        display: false // 범례 숨김
      },
      cutoutPercentage: 80, // 도넛 차트의 중심에 뚫린 부분의 크기 (0-100)
    }
  });
});



