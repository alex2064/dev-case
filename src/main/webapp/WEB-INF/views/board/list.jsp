<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<div class="page-heading">
	<div class="page-title">
		<div class="row">
			<div class="col-12 col-md-6 order-md-1 order-last">
				<h3>Button</h3>
			</div>
			<div class="col-12 col-md-6 order-md-2 order-first">
				<div class="float-start float-lg-end">
					<button type="button" id="btnSearch" class="btn btn-info"><i class="fa-solid fa-magnifying-glass"></i> Search</button>
					<button type="button" id="btnAdd" class="btn btn-success"><i class="fa-solid fa-pen"></i> Write</button>
				</div>
			</div>
		</div>
	</div>	
</div>
<div class="page-content">
	<section class="section">
		<div class="card card-search">
			<div class="card-body">
				<div class="row">
					<div class="col-12 col-md-2">
						<label for="txiSTitle">Title</label>
						<input type="text" id="txiSTitle" class="form-control" placeholder="Title">
					</div>
					<div class="col-12 col-md-4">
						<label for="txiSFrDate">Update date</label>
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<input type="date" id="txiSFrDate" class="form-control">
							</div>
							<div class="col-md-6 col-xs-6 pl-0">
								<input type="date" id="txiSToDate" class="form-control">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="section">
		<div class="card">
			<div class="card-header">
				Datatable
			</div>
			<div class="card-body">
				<table id="tableList" class="table table-bordered table-hover display nowrap">
					<thead>
						<tr>
							<th>Title</th>
							<th>Contents</th>
							<th>Update date</th>
							<th>Update by</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</section>
</div>

<div class="modal fade text-left" id="modalEdit" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Edit Form </h4>
				<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i data-feather="x"></i></button>
			</div>
			<form id="formEdit" onsubmit="return false;">
				<div class="modal-body">
					<div class="form-group">
						<label>Title</label>
						<input type="text" id="txiTitle" class="form-control" placeholder="Title" required>
					</div>
					<div class="form-group">
						<label>Content</label>
						<input type="text" id="txiContent" class="form-control" placeholder="Content" required>
					</div>
					<input type="hidden" id="txiSeq">
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success"><i class="fa-solid fa-pen"></i> Write</button>
					<button type="button" id="btnDelete" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i> Delete</button>
					<button type="button" class="btn btn-warning" data-bs-dismiss="modal"><i class="fa-solid fa-ban"></i> Cancel</button>
				</div>
			</form>
		</div>
	</div>
</div>



<script type="text/javascript">
	$(document).ready(function(){

		/*******************
		 * INIT
		 *******************/

		// 날짜
		$("#txiSFrDate").val(defaultDate(7));
		$("#txiSToDate").val(defaultDate(0));


		
		/*******************
		 * Event
		 *******************/
		
		// 조회
		$("#btnSearch").click(function(event) {
			selectList();
		});

		function selectList(){
			var param = new Object();
			param.title = $("#txiSTitle").val();
			param.frDate = $("#txiSFrDate").val();
			param.toDate = $("#txiSToDate").val();

			$.ajax({
				url: "/board/list/page"
				, type: "post"
				, data: JSON.stringify(param)
				, dataType:"json"
				, headers : { "Accept":"application/json" ,"Content-Type":"application/json" }
				, beforeSend : function(xhr){ xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); $("#loadingBar").show();}
				, success: function(data){
					$("#tableList").DataTable().destroy();
					$("#tableList").DataTable({
						data:data.dsList
						, columns: [
							{data: "title"}
							, {data: "content"}
							, {data: "updatedDate"}
							, {data: "updatedBy"}
							//, {data: "left", className: "dt-body-left"}
							//, {data: "number", className: "dt-body-right" , render: function (data, type, row) { return renderNumber(data, 4); }}		
							]
					
						, initComplete: function(settings, json){
							var apiTable = this.api();
							
							$("#tableList tbody").off("dblclick");
							$("#tableList tbody").on("dblclick", "tr", function (){
								$("#txiSeq").val(apiTable.row(this).data().seq);
								$("#txiTitle").val(apiTable.row(this).data().title);
								$("#txiContent").val(apiTable.row(this).data().content);

								$("#btnDelete").attr("disabled", false);
								$("#modalEdit").modal("show");
							});
						}
						, dom: 'Blfrtip'
						, buttons: [
							{extend: "copy"
								, text: "<u>C</u>opy"
								, title: null
								, key: {key: "c", shiftKey: true}}
							, {extend: "excel"
								, text: "<u>E</u>xcel"
								, title: null
								, autoFilter: true
								, key: {key: "e", shiftKey: true}}
							]
						, select: {style: "single"}
					});
					$("#loadingBar").hide();
				}
				, error: function(e){
					Swal.fire("Exception", e, "error");
					$("#loadingBar").hide(); 
				}
			});
		};

		// 추가
		$("#btnAdd").click(function(event) {
			// 기존 데이터 비우기
			$("#formEdit .form-control").val("").attr("disabled", false);
			$("#formEdit select").trigger("change");
			$("#btnDelete").attr("disabled", true);
			$("#modalEdit").modal("show");
		});


		// 저장
		$("#formEdit").submit(function(event) {
			var param = new Object();
			param.seq = $("#txiSeq").val();
			param.title = $("#txiTitle").val();
			param.content = $("#txiContent").val();

			var method = "post";
			if($("#txiSeq").val() != ""){
				method = "put";
			}

			$.ajax({
				url: "/board/list"
				, type: method
				, data: JSON.stringify(param)
				, dataType:"json"
				, headers : { "Accept":"application/json" ,"Content-Type":"application/json" }
				, beforeSend : function(xhr){ xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); $("#loadingBar").show();}
				, success: function(data){
					if(data.result == "success"){
						selectList();
						$("#modalEdit").modal("hide");
						Swal.fire("Success", "", "success");
					}else{
						Swal.fire("Error", data.result, "error");
					}
					$("#loadingBar").hide();
				}
				, error: function(e){
					Swal.fire("Exception", e, "error");
					$("#loadingBar").hide(); 
				}
			});
		});

		// 삭제
		$("#btnDelete").click(function(event) {
			confirmDelete( deleteOne );
		});

		function deleteOne(){
			var param = new Object();
			param.seq = $("#txiSeq").val();

			$.ajax({
				url: "/board/list"
				, type: "delete"
				, data: JSON.stringify(param)
				, dataType:"json"
				, headers : { "Accept":"application/json" ,"Content-Type":"application/json" }
				, beforeSend : function(xhr){ xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); $("#loadingBar").show();}
				, success: function(data){
					if(data.result == "success"){
						selectList();
						$("#modalEdit").modal("hide");
						Swal.fire("Success", "", "success");
					}else{
						Swal.fire("Error", data.result, "error");
					}
					$("#loadingBar").hide();
				}
				, error: function(e){
					Swal.fire("Exception", e, "error");
					$("#loadingBar").hide(); 
				}
			});
		};




		
	});
</script>	



