<html>
<hrad>
    <meta charset="utf-8">
    <title>成功提示</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</hrad>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    成功!
                </h4> <strong>${msg!""}</strong><a href="${url}" class="alert-link">3s后自动跳转</a>
                <#-- ${msg!""}的意思是，msg默认值为空  -->
            </div>
        </div>
    </div>
</div>
</body>
<script>
    setTimeout('location.href="${url}"',3000)//3秒跳转
</script>


</html>