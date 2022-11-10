<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label>氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="${AttributeConst.TIM_START_TIME.getValue()}">出勤日時</label><br />
<input type="text" name="${AttributeConst.TIM_START_TIME.getValue()}" id="${AttributeConst.TIM_START_TIME.getValue()}" value="${timeSheet.startTime}" />
<br /><br />

<label for="${AttributeConst.TIM_FINISH_TIME.getValue()}">退勤日時</label><br />
<input type="text" name="${AttributeConst.TIM_FINISH_TIME.getValue()}" id="${AttributeConst.TIM_FINISH_TIME.getValue()}" value="${timeSheet.finishTime}" />
<br /><br />

<label for="${AttributeConst.TIM_OVERTIME_REASON.getValue()}">残業理由</label><br />
<input type="text" name="${AttributeConst.TIM_OVERTIME_REASON.getValue()}" id="${AttributeConst.TIM_OVERTIME_REASON.getValue()}" value="${timeSheet.overtimeReason}" />
<br /><br />
<input type="hidden" name="${AttributeConst.TIM_ID.getValue()}" value="${timeSheet.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">確定</button>