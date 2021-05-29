<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h4>Đặt món</h4>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 "></div>
		</div>

		<div class="table-responsive">
			<table class="table table-striped jambo_table bulk_action">
				<thead>
					<tr class="headings">
						<th class="column-title">#</th>
						<th class="column-title">Hình ảnh</th>
						<th class="column-title">Tên</th>
						<th class="column-title">Giá</th>
						<th width="20%" class="column-title no-link last text-center"
							colspan="3"><span class="nobr"> + </span></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="item" varStatus="i">
								<tr>
									<td>${pageInfo.offSet + i.index + 1}</td>
									<td><img alt="" width="70px" height="70px"
										src='<c:url value="${item.imageUrl}"></c:url>'></td>
									<td class="title-product">${item.name}</td>
									<td>${item.price}</td>
									<td colspan="3" class="last text-center"><a
										href="javascript:void(0)" onclick="orderItem(${item.id})"
										class="btn btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
									</td>
								</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/WEB-INF/views/layout/paging.jsp" />
		</div>

		<div class="page-title">
			<div class="title_left">
				<h4>Hoá đơn</h4>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 "></div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped jambo_table bulk_action">
				<thead>
					<tr class="headings">
						<th class="column-title">#</th>
						<th class="column-title">Tên món</th>
						<th class="column-title">Giá</th>
						<th class="column-title">Số lượng</th>
						<th class="column-title">Tổng giá</th>
						<th width="20%" class="column-title no-link last text-center"><span class="nobr"> + </span></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${list != null}">
						<c:forEach items="${invoice.listOrder}" var="item" varStatus="i">
							<tr>
								<td>${pageInfo.offSet + i.index + 1}</td>
								<td class="title-product">${item.product.name}</td>
								<td>${item.price}</td>
								<td>${item.count}</td>
								<td>${item.totalPrice}</td>
								<td class="last text-center"><a href="javascript:void(0)"
									onclick="deleteItem(${item.product.id})" class="btn btn-danger"><i
										class="glyphicon glyphicon-trash"></i></a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4"></td>
						<td>${invoice.totalPrice}</td>
						<td></td>
					</tr>
					<tr class="text-center">
						<td colspan="4"></td>
						<td></td>
						<td>
							<a class="btn btn-success" onclick="payInvoice()">Thanh toán</a>
							<a class="btn btn-danger" onclick="cancelInvoice()">Huỷ đơn</a>
						</td>
					</tr>
				</tfoot>
			</table>
			 
		</div>
	</div>
</div>

<script type="text/javascript">
	function gotoPage(page){
		location.href="<c:url value='/products/list/'/>"+page;
	}
	function orderItem(id){
		if(confirm("Bạn có chắc chắn muốn đặt nó không ?")){
			location.href="<c:url value='/orderfood/order/'/>"+id;
		}
	}
	function deleteItem(id){
		if(confirm("Bạn có chắc chắn muốn xoá nó không ?")){
			location.href="<c:url value='/orderfood/remove/'/>"+id;
		}
	}
	function cancelInvoice(){
		if(confirm("Bạn có chắc chắn muốn huỷ nó không ?")){
			location.href="<c:url value='/orderfood/cancel'/>";
		}
	}
	function payInvoice(){
		if(confirm("Bạn có chắc chắn muốn thanh toán không ?")){
			location.href="<c:url value='/orderfood/pay'/>";
		}
	}
	$(document).ready(function(){
		var msgError = '${msgError}';
		var msgSuccess ='${msgSuccess}';
		if(msgError){
			new PNotify({
		        title: 'Thông Báo',
		        text: msgError,
		        type: 'error',
		        styling: 'bootstrap3'
		        
		    });	
		}
		if(msgSuccess){
			new PNotify({
		        title: 'Thông Báo',
		        text: msgSuccess,
		        type: 'success',
		        styling: 'bootstrap3'
		    });	
		}
	});
	
	$(document).ready(function(){
		$('#orderfood').parents("li").addClass('active').siblings().removeClass("active");
		$('#orderfood').addClass('current-page').siblings().removeClass("current-page");
		$('#orderfood').parents().show();
	});
	

	
</script>



