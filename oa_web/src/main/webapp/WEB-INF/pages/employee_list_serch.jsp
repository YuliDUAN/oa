<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>
<script type="text/javascript">
    function checkData() {
        var q = document.getElementById("q").value.trim();
        if (q==null||q == ""){
            alert("请输入要查询的关键字！")
            return false;
        } else {
            return true;
        }
    }
</script>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 搜索&nbsp;<font color="red">${q}</font>&nbsp; 的结果</h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1200 center-block">
            <label>共得到&nbsp;<font color="red">${resultTotal}</font>&nbsp;条记录</label>
        </div>
        <div class="admin-form theme-primary mw1200 center-block" style="padding-bottom: 175px;">
            <div class="panel  heading-border">
                <div class="panel-menu">
                    <div class="row">
                        <div class="hidden-xs hidden-sm col-md-3">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-refresh"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-plus" onclick="javascript:window.location.href='/employee/to_add';"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-9 text-right">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-chevron-left"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body pn">
                    <table id="message-table" class="table admin-form theme-warning tc-checkbox-1">
                        <thead>
                        <tr class="">
                            <th class="text-center hidden-xs">Select</th>
                            <th class="hidden-xs">工号</th>
                            <th class="hidden-xs">姓名</th>
                            <th class="hidden-xs">部门ID</th>
                            <th class="hidden-xs">职务</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${resultTotal==0}">
                                <td class="hidden-xs">未查询到结果</td>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${result}" var="emp">
                                    <tr class="message-unread">
                                        <td class="hidden-xs">
                                            <label class="option block mn">
                                                <input type="checkbox" name="mobileos" value="FR">
                                                <span class="checkbox mn"></span>
                                            </label>
                                        </td>
                                        <td>${emp.sn}</td>
                                        <td>${emp.name}</td>
                                        <td class="text-center fw600">${emp.departmentSn}</td>
                                        <td class="hidden-xs">
                                            <span class="badge badge-warning mr10 fs11">${emp.post}</span>
                                        </td>
                                        <td>
                                            <a href="#">编辑</a>
                                            <a href="#">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>