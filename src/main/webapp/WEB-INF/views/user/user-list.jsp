<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div class="right_col" role="main">
	<div class="">
			<div class="page-title">
				<div class="title_left">
					<h4>Tài khoản</h4>
				</div>
				<div class="clearfix"></div>
			</div>
				<div class="clearfix"></div>
			<div class="clearfix"></div>
 
	<div class="table-responsive">
		<a href='<c:url value="/user/add"></c:url>'><button class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>Thêm</button></a>
		 
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">#</th>
                            <th class="column-title">Id</th>
                            <th class="column-title">Tên</th>  
                            <th class="column-title">Tên tài khoản</th>
                            <th class="column-title">Email</th>
                            <th class="column-title">Quyền</th>
                            <th class="column-title no-link last text-center" colspan="3" ><span class="nobr">Action</span>
                            </th>
                          </tr>
                        </thead>

                        <tbody>
                        
                       	   <c:forEach items="${list}" var="item" varStatus="i"> 
                          	<tr>
                            <td>${pageInfo.offSet + i.index + 1} </td>
                            <td>${item.id}</td>                        
                        	<td>${item.name}</td>
                        	<td>${item.username}</td>
                        	<td>${item.email}</td>
                        	<td>
                        		<c:choose>
                        			<c:when test="${item.role == 1}">
                        				Admin
                        			</c:when>
                        			<c:otherwise>
                        				User
                        			</c:otherwise>
                        		</c:choose>
                        	</td>
                            <td colspan="3" class="last text-center">
                            	<input type="hidden" id="idProduct" value="${item.id}">
	                            <a href='<c:url value="/user/view/${item.id}"></c:url>' class="btn btn-primary"><i class="glyphicon glyphicon-eye-open"></i></a> 
	                            <a href='<c:url value="/user/edit/${item.id}"></c:url>' class="btn btn-warning"><i class="glyphicon glyphicon-edit"></i></a> 
	                            <a href="javascript:void(0)" onclick="deleteItem(${item.id})" class="btn btn-danger btn-delete"><i class="glyphicon glyphicon-trash"></i></a>
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
	function gotoPage(page){
		location.href="<c:url value='/user/list/'/>"+page;
	}
	function deleteItem(id){
		if(confirm("Bạn có chắc chắn xóa nó không ?")){
			location.href="<c:url value='/user/delete/'/>"+id;
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
		$('#userlist').parents("li").addClass('active').siblings().removeClass("active");
		$('#userlist').addClass('current-page').siblings().removeClass("current-page");
		$('#userlist').parents().show();
	});
	

	
</script>



