<%@ page language="java" contentType="text/html; charset=UTF-16" pageEncoding="UTF-16"%>
<%@ include file="layout/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container">
  <c:set var="pageSize" value="${boards.size}" />
  <c:set var="pageNumber" value="${boards.number}" />
  <c:forEach var="board" items="${boards.content}" varStatus="status">
    <c:set var="boardNumber" value="${(pageSize * pageNumber) + status.index + 1}" />
    <div class="card m-2">
      <div class="card-body d-flex justify-content-between align-items-center">
        <div>
          글 번호 : <span id="id"><i>${board.id} </i></span><br>
          작성자 : <span><i>${board.user.username} </i></span>
          <h2 class="card-title">${board.title}</h2>
        </div>
        <div class="text-right">
          <c:set var="createdAt" value="${board.createDate}" />
          <c:set var="currentTime" value="<%= new java.util.Date() %>" />
          <c:choose>
            <c:when test="${(currentTime.time - createdAt.time) <= 86400000}"> <!-- 게시물이 24시간 이내에 생성된 경우 86400000-->
              <span class="badge badge-info float-right">new</span><br>
            </c:when>
            <c:otherwise>
            </c:otherwise>
          </c:choose>
          <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<!-- 검색 폼 시작 -->
<ul class="pagination justify-content-center">
  <form method="GET" action="검색결과페이지.jsp">
    <div class="input-group">
      <select name="option">
        <option value="01" selected="selected">제목</option>
        <option value="02">내용</option>
      </select>
      <input type="text" id="키워드" name="키워드">
      <input type="submit" value="검색">
    </div>
  </form>
</ul>
<!-- 검색 폼 종료 -->

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

