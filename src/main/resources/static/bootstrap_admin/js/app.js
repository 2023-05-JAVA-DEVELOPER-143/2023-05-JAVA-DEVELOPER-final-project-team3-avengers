//실시간 업데이트 버튼
$(function() {
	$('#dailyUpdate').click(function() {
		$.ajax({
			url: '/admin_data/daily_update',
			method: 'GET',
			success: function(data) {
				var updatedMonthlyStat = data.updatedMonthlyStat;
				var updated7dStat = data.updated7dStat;
			},
			error: function() {
				alert('Failed to update statistic.');
			}
		});
	});
});

//월별/일별 전환 버튼ㄴ
$(function() {
	$('#optionYear').click(function() {
		$.ajax({
			url: '/admin_data/option_year',
			method: 'GET',
			success: function(data) {
				$('#displayText').text('2023년 월별 데이터 추이');
				updateOptionList(data);
			},
			error: function() {
				alert('데이터 가져오기 실패');
			}
		});
	});
	$('#optionDays').click(function() {
		$.ajax({
			url: '/admin_data/option_days',
			method: 'GET',
			success: function(data) {
				$('#displayText').text('최근 7일 데이터 추이');
				updateOptionList(data);
			},
			error: function() {
				alert('데이터 가져오기 실패');
			}
		});
	});
	 function updateOptionList(data) {
            var chart = new ApexCharts(document.querySelector('#myBarChart'), {
				series:data
			})
			chart.render();
        }
});