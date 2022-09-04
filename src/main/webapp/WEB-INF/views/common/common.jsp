<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>


<script type="text/javascript">
	// dataTable default setting
	$.extend(true, $.fn.dataTable.defaults, {
		autoWidth: false
		, info: true					// 총갯수 등 정보표시
		, lengthChange: true			// 표시 건수
		, lengthMenu: [[30,50,100,-1],[30,50,100,"ALL"]]	// 표시 건수 메뉴 -1은 전체 조회
		, displayLength: 30				// 표시 건수 기본값
		, ordering: true				// 정렬
		, order: []						// 초기 정렬 표시 안함
		, paging: true					// 페이징
		, pagingType: "full_numbers"	// 페이징 표시 타입 (ex. full_numbers, simple_numbers)
		, searching: false				// 검색 jquery.dataTables.js 에서 "bFilter": false,로 변경
		, processing: true				// table 셋팅 중 메세지
		, deferRender: true				// 페이지가 그려질때 필요한 노드만 생성(데이터가 많으면 속도차가 발생)
		, select: true					// 그리드  선택   단일항목 : {style: "single"} 
		, columnDefs:[{targets: "_all", className: "dt-center"}]	// DataTable 기본 가운데 정렬
		, scrollX: true
		, scrollY: "50vh"
		, scrollCollapse: true
		//, rowReorder: {selector: "tr"}	// 클릭앤드래그로 행 재정렬 true면 첫 열로만 가능(다른 기능 영향인지 안됨)
	});

	function confirmDelete(callback){
		Swal.fire({
			title: "Do you want to delete?"
			, text: "You won't be able to revert this!"
			, icon: "warning"
			, showCancelButton: true
			, confirmButtonColor: "#dc3545"
			, cancelButtonColor: "#ffc107"
			, confirmButtonText: "Delete"
			}).then((result) => {
				if (result.isConfirmed) {
					callback();
				}
			});		
	};

	function renderNumber(data, digits){
		return data.toLocaleString(undefined, {maximumFractionDigits:digits});
	};

	function defaultDate(period){
		var d = new Date();
		d.setDate(d.getDate()-period);
		return d.toISOString().substring(0,10);
	};

	var lineChartOptions = {
    	responsive: true
		, maintainAspectRatio: false
		, title: {
			display: false
			, text: 'Line Chart'
        }
		, tooltips: { mode: 'index', intersect: false }	// enabled: false 
		, legend: { position: 'bottom' }
		, hover: { animationDuration: 0 } // mode: 'nearest', intersect: true
        , scales: {
			xAxes: [{
				display: true
				, scaleLabel: {
					display: false
					, labelString: 'Month'
                }
			}]
			, yAxes: [{
				ticks: {
					callback: function(data, index) {
						return data.toLocaleString();
					}
                  }
				, display: true
				, scaleLabel: {
					display: false
					, labelString: 'Value'
				}
			}]
		}
    };

	var barChartOptions = {
    	responsive: true
		, maintainAspectRatio: false
		, responsiveAnimationDuration:500
		, title: {
			display: false
			, text: 'Bar Chart'
        }
        , tooltips: { mode: 'index', intersect: false }	// enabled: false 
		, legend: { position: 'bottom' }
		, hover: { animationDuration: 0 } // mode: 'nearest', intersect: true
		/*
		, animation: {
			onComplete: function(){
				var chart = this.chart;
				var ctx = chart.ctx;

				var fontSize = Chart.defaults.global.defaultFontSize;
				var fontStyle = Chart.defaults.global.defaultFontStyle;
				var fontFamily = Chart.defaults.global.defaultFontFamily;
				ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
				ctx.fillStyle = "#000000";
				ctx.textAlign = "center";
				ctx.textBaseline = "middle";

				chart.data.datasets.forEach(function(dataset, i){
					var meta = chart.getDatasetMeta(i);
					if (!meta.hidden) {
						meta.data.forEach(function(element, index) {
							var dataString = dataset.data[index].toLocaleString();
							var position = element.tooltipPosition();
							var padding = 5;
							ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
						});
					}
				});
			}
		}
		*/
		, scales: {
			xAxes: [{
				display: true
				, stacked: true
				, scaleLabel: {
					display: false
					, labelString: 'Month'
                }
			}]
			, yAxes: [{
				ticks: {
					callback: function(data, index) {
						return data.toLocaleString();
					}
                  }
				, display: true
				, stacked: true
				, scaleLabel: {
					display: false
					, labelString: 'Value'
				}
			}]
		}
    };

    
	/*
	Chart.plugins.register({
		afterDatasetDraw: function(chart){
			var ctx = chart.ctx;

			var fontSize = Chart.defaults.global.defaultFontSize;
			var fontStyle = Chart.defaults.global.defaultFontStyle;
			var fontFamily = Chart.defaults.global.defaultFontFamily;
			ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
			ctx.fillStyle = "#000000";
			ctx.textAlign = "center";
			ctx.textBaseline = "middle";

			chart.data.datasets.forEach(function(dataset, i){
				var meta = chart.getDatasetMeta(i);
				if (!meta.hidden) {
					meta.data.forEach(function(element, index) {
						var dataString = dataset.data[index].toLocaleString();
						var position = element.tooltipPosition();
						var padding = 5;
						ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
					});
				}
			});
		}
	});
	*/

	$(document).ready(function(){
		// 필수값 표시
		$("[required]").addClass("border-primary");

		// select Tag에 필터기능 설정
		$("select").select2();
		$(".select2").css("width", "100%");
		$("select[required]").next().find(".select2-selection").addClass("border-primary");

		// modal에서 헤더 사이즈 다시 맞추기
		$(".modal").off("shown.bs.modal").on("shown.bs.modal", function(e){
			$(this).find($.fn.dataTable.tables(true)).DataTable().columns.adjust();
		});

		// tab에서 헤더 사이즈 다시 맞추기
		$("a[id^=tab].nav-link").off("shown.bs.tab").on("shown.bs.tab", function(e){
			$($(this).attr("href")).find($.fn.dataTable.tables(true)).DataTable().columns.adjust();
		});

		// 조회조건에서 엔터시 조회
		$(".card-search .form-control").off("keyup").keyup(function(event) {
			if(event.keyCode == 13){
				$(this).closest(".content-body").parent().find("[id^=btnSearch]").click();
			}
    	});
	});

</script>