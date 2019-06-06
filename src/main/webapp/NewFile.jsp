<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="static/js/vendor/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
    function fasong(){
        var orderNo = $("#orderNo").val();
        var ipAddress = $("#ipAddress").val();
        $.ajax({
            url:'${ctx}/getPrintData',
            data:{OrderCode:orderNo,ip:ipAddress},
            success:function(data){
                $("#RequestData").val(data.RequestData)
                $("#DataSign").val(data.DataSign)
            }
        })
    }
    function getIp(){
        $.ajax({
            url:'${ctx}/getIpAddress',
            success:function(data){
                alert(data);
            }
        })
    }
</script>
<h1>测试打印页面</h1>
    <div id="head"></div>
    <div>
        <input type="text" id="orderNo">
        <input type="text" id="ipAddress">
        <input type="button" onclick="fasong()" value="发送" />
    </div>
    <div>
        <input type="button" onclick="getIp()" value="获取ip地址通过java"/>
    </div>
    <form id="form1" action="http://www.kdniao.com/External/PrintOrder.aspx" method="post" target="_self">
        <div style="">
            <div><input type="text" id="RequestData" name="RequestData" readonly="readonly"/>请求数据</div>
            <div><input type="text" id="DataSign" name="DataSign" readonly="readonly"/>签名</div>
            <div><input type="text" id="EBusinessID" name="EBusinessID" value="1267739"/>商户id</div>
            <div><input type="text" id="IsPreview" name="IsPreview" value="1"/><span style="color: red;">是否预览 0-不预览 1-预览</span></div>
            <div><input type="submit" id="tijiao" value="打印" /></div>
        </div>
    </form>

</body>
</html>