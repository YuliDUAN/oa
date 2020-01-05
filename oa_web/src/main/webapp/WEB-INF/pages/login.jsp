<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>欢迎使用报销单OA</title>
    <meta name="keywords" content="HTML5 Bootstrap 3 Admin Template UI Theme" />
    <meta name="description" content="AbsoluteAdmin - A Responsive HTML5 Admin UI Framework">
    <meta name="author" content="AbsoluteAdmin">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="assets/skin/default_skin/css/theme.css">
    <link rel="stylesheet" type="text/css" href="assets/admin-tools/admin-forms/css/admin-forms.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/avatars/favicon.ico">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/jquery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>

    <script type="text/javascript">
        $(function(){
            $("#addEmpBtn").click(function(){
                //弹出（新增）模态窗口
                $("#editEmpModal").modal({
                    backdrop:"static"
                });
            });
        });
    </script>

</head>
<body class="external-page external-alt sb-l-c sb-r-c">


<!-- 人脸登陆模态窗口 -->
<div id="editEmpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">人脸登陆</h4>
            </div>
            <div >
                <p align="center">
                    <button class="btn btn-primary" id="open">开启摄像头</button>
                    <button class="btn btn-default" id="close">关闭摄像头</button>
                    <button class="btn btn-primary" id="CatchCode">拍照</button>
                </p>
                <div align="center" style="float: left;">
                    <video id="video" width="400px" height="400px" autoplay></video>
                    <canvas  id="canvas" width="350" height="350"></canvas>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 人脸登陆模态窗口 -->

<div id="main" class="animated fadeIn">
    <section id="content_wrapper">
        <section id="content">
            <div class="admin-form theme-info mw500" id="login">
                <div class="content-header">
                    <h1> 报销单OA</h1>
                    <p class="lead">欢迎使用报销单管理系统</p>
                </div>
                <div class="panel mt30 mb25">
                    <div style="background: #fafafa;text-align:right">
                        <button style="background-image:url('/assets/img/avatars/faceLogin.png');width:64px;height:64px;border-style: none;"
                                id="addEmpBtn">
                        </button>
                    </div>
                    <form method="post" action="login" id="contact">
                        <div class="panel-body bg-light p25 pb15">
                            <div class="section">
                                <label for="sn" class="field-label text-muted fs18 mb10">工号</label>
                                <label for="sn" class="field prepend-icon">
                                    <input type="text" name="sn" id="sn" class="gui-input" placeholder="请输入工号...">
                                    <label for="sn" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="section">
                                <label for="password" class="field-label text-muted fs18 mb10">密码</label>
                                <label for="password" class="field prepend-icon">
                                    <input type="password" name="password" id="password" class="gui-input" placeholder="请输入密码...">
                                    <label for="password" class="field-icon">
                                        <i class="fa fa-lock"></i>
                                    </label>
                                </label>
                            </div>
                        </div>
                        <div class="panel-footer clearfix">
                            <button type="submit" class="button btn-primary mr10 pull-right">登陆</button>
                            <label class="switch ib switch-primary mt10">
                                <input type="checkbox" name="remember" id="remember" checked="true">
                                <label for="remember" data-on="是" data-off="否"></label>
                                <span>记住我</span>
                            </label>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </section>
</div>

<script type="text/javascript">
    var video;//视频流对象
    var context;//绘制对象
    var canvas;//画布对象
    $(function() {
        var flag = false;
        //开启摄像头
        $("#open").click(function() {
            //判断摄像头是否打开
            if (!flag) {
                //调用摄像头初始化
                open();
                flag = true;
            }
        });
        //关闭摄像头
        $("#close").click(function() {
            //判断摄像头是否打开
            if (flag) {
                video.srcObject.getTracks()[0].stop();
                flag = false;
            }
        });
        //拍照
        $("#CatchCode").click(function() {
            if (flag) {
                context.drawImage(video, 0, 0, 330, 250);
                CatchCode();
            } else
                alert("请先开启摄像头!");
        });
    });
    //将当前图像传输到后台
    function CatchCode() {
        //获取图像
        var img = getBase64();
        //Ajax将Base64字符串传输到后台处理
        $.ajax({
            type : "POST",
            url : "FaceLoginServlet",
            data : {
                img : img
            },
            dataType : "JSON",
            success : function(data) {
                //返回的结果
                //取出对比结果的返回分数，如果分数90以上就判断识别成功了
                if(parseInt(data.result.user_list[0].score) > 90) {
                    //关闭摄像头
                    video.srcObject.getTracks()[0].stop();
                    //提醒用户识别成功
                    //alert("验证成功!");
                    //验证成功跳转页面
                    //window.location.href="self.jsp";

                    //异步实现自登陆
                    $.ajax({
                        url:"/faceLogin/"+parseInt(data.result.user_list[0].user_id),
                        type:"get",
                        success:function (result) {
                            if(result=="SUC"){
                                //关闭模态窗
                                $("#addEmpModal").modal("hide");
                                location.href="/self";
                            }else {
                                alert("数据库中没有您的账号！");
                            }
                        }
                    })
                }else {
                    alert("人脸库中没有您的信息！");
                }
            },
            error : function(q, w, e) {
                alert(q + w + e);
            }
        });
    };
    //开启摄像头
    function open() {
        //获取摄像头对象
        canvas = document.getElementById("canvas");
        context = canvas.getContext("2d");
        //获取视频流
        video = document.getElementById("video");
        var videoObj = {
            "video" : true
        }, errBack = function(error) {
            console.log("Video capture error: ", error.code);
        };
        context.drawImage(video, 0, 0, 330, 250);
        //初始化摄像头参数
        if (navigator.getUserMedia || navigator.webkitGetUserMedia
            || navigator.mozGetUserMedia) {
            navigator.getUserMedia = navigator.getUserMedia
                || navigator.webkitGetUserMedia
                || navigator.mozGetUserMedia;
            navigator.getUserMedia(videoObj, function(stream) {
                video.srcObject = stream;
                video.play();
            }, errBack);
        }
    }
    //将摄像头拍取的图片转换为Base64格式字符串
    function getBase64() {
        //获取当前图像并转换为Base64的字符串
        var imgSrc = canvas.toDataURL("image/png");
        //截取字符串
        return imgSrc.substring(22);
    };
</script>

<script src="vendor/jquery/jquery-1.11.1.min.js"></script>
<script src="vendor/jquery/jquery_ui/jquery-ui.min.js"></script>
<script src="assets/js/utility/utility.js"></script>
<script src="assets/js/demo/demo.js"></script>
<script src="assets/js/main.js"></script>
</body>
</html>
