<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶログイン</h1>

<c:choose>
  <%-- セッションに loginUser が存在する場合（ログイン成功） --%>
  <c:when test="${not empty loginUser}">
    <p>ログインに成功しました</p>
    <p>ようこそ<c:out value="${loginUser.name}" />さん</p>
    <a href="Main">つぶやき投稿・閲覧へ</a>
  </c:when>
  
  <%-- それ以外（ログイン失敗） --%>
  <c:otherwise>
    <p>ログインに失敗しました</p>
    <a href="index.jsp">トップへ</a>
  </c:otherwise>
</c:choose>

</body>
</html>