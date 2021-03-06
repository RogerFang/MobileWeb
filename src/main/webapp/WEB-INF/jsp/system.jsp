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
    <title>系统参数</title>

    <script>
        $(function (){
            $("#systemNav").addClass("active");
        });
    </script>
</head>
<body>

<div class="row">
    <div class="col-lg-6 col-lg-offset-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                系统参数
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

