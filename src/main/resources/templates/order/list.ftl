<html>
    <#include "../common/header.ftl">
    <body>
    <div id="wrapper" class="toggled">
         <#--边栏-->
        <#include "../common/nav.ftl">


        <#--主要内容content-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <#--展示-->
                    <div class="col-md-12 column">
                        <table class="table table-condensed table-bordered">
                            <thead>
                            <tr>
                                <th>订单Id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatesEnum().message}</td>
                                    <#--                            <td>${orderDTO.payStatus}</td>-->
                                    <td>${orderDTO.getPayStatesEnum().message}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td>
                                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                                    </td>
                                    <td>
                                        <#if orderDTO.getOrderStatesEnum().message == "新订单">
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                            <#--如果现在处于第一页，上一页不能点-->
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${currentSize}">上一页</a></li>
                            </#if>
                            <#list 1..orderDTOPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index} </a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${currentSize}">${index} </a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${currentSize}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>

<#--弹窗-->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    你有新的订单
                </div>
                <div class="modal-footer">
                    <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                </div>
            </div>
        </div>
    </div>

    <#--播放音乐-->
    <audio id="notice" loop="loop" >
        <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
    </audio>


    <#--jquery-->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <#--bootstrap-->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <#---->
    <script>
        var webSocket = null;
        if ('WebSocket' in window){
            webSocket = new WebSocket('ws://yuhe.nat300.top/sell/webSocket')
        }else {
            alert("该浏览器不支持webSocket")
        }
        webSocket.onopen = function (event) {
            console.log('建立链接');
        }
        webSocket.onclose = function(event){
            console.log('链接关闭')
        }
        webSocket.onmessage = function (event) {//消息来的事件
            console.log('收到消息' + event.data);
            //弹窗提醒
            $('#myModal').modal('show');
            //播放音乐
            document.getElementById('notice').play();
        }
        webSocket.onerror = function (event) {//发生错误的时候
            alert('webSocket通信发生错误！')
        }
        window.onbeforeunload = function () {
            webSocket.close();
        }

    </script>

    </body>
</html>





