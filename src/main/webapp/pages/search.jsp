<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>DBMS</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@200;300;400;600;900&display=swap" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">


    <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo/favicon.ico">
</head>

<body>

<!-- Page Preloder -->

<!-- Breadcrumb Section End -->
<c:import url="/header.jsp">
    <c:param name="navbar_opt" value="1"/>
    <c:param name="title" value="Search"/>
</c:import>
<!-- Profile Section Begin -->

<!-- Profile Section End -->

<!-- Profile Function Section Begin -->
<div class="row" id="notifice">
    <div class="col-lg-6"></div>
    <div class="col-lg-6">
        <c:choose>
            <c:when test="${status==null}">
            </c:when>
            <c:when test="${status.contains(\"thành công\")}">
                <h5 class="alert alert-info" role="alert"><c:out value="${status}"/></h5>
            </c:when>
            <c:otherwise>
                <h5 class="alert alert-warning" role="alert"><c:out value="${status}"/></h5>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script>
</script>
<section class="profile__featured">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="featured__controls">
                    <ul>
                        <li class="active" data-filter=".general">Tìm Kiếm Từ Đã Lưu Vào Database</li>
                       <!-- <li class="" data-filter=".lecturer">Thông tin lớp học của mỗi Giảng Viên</li> --> 
                    </ul>
                </div>
            </div>
        </div>
        <div class="row justify-content-center profile__options__filter">

            <div class="profile__featured__general  col-lg-12 mix general">
                <div class="row justify-content-center align-items-center">
                    <div class="col-lg-5">
                        <form action="searchword" id="signup_form" method="get">
                            <!-- form field-->
                            <div class="form-group">
                                <label class="control-label" for="word_in">Nhập từ cần tìm:</label>
                                <input class="form-control" type="text" name="word_in" id="word_in" value="${word}" required>
                            </div>
                            <div class="form-group text-center">
	                            <div class="form-group text-center">
	       							<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="db" id="oracle" value="oracle">
									  <label class="form-check-label" for="oracle">Oracle</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="db" id="hbase" value="hbase">
									  <label class="form-check-label" for="inlineRadio2">HBase</label>
									</div><!-- 
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="db" id="both" value="both">
									  <label class="form-check-label" for="inlineRadio3">Both</label>
									</div> -->
	       						</div>
                                <button type="submit" class="site-btn" id="btn-form" name="btn-form" value="search">
                                    Search
                                </button>
                            </div>
                        </form>
                    </div>
                    <div class="col-lg-10 text-center">
                 		   <div class="form-group row" style ="margin-left: 0px">
                                <label class="col-form-label" for="search_time">${search_time}</label>
                           </div>
                           <div class="form-group row" style ="margin-left: 0px">
                                <label class="col-form-label" for="search_time">${search_total}</label>
                           </div>
                        <c:choose>
                            <c:when test="${word_list==null}">
                            </c:when>
                            <c:otherwise>
                                    <table id="user_list" class="table table-striped">
                                        <thead>
                                        
                                        <tr class="table-primary">
                                        	<th>STT Trong File Ban Đầu</th>
                                            <th>Các Từ Tìm Thấy</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${word_list}" var="entry" varStatus="loop">
                                            <tr>
                                    			<td>
                                                	<c:out value="${entry.getStt()}"/>
                                                </td>
                                                <td>
                                                	<c:out value="${entry.get_enWord()}"/>
                                                </td>
                                               
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>              
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.slicknav.js"></script>
<script src="${pageContext.request.contextPath}/js/mixitup.min.js"></script>
<script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/aaoemployee/main.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/customer/main.js"></script>
</body>
</html>
