<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTim" value="${ForwardConst.ACT_TIM.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>タイムシート　一覧</h2>
        <table id="timeSheet_list">
            <tbody>
                <tr>
                    <th>氏名</th>
                    <th>出勤日時</th>
                    <th>退勤日時</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="timeSheet" items="${timeSheets}" varStatus="status">
                    <%-- <fmt:parseDate value="${timeSheet.startTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="startDay" type="date" /> --%>
                    <%-- <fmt:parseDate value="${timeSheet.finishTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="finishDay" type="date" /> --%>

                    <tr class="row${status.count % 2}">
                        <td><c:out value="${timeSheet.employee.name}" /></td>

                        <td><c:out value="${timeSheet.startTime}" /></td>

                        <%---
                        <td class="start_time"><fmt:formatDate value='${startDay}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                        --%>


                        <td><c:out value="${timeSheet.finishTime}" /></td>

                        <%--
                        <td class="finish_time"><fmt:formatDate value='${finishDay}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                        --%>

                        <td>
                            <c:choose>
                                <c:when test="${timeSheet.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='?action=${actTim}&command=${commShow}&id=${timeSheet.id}' />">詳細を見る</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${timeSheets_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((timeSheets_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actTim}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actTim}&command=${commNew}' />">タイムシート　入力</a></p>

    </c:param>
</c:import>