<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div class="right_col" role="main">
	<div class="">
			<div class="page-title">
				<div class="title_left">
					<h4>Sản phẩm</h4>
				</div>
				<div class="clearfix"></div>
			</div>
				<div class="clearfix"></div>
			<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 ">
							 
						</div>
					</div>

	
	<div class="table-responsive">
		<a href='<c:url value="/products/add"></c:url>'><button class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>Thêm</button></a>
	 
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">#</th>
                            <th class="column-title">Id</th>
                            <th class="column-title">Hình ảnh</th>
                            <th class="column-title">Tên</th>
                            <th class="column-title">Danh mục</th>
                            <th class="column-title">Giá</th>                    
                            <th width="20%" class="column-title no-link last text-center" colspan="3" ><span class="nobr">Action</span>
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${list}" var="item" varStatus="i"> 
                          	<tr>
                            <td>${pageInfo.offSet + i.index + 1} </td>
                            <td>${item.id}</td> 
                            <td><img alt="" width="70px" height="70px" src='<c:url value="${item.imageUrl}"></c:url>'></td>                       
                        	<td class="title-product">${item.name}</td>
                        	<td>${item.category.name}</td>          
                        	<td>${item.price}</td>
                            <td colspan="3" class="last text-center">
                            	<input type="hidden" id="idProduct" value="${item.id}">
	                            <a href='<c:url value="/products/view/${item.id}"></c:url>' class="btn btn-primary"><i class="glyphicon glyphicon-eye-open"></i></a> 
	                            <a href='<c:url value="/products/edit/${item.id}"></c:url>' class="btn btn-warning"><i class="glyphicon glyphicon-edit"></i></a> 
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
		location.href="<c:url value='/products/list/'/>"+page;
	}
	function deleteItem(id){
		if(confirm("Bạn có chắc chắn xóa nó không ?")){
			location.href="<c:url value='/products/delete/'/>"+id;
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
		$('#productlist').parents("li").addClass('active').siblings().removeClass("active");
		$('#productlist').addClass('current-page').siblings().removeClass("current-page");
		$('#productlist').parents().show();
	});
	

	
</script>



