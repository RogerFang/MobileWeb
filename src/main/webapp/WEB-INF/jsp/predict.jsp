<%--
  Created by IntelliJ IDEA.
  User: Roger
  Date: 2016/7/1
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default">
    <title>预测</title>

    <script>
        $(function (){
            $("#predictNav").addClass("active");

            initAjax($("#predictSelect option:selected").val());
        });
    </script>
    <script>
        function initAjax(value) {
            getPredictDataAjax(value);
            getPredictRecordAjax(value)
        }

        function predictAjax() {
            $("#predictForm").ajaxSubmit({
                success:function (data) {
                    alert(data.msg);
                    initAjax($("#predictSelect option:selected").val());
                }
            });
        }

        function getPredictRecordAjax(value) {
            $.post(
                    "${ctx}/predict/getPredictRecord",
                    {trainRecordId:value},
                    function (data) {
                        $("tbody").html("");
                        $.each(data, function(i, item) {
                            var state;
                            var stateClass;
                            if (item.state == 0){
                                state = "完成";
                                stateClass = "alert-success";
                            }else if (item.state == 1){
                                state = "进行";
                                stateClass = "alert-info";
                            }else if(item.state == 2){
                                state = "异常";
                                stateClass = "alert-danger";
                            }else if (item.state == -1){
                                state = "中止";
                                stateClass = "alert-warning";
                            }
                            $("tbody").append(
                                    "<tr class='"+ stateClass +"'>" +
                                    "<td>" + (i+1) + "</td>" +
                                    "<td>" + item.id + "</td>" +
                                    "<td>" + item.predictMonthDataName + "</td>" +
                                    "<td>" + item.trainRecord['modelPath'] + "</td>" +
                                    "<td>" + item.resultPath + "</td>" +
                                    "<td>" + item.predictPrecision + "</td>" +
                                    "<td>" + state + "</td>" +
                                    "<td>" + item.formatUpdateTime + "</td>" +
                                    "</tr>");
                        });
                    },
                    "json"
            )
        }
        function getPredictDataAjax(value) {
            $.post(
                    "${ctx}/predict/getPredictData",
                    {trainRecordId:value},
                    function (data) {
                        $("#predictData").html("");
                        $.each(data, function(key, value) {
                            $("#predictData").append(
                                    "<label>" +
                                    "<input type='radio' name='predictMonthData' value='"+ value +"'> "+ key +" &nbsp;&nbsp;" +
                                    "</label>");
                        });
                    },
                    "json"
            );
        }
    </script>
</head>
<body>
<div class="row">
    <div class="col-lg-6 col-lg-offset-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                模型预测
            </div>
            <div class="panel-body">
                <form id="predictForm" action="${ctx}/predict" method="post">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-3">
                                    使用模型
                                </div>
                                <div class="col-lg-3">
                                    <select id="predictSelect" name="trainRecordId" onchange="initAjax(this.value)">
                                        <c:forEach items="${model}" var="item">
                                            <option value="${item.value['id']}">${item.key}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-3">
                                    预测数据
                                </div>
                                <div class="col-lg-3">
                                    <div class="radio" id="predictData">

                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <button id="predictBtn" type="button" onclick="predictAjax();" class="btn btn-primary col-center-block">预测</button>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                模型预测记录
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>id</th>
                        <th>预测数据</th>
                        <th>预测模型路径</th>
                        <th>预测结果路径</th>
                        <th>预测准确率</th>
                        <th>状态</th>
                        <th>更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>

