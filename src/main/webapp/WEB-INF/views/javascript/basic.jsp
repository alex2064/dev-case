<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	let a = 1;		// 변수
	const b = 2;	// 상수

	let userName = prompt("이름?", "영희");
	let isTeaWanted = confirm("차 한잔 드릴까요?");

	alert(`방문객 : ${userName}`);
	alert("차 주문 여부 : " + isTeaWanted);


	function sum(a, b){
		return a+b;
	}

	let sum1 = function(a, b){
		let result = a+b;
		return result;
	}

	let sum2 = (a,b) => a+b;

	let sum3 = (a,b) =>{
		return a+b;
	}

	// 인자값이 없는경우
	let say = () => alert("Hello");

	[1,2].forEach(alert);
</script>	



