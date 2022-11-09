<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTim" value="${ForwardConst.ACT_TIM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardCont.CMD=EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>タイムシート　詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${timeSheet.name}" /></td>
                </tr>
                <tr>
                    <th>出勤日時</th>
                    <td><c:out value="${timeSheet.startTime}" /></td>
                </tr>
                <tr>
                    <th>退勤日時</th>
                    <td><c:out value="${timeSheet.finishTime}" /></td>
                </tr>
                <tr>
                    <th>残業理由</th>
                    <td><pre><c:out value="${timeSheet.overtimeReason}" /></pre></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='?action=${actTim}&command=${commEdit}&id=${timeSheet.id}' />">このタイムシートを修正する</a>
        </p>

        <p>
            <a href="<c:url value='?action=${actTim}&command=&{commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>