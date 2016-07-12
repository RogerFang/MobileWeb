<%--
  Created by IntelliJ IDEA.
  User: Roger
  Date: 2016/5/19
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default">
    <title>效果展示</title>

    <script>
        $(function (){
            $("#displayNav").addClass("active");
        });

        function searchAjax() {
            $("#searchForm").ajaxSubmit({
                success:function (data) {
                    $("tbody").html("");
                    $.each(data, function(i, item) {
                        var calState;
                        if (item.calState == 0){
                            calState = "完成";
                        }else if (item.calState == 1){
                            calState = "进行";
                        }else if (item.calState == 2){
                            calState = "异常";
                        }
                        $("tbody").append(
                                "<tr>" +
                                "<td>" + (i+1) + "</td>" +
                                "<td>" + item.id + "</td>" +
                                "<td>" + item.model + "</td>" +
                                "<td>" + item.predictMonth + "</td>" +
                                "<td>" + item.predictMonthData + "</td>" +
                                "<td>" + item.trainRecord['modelPath'] + "</td>" +
                                "<td>" + item.trainRecord['trainPrecision'] + "</td>" +
                                "<td>" + item.predictPrecision + "</td>" +
                                "<td>" + calState + "</td>" +
                                "<td>" + item.formatUpdateTime + "</td>" +
                                "</tr>");
                    });
                }
            });
        }
    </script>
</head>
<body>
<%--<div class="row">
    <div class="col-lg-6 col-lg-offset-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                模型训练效果
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>模型</th>
                        <th>模型路径</th>
                        <th>训练准确率</th>
                        <th>更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${model}" var="item" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${item.key}</td>
                            <td>${item.value['modelPath']}</td>
                            <td>${item.value['trainPrecision']}</td>
                            <td>${item.value['updateTime']}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>--%>

<div class="row">
    <div class="col-lg-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                模型训练效果搜索
            </div>
            <div class="panel-body">
                <form id="searchForm" action="${ctx}/display/search" method="post">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-1">
                                    <label>模型</label>
                                </div>
                                <div class="col-lg-2">
                                    <div class="radio">
                                        <c:forEach items="${model}" var="item">
                                            <label><input type="radio" name="trainRecordId" value="${item.value['id']}">${item.key}&nbsp;&nbsp;</label>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-2">
                                    <label>校验数据</label>
                                </div>
                                <div class="col-lg-4">
                                    <div class="radio">
                                        <c:forEach items="${data}" var="item">
                                            <label><input type="radio" name="checkMonthData" value="${item.value}">${item.key}&nbsp;&nbsp;</label>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <button id="searchBtn" type="button" onclick="searchAjax();" class="btn btn-primary col-center-block">搜索</button>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                模型训练效果
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>predict_id</th>
                        <th>模型</th>
                        <th>预测月份</th>
                        <th>预测所用数据</th>
                        <th>模型路径</th>
                        <th>训练准确率</th>
                        <th>预测准确率</th>
                        <th>计算状态</th>
                        <th>更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%--<c:forEach items="${model}" var="item" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${item.key}</td>
                            <td>${item.value['modelPath']}</td>
                            <td>${item.value['trainPrecision']}</td>
                            <td>${item.value['updateTime']}</td>
                        </tr>
                    </c:forEach>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
