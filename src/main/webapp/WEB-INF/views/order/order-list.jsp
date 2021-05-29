<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h4>Đơn hàng</h4>
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
                            <th class="column-title">Id</th>
                            <th class="column-title">Giá tiền</th>
                            <th class="column-title">Thời gian</th> 
                            <th class="column-title"> + </th>              
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${list}" var="item" varStatus="i"> 
                          	<tr>
                            <td>${pageInfo.offSet + i.index + 1} </td>
                            <td>${item.id}</td>                        
                        	<td>${item.totalPrice}</td>             
                            <td>${item.date}</td>                   
                            <td  class="last text-center">
	                            <a href='<c:url value="/order/view/${item.id}">
	                            	</c:url>' class="btn btn-primary"><i class="glyphicon glyphicon-eye-open"></i>
	                           	</a> 
                            </td>   
                          	</tr>
                          </c:forEach>
							
                        </tbody>
                      </table>
     <jsp:include page="/WEB-INF/views/layout/paging.jsp"/>                      
 
      </div>

	</div>
</div>

<script type="text/javascript">
	function gotoPage(page) {
		location.href = "<c:url value='/order/list/'/>" + page;
	}

	$(document).ready(function() {
		var msgError = '${msgError}';
		var msgSuccess = '${msgSuccess}';
		if (msgError) {
			new PNotify({
				title : 'Thông Báo',
				text : msgError,
				type : 'error',
				styling : 'bootstrap3'

			});
		}
		if (msgSuccess) {
			new PNotify({
				title : 'Thông Báo',
				text : msgSuccess,
				type : 'success',
				styling : 'bootstrap3'
			});
		}
	});

	$(document).ready(function() {
				$('#orderlist').parents("li").addClass('active').siblings().removeClass("active");
				$('#orderlist').addClass('current-page').siblings().removeClass("current-page");
				$('#orderlist').parents().show();
			});
</script>
 