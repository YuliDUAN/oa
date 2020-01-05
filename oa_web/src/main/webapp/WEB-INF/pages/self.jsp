<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/jquery/jquery-3.3.1.js"></script>
<jsp:include page="top.jsp"/>

<script type="text/javascript">
    $(function () {
        $("#getEmpBtn").click(function(){
            //先加载图片
            getImg();
            //弹出（新增）模态窗口
            $("#moreEmpModal").modal({
                backdrop:"static"
            });
        });
    });

    function getImg(){
           //生成二维码
            $.ajax({
                url:"/to_getCode",
                type:"get",
                success:function(result){
                    $("#img1").attr("src","../../Images/"+result+"");
                    }
            });
    }

</script>

<!-- 得到员工详情模态窗口 -->
<div id="moreEmpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">手机扫描获取详情</h4>
            </div>
            <div align="center" style="margin-bottom: 50px">
                <img id="img1" src="" style="height: 200px;width: 200px"/>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 得到员工详情模态窗口 -->

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div>
            <iframe style="float: right;" width="420" scrolling="no" height="45" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
        </div>
        <div class="content-header">
            <h2> 个人信息 </h2>
            <p class="lead"></p>
        </div>

        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">工号</div>
                        <div class="col-md-4">${sessionScope.employee.sn}</div>
                        <div class="col-md-2">姓名</div>
                        <div class="col-md-4">${sessionScope.employee.name}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">所属部门</div>
                        <div class="col-md-4">${sessionScope.employee.department.name}</div>
                        <div class="col-md-2">职务</div>
                        <div class="col-md-4">${sessionScope.employee.post}</div>
                    </div>
                    <div class="panel-footer text-right">
                        <button type="button" class="button" id="getEmpBtn"> 查看更多信息>>> </button>
                        <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>