<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>알림</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        window.onload = function() {
            // 3초 후 자동 이동하거나 확인 버튼 클릭 시 이동
            setTimeout(function() {
                <c:choose>
                    <c:when test="${code == 'create_success'}">
                        location.href = "/dog_dmti/read.do?dogno=${dogno}";
                    </c:when>
                    <c:otherwise>
                        history.back();
                    </c:otherwise>
                </c:choose>
            }, 3000);
        };
    </script>
    <style>
        .msg-container {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .message-box {
            padding: 40px;
            border-radius: 20px;
            background-color: white;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            text-align: center;
        }
    </style>
</head>
<body style="background-color: #f8f9fa;">

<div class="msg-container">
    <div class="message-box">
        <c:choose>
            <c:when test="${code == 'create_success'}">
                <div class="mb-4">
                    <span style="font-size: 50px;">🎉</span>
                </div>
                <h3 class="fw-bold text-primary">분석이 완료되었습니다!</h3>
                <p class="text-muted mt-3">잠시 후 상세 결과 페이지로 이동합니다.</p>
                <a href="/dog_dmti/read.do?dogno=${dogno}" class="btn btn-primary mt-3 px-4">지금 결과 보기</a>
            </c:when>
            
            <c:when test="${code == 'create_fail'}">
                <div class="mb-4">
                    <span style="font-size: 50px;">⚠️</span>
                </div>
                <h3 class="fw-bold text-danger">저장 중 오류가 발생했습니다.</h3>
                <p class="text-muted mt-3">다시 시도해 주세요.</p>
                <button onclick="history.back()" class="btn btn-outline-danger mt-3 px-4">이전으로</button>
            </c:when>
        </c:choose>
    </div>
</div>

</body>
</html>