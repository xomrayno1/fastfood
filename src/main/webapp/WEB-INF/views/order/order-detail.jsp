<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h4>Chi tiết hoá đơn</h4>
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
                            <th class="column-title">Tên</th>
                            <th class="column-title">Giá tiền</th>
                            <th class="column-title">Số lượng</th> 
                            <th class="column-title">Tổng tiền</th>         
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${list}" var="item" varStatus="i"> 
                          	<tr>
	                            <td>${pageInfo.offSet + i.index + 1} </td>
	                            <td>${item.id}</td>  
	                            <td>${item.product.name}</td>
	                            <td>${item.product.price}</td>  
	                            <td>${item.count}</td>                     
	                        	<td>${item.totalPrice}</td>                                
                          	</tr>
                          </c:forEach>
                        </tbody>
                        <tfoot>
                        	<tr>
                        		<td colspan="6">
                        			<a href='<c:url value="/order/list/1"></c:url>' class="btn btn-info">Quay lại</a>
                        		</td>
                        	</tr>
                        </tfoot>
                      </table>
     <jsp:include page="/WEB-INF/views/layout/paging.jsp"/>                      
      </div>
	</div>
</div>

<script type="text/javascript">

	$(document).ready(function() {
				$('#orderlist').parents("li").addClass('active').siblings().removeClass("active");
				$('#orderlist').addClass('current-page').siblings().removeClass("current-page");
				$('#orderlist').parents().show();
			});
</script>
 