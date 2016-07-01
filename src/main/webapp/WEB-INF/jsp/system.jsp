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
    <title>设置</title>

    <script>
        $(function (){
            $("#systemNav").addClass("active");

            getTrainRecordAjax($("#trainSelect option:selected").val());
            if ($("#predictSelect option:selected").val()!=null){
                getPredictRecordAjax($("#predictSelect option:selected").val())
            }
        });
    </script>
    <script>
        function trainAjax() {
            $("#trainForm").ajaxSubmit({
                success:function (data) {
                    if (data.msg){
                        alert(data.msg);
                    }
                    getTrainRecordAjax($("#trainSelect option:selected").val());
                }
            });
        }
        function predictAjax() {
            $("#predictForm").ajaxSubmit({
                success:function (data) {
                    $("#predictBtn").attr("disabled", true);

                    getPredictRecordAjax($("#predictSelect option:selected").val())
                }
            });
        }

        function getTrainRecordAjax(value) {
            $.post(
                    "${ctx}/getTrainRecord",
                    {model:value},
                    function (data) {

                        if (data.state == 0){
                            $("#trainState").html('<label class="alert alert-success">训练完成</label>');
                            $("#trainBtn").attr("disabled", false);
                        }else if (data.state == 1){
                            $("#trainState").html('<label class="alert alert-info">正在训练</label>');
                            $("#trainBtn").attr("disabled", true);
                        }else if(data.state == 2){
                            $("#trainState").html('<label class="alert alert-warning">训练出错</label>');
                            $("#trainBtn").attr("disabled", false);
                        }else {
                            $("#trainState").html('<label class="alert alert-warning">未训练过</label>');
                            $("#trainBtn").attr("disabled", false);
                        }
                    },
                    "json"
            )
        }
        function getPredictRecordAjax(value) {
            $.post(
                    "${ctx}/getPredictRecord",
                    {model:value, predictMonth:$("#predictMonth").val()},
                    function (data) {

                        if (data.state == 0){
                            $("#predictState").html('<label class="alert alert-success">预测完成</label>');
                            $("#predictBtn").attr("disabled", true);
                        }else if (data.state == 1){
                            $("#predictState").html('<label class="alert alert-info">正在预测</label>');
                            $("#predictBtn").attr("disabled", true);
                        }else {
                            $("#predictState").html('<label class="alert alert-warning">未预测过</label>');
                            $("#predictBtn").attr("disabled", false);
                        }
                    },
                    "json"
            )
        }
    </script>
</head>
<body>
<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                模型训练
            </div>
            <div class="panel-body">
                <form id="trainForm" action="${ctx}/train" method="post">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="row">
                                <div class="checkbox">
                                    <c:forEach items="${data2TrainPath}" var="item">
                                        <label>
                                            <input type="checkbox" name="months" value="${item}"> ${item} &nbsp;&nbsp;
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-3">
                                    <label>模型选择:&nbsp;&nbsp;</label>
                                </div>
                                <div class="col-lg-3">
                                    <select id="trainSelect" name="model" onchange="getTrainRecordAjax(this.value)">
                                        <option value="linear" selected>linear</option>
                                        <option value="rmlp">rmlp</option>
                                        <option value="cmlp">cmlp</option>
                                        <option value="cnn">cnn</option>
                                        <option value="rnn">rnn</option>
                                    </select>
                                </div>
                                <div class="col-lg-3">
                                    <div id="trainState" >

                                    </div>
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
    <div class="col-lg-6">
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
                                    预测时间
                                </div>
                                <div class="col-lg-3">
                                    <input type="hidden" id="predictMonth" value="${predictMonth}">
                                    ${predictMonth}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-3">
                                    数据
                                </div>
                                <div class="col-lg-3">
                                    <input type="hidden" value="${data2PredictPath}" name="months">
                                    <c:if test="${empty data2PredictPath}">
                                        <label class="alert alert-error">预测所需数据不存在!</label>
                                    </c:if>
                                    ${data2PredictPath}
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-lg-3">
                                    使用模型
                                </div>
                                <div class="col-lg-3">
                                    <select id="predictSelect" name="model" onchange="getPredictRecordAjax(this.value)">
                                        <c:forEach items="${trainRecordsFinished}" var="item">
                                            <option value="${item.model}">${item.model}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-lg-3">
                                    <div id="predictState" >

                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <c:choose>
                                <c:when test="${empty data2PredictPath || empty trainRecordsFinished}">
                                    <button disabled id="predictBtn" type="button" onclick="predictAjax();" class="btn btn-primary col-center-block">预测</button>
                                </c:when>
                                <c:otherwise>
                                    <button id="predictBtn" type="button" onclick="predictAjax();" class="btn btn-primary col-center-block">预测</button>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                系统设置
            </div>
            <div class="panel-body">
                <%--<form id="systemForm" class="form-horizontal" action="${ctx}/system/setProps" method="post">--%>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="form-group">
                                <label class="col-md-5 control-label">DATA_TO_TRAIN_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['DATA_TO_TRAIN_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="form-group">
                                <label class="col-md-5 control-label">DATA_TO_PREDICT_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['DATA_TO_PREDICT_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">MODEL_SAVE_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['MODEL_SAVE_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">FEATURE_SINGLE_FILE_COUNT&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['FEATURE_SINGLE_FILE_COUNT']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">TRAIN_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['TRAIN_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">TEST_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['TEST_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">TMP_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['TMP_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">PREDICT_DIR&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" value="${configProps.props['PREDICT_DIR']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">PY_INTERFACE&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="pyInterface" class="form-control" value="${configProps.props['PY_INTERFACE']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">trainingEpochs&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="trainingEpochs" class="form-control" value="${configProps.props['trainingEpochs']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">batchSize&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="batchSize" class="form-control" value="${configProps.props['batchSize']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">displayStep&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="displayStep" class="form-control" value="${configProps.props['displayStep']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">saveStep&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="saveStep" class="form-control" value="${configProps.props['saveStep']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">learningRate&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="learningRate" class="form-control" value="${configProps.props['learningRate']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">numSteps&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="numSteps" class="form-control" value="${configProps.props['numSteps']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">featureSize&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="featureSize" class="form-control" value="${configProps.props['featureSize']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">outputSize&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="outputSize" class="form-control" value="${configProps.props['outputSize']}" readonly>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class=" form-group">
                                <label class="col-md-5 control-label">perm&nbsp;&nbsp;&nbsp;</label>
                                <div class="col-md-7">
                                    <input type="text" name="perm" class="form-control" value="${configProps.props['perm']}" readonly>
                                </div>
                            </div>
                        </li>
                    </ul>
                <%--</form>--%>
            </div>
        </div>
    </div>
</div>
</body>
</html>

