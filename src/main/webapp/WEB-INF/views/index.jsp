<%@ page language="java" contentType="text/html; charset=UTF-16" pageEncoding="UTF-16"%>
<%@ include file="layout/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--<div class="container">--%>
<%--  <c:set var="pageSize" value="${boards.size}" />--%>
<%--  <c:set var="pageNumber" value="${boards.number}" />--%>
<%--  <c:forEach var="board" items="${boards.content}" varStatus="status">--%>
<%--    <c:set var="boardNumber" value="${(pageSize * pageNumber) + status.index + 1}" />--%>
<%--    <div class="card m-2">--%>
<%--      <div class="card-body d-flex justify-content-between align-items-center">--%>
<%--        <p>번호: ${boardNumber}</p>--%>
<%--        <h2 class="card-title">${board.title}</h2>--%>
<%--        <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>--%>
<%--      </div>--%>
<%--    </div>--%>
<%--  </c:forEach>--%>
<%--</div>--%>

<div class="container">
  <c:set var="pageSize" value="${boards.size}" />
  <c:set var="pageNumber" value="${boards.number}" />
  <c:forEach var="board" items="${boards.content}" varStatus="status">
    <c:set var="boardNumber" value="${(pageSize * pageNumber) + status.index + 1}" />
    <div class="card m-2">
      <div class="card-body justify-content-between align-items-center">
        <p>번호: ${boardNumber}</p>
        <h2 class="card-title">${board.title}</h2>
        <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
      </div>
    </div>
  </c:forEach>
</div>


  <ul class="pagination justify-content-center">
    <c:choose>
      <c:when test="${boards.first}">
        <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
      </c:when>
      <c:otherwise>
        <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
      </c:otherwise>
    </c:choose>


    <ul class="pagination">
      <c:forEach var="pageNum" begin="1" end="${boards.totalPages}">
        <c:choose>
          <c:when test="${pageNum == boards.number + 1}">
            <li class="page-item active"><a class="page-link btn-sm" href="#">${pageNum}</a></li>
          </c:when>
          <c:otherwise>
            <li class="page-item"><a class="page-link btn-sm" href="?page=${pageNum-1}">${pageNum}</a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </ul>


    <c:choose>
      <c:when test="${boards.last}">
        <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
      </c:when>
      <c:otherwise>
        <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
      </c:otherwise>
    </c:choose>
  </ul>
</div>
