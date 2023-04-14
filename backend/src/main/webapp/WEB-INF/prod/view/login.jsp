<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>
        kakao login test
    </title>
</head>

<body>
    <h1>카카오 로그인 test</h1>

    <!--form-->
    <form method="POST" action ="{pageContext.request.contextPath}/loginProc">
        <input type ="submit" value="카카오 로그인">
    </form>

    <!--taglib-->
    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>
</body>
</html>