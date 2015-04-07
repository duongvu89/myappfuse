<%--
  Created by IntelliJ IDEA.
  User: ndvu
  Date: 4/6/2015
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>

<head>
  <title><fmt:message key="salaryRecordList.title"/></title>
  <meta name="menu" content="PersonMenu"/>
</head>

<h2><fmt:message key="salaryRecordList.heading"/></h2>

<div id="actions" class="btn-group">
  <a href='<c:url value="/personform"/>' class="btn btn-primary">
    <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
  <a href='<c:url value="/home"/>' class="btn btn-default"><i class="icon-ok"></i> <fmt:message key="button.done"/></a>
</div>

<display:table name="salaryRecordList" class="table table-condensed table-striped table-hover" requestURI=""
               id="salaryRecordList" export="true" pagesize="25">
  <display:column property="startDate" sortable="true" titleKey="Start Date"/>
  <display:column property="endDate" sortable="true" titleKey="End Date"/>
  <display:column property="company" sortable="true" titleKey="Company"/>
  <display:column property="salary" sortable="true" titleKey="Salary"/>
  <display:column property="tax" sortable="true" titleKey="Tax"/>

  <display:setProperty name="paging.banner.item_name">banner 1</display:setProperty>
  <display:setProperty name="paging.banner.items_name">banner 2</display:setProperty>
</display:table>