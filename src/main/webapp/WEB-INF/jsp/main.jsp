<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶメイン</h1>
<p><c:out value="${loginUser.name}" />さん、ログイン中
<a href="Logout">ログアウト</a>
</p>
<p><a href="Main">更新</a></p>
<form action="Main" method="post" enctype="multipart/form-data">
<input type="text" name="text">
画像：<input type="file" name="image" accept="image/*" /><br>
<input type="submit" value="つぶやく">
</form>
<c:if test="${not empty errorMsg}">
  <p>${errorMsg}</p>
</c:if>
<BR>
<table border="1" cellspacing="0" cellpadding="5">
  <tr>
    <th>時間</th>
    <th>ユーザー名</th>
    <th>つぶやき</th>
    <th>画像</th>
    <th>制御</th>
  </tr>
  <c:forEach var="mutter" items="${mutterList}">
    <tr>
      <td><c:out value="${mutter.createdAt}" /></td>
      <td><c:out value="${mutter.userName}" /></td>
      <td><c:out value="${mutter.text}" /></td>
      <td>
        <c:if test="${not empty mutter.image}">
          <img src="ImageServlet?id=${mutter.id}" width="100" />
        </c:if>
      </td>
     <%-- 削除リンクを追加 --%>
      <td>
          <a href="DeleteMutterServlet?id=${mutter.id}" onclick="return confirm('本当に削除しますか？');">削除</a>
          <a href="EditFormServlet?id=${mutter.id}">編集</a></a></td>
    </tr>
  </c:forEach>
</table>


</body>
</html>