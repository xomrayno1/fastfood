<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
	<div class="menu_section">
		<h3>General</h3>
			<ul class="nav side-menu" id="home">
				<li>
					<a href='<c:url value="/index"></c:url>'><i class="fa fa-home"></i>Trang chủ  </a>					
				</li>
			</ul>
			<ul class="nav side-menu" id="statistics">
					<li><a><i class="fa fa-pencil-square-o" aria-hidden="true"></i>Đặt món<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
							<li id="orderfood"><a href='<c:url value="/orderfood/list"></c:url>'>Đặt món</a></li>												
						</ul>
					</li>
			</ul>
			<c:if test="${userInfo.role == 1}">
				<ul class="nav side-menu" id="manage">
					<li><a><i class="fa fa-users"></i>Quản lý<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
							<li id="userlist"><a href='<c:url value="/user/list"></c:url>'>Tài khoản</a></li>	
							<li id="productlist"><a href='<c:url value="/products/list"></c:url>'>Sản phẩm</a></li>
							<li id="categorylist"><a href='<c:url value="/category/list"></c:url>'>Danh mục</a></li>	
						 	<li id="orderlist"><a href='<c:url value="/order/list"></c:url>'>Đơn hàng</a></li>	
						</ul>
					</li>
				</ul>
			</c:if>
	</div>
</div>