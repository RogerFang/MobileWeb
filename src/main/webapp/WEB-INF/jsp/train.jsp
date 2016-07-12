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
    <title>训练</title>

    <script>
        $(function (){
            $("#trainNav").addClass("active");

            initAjax($("#trainSelect option:selected").val());
        });
    </script>
    <script>
        function trainAjax() {
            $("#trainForm").ajaxSubmit({
                success:function (data) {
                    if (data.msg){
                        alert(data.msg);
                    }else {
                        alert("正在进行模型训练!")
                        initAjax($("#trainSelect option:selected").val());
                    }
                }
            });
        }

        function initAjax(value) {
            getTrainDataAjax(value);
            getTrainRecordAjax(value);
        }

        function getTrainRecordAjax(value) {
            $.post(
                    "${ctx}/train/getTrainRecord",
                    {model:value},
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
                                    "<td>" + item.trainMonthDataName + "</td>" +
                                    "<td>" + item.modelPath + "</td>" +
                                    "<td>" + item.trainPrecision + "</td>" +
                                    "<td>" + state + "</td>" +
                                    "<td>" + item.formatUpdateTime + "</td>" +
                                    "</tr>");
                        });
                    },
                    "json"
            );
        }
        function getTrainDataAjax(value) {
            $.post(
                    "${ctx}/train/getTrainData",
                    {model:value},
                    function (data) {
                        $("#trainData").html("");
                        $.each(data, function(key, value) {
                            $("#trainData").append(
                                    "<label>" +
                                    "<input type='checkbox' name='months' value='"+ value +"'> "+ key +" &nbsp;&nbsp;" +
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
                模型训练
            </div>
            <div class="panel-body">
                <form id="trainForm" action="${ctx}/train" method="post">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-3">
                                    <label>模型选择:&nbsp;&nbsp;</label>
                                </div>
                                <div class="col-lg-3">
                                    <select id="trainSelect" name="model" onchange="initAjax(this.value)">
                                        <option value="linear" selected>linear</option>
                                        <option value="rmlp">rmlp</option>
                                        <option value="cmlp">cmlp</option>
                                        <option value="cnn">cnn</option>
                                        <option value="rnn">rnn</option>
                                    </select>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="checkbox" id="trainData">

                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-center-block">
                                    <button id="trainBtn" type="button" class="btn btn-primary" onclick="trainAjax();">训练</button>
                                </div>
                            </div>
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
                模型训练记录
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>id</th>
                        <th>训练数据</th>
                        <th>模型位置</th>
                        <th>训练准确率</th>
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

