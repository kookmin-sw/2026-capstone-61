<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${dogVO.name}의 DMTI 분석 결과</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; font-family: 'Pretendard', sans-serif; }
        .result-card { border-radius: 20px; border: none; overflow: hidden; }
        .type-badge { font-size: 3rem; font-weight: 800; letter-spacing: 5px; color: #fff; background: linear-gradient(135deg, #6e8efb, #a777e3); padding: 20px 40px; border-radius: 15px; display: inline-block; margin-bottom: 20px; box-shadow: 0 10px 20px rgba(0,0,0,0.1); }
        .progress { height: 35px; border-radius: 17px; background-color: #e9ecef; margin-bottom: 10px; font-size: 1rem; font-weight: bold; }
        .label-box { display: flex; justify-content: space-between; font-weight: 600; margin-bottom: 5px; font-size: 0.95rem; }
    </style>
</head>
<body>

<div class="container py-5" style="max-width: 800px;">
    <div class="text-center mb-5">
        <h2 class="fw-bold text-dark mb-3">🐾 DMTI 분석 리포트</h2>
        <p class="text-muted mb-4">우리 <strong>${dogVO.name}</strong>(${dogVO.breed})는 이런 성향을 가졌어요!</p>
        <div class="type-badge">${dogVO.dbti_type}</div>
        <p class="mt-2 text-secondary">검사일: ${dogDbtiVO.updateDate}</p>
    </div>

    <div class="card result-card shadow-sm p-4 mb-4">
        <h3 class="h5 fw-bold mb-4 border-bottom pb-2">📊 성향 지표 분석</h3>

        <div class="mb-4">
            <div class="label-box">
                <span class="text-danger">외향(Energy) ${dogDbtiVO.ePer}%</span>
                <span class="text-secondary">내향(Introversion) ${dogDbtiVO.IPer}%</span>
            </div>
            <div class="progress">
                <div class="progress-bar bg-danger progress-bar-striped progress-bar-animated" style="width: ${dogDbtiVO.ePer}%">E</div>
                <div class="progress-bar bg-secondary" style="width: ${dogDbtiVO.IPer}%">I</div>
            </div>
        </div>

        <div class="mb-4">
            <div class="label-box">
                <span class="text-warning text-dark">사교적(Social) ${dogDbtiVO.sPer}%</span>
                <span class="text-dark">독립적(Normal) ${dogDbtiVO.NPer}%</span>
            </div>
            <div class="progress">
                <div class="progress-bar bg-warning" style="width: ${dogDbtiVO.sPer}%">S</div>
                <div class="progress-bar bg-dark" style="width: ${dogDbtiVO.NPer}%">N</div>
            </div>
        </div>

        <div class="mb-4">
            <div class="label-box">
                <span class="text-info">애정형(Affection) ${dogDbtiVO.aPer}%</span>
                <span class="text-muted">이성형(Thinker) ${dogDbtiVO.TPer}%</span>
            </div>
            <div class="progress">
                <div class="progress-bar bg-info" style="width: ${dogDbtiVO.aPer}%">A</div>
                <div class="progress-bar bg-light text-dark border" style="width: ${dogDbtiVO.TPer}%">T</div>
            </div>
        </div>

        <div class="mb-4">
            <div class="label-box">
                <span class="text-success">복종/계획(Obedience) ${dogDbtiVO.oPer}%</span>
                <span class="text-primary">자유/즉흥(Free) ${dogDbtiVO.FPer}%</span>
            </div>
            <div class="progress">
                <div class="progress-bar bg-success" style="width: ${dogDbtiVO.oPer}%">O</div>
                <div class="progress-bar bg-primary" style="width: ${dogDbtiVO.FPer}%">F</div>
            </div>
        </div>
    </div>

    <div class="alert alert-light border shadow-sm p-4">
        <h4 class="alert-heading fw-bold">💡 전문가 가이드</h4>
        <hr>
        <p class="mb-0">
            <strong>${dogVO.name}</strong>는 
            <c:choose>
                <c:when test="${dogVO.dbti_type.startsWith('E')}">활발하고 에너지가 넘쳐서 산책 시 친구들을 만나는 것을 즐깁니다.</c:when>
                <c:otherwise>차분하고 독립적인 성향이 강해 조용한 산책길을 선호할 가능성이 높습니다.</c:otherwise>
            </c:choose>
            <br><br>
            현재 결과 수치는 <strong>${dogDbtiVO.ePer}%</strong>의 외향성과 <strong>${dogDbtiVO.oPer}%</strong>의 복종도를 보이고 있습니다. 
            이를 고려한 맞춤형 훈련을 시작해보세요!
        </p>
    </div>

    <div class="text-center mt-4">
        <a href="/dog/list.do" class="btn btn-outline-secondary px-4">목록으로</a>
        <a href="/dog_dmti/create.do?dogNo=${dogVO.dogno}" class="btn btn-primary px-4">다시 검사하기</a>
    </div>
</div>

</body>
</html>