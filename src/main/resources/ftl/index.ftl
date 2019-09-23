<!DOCTYPE html>
<html >
<#include "base/baseStyle.ftl">
<body>
<H1>Hello,World!</H1>
<div>
    <a href="/export" target="_blank">EasyExcel导出测试</a>
</div>
<div style="position: relative;">
    <input style="width: 68px; position: absolute;opacity: 0;" id="file" type="file" name="file">
    <button >导入测试</button>
</div>


<#include "base/baseJs.ftl">
<script src="/assets/jqueryFileUpload-9.10.0/vendor/jquery.ui.widget.js?1"></script>
<script src="/assets/jqueryFileUpload-9.10.0/jquery.fileupload.js?1"></script>
<script>
    $(function () {
        layui.use('upload', function(){
            var upload = layui.upload;

            //执行上传
            var uploadInst = upload.render({
                elem: '#file' //绑定元素
                ,url: '/importExcel' //上传接口
                ,method: 'POST'
                ,accept: 'file'
                ,size: 50
                ,before: function(obj){
                    layer.load();
                }
                ,done: function(res){//上传完毕回调
                    layer.closeAll('loading');
                    layer.msg(res.message);
                }
                ,error: function(){//请求异常回调
                    layer.closeAll('loading');
                    layer.msg('网络异常，请稍后重试！');
                }
            });
        });
    })



    // $('#importExcel').fileupload({
    //     url: "/importExcel",
    //     dataType: 'json',
    //     success: function (data) {
    //         layer.msg(data.message);
    //     }
    // });

    // var formData = new FormData();
    // formData.append("file", $('#file')[0].files[0]);
    // $.ajax({
    //     url: "/importExcel",
    //     data: formData,
    //     cache: false,
    //     contentType: false,
    //     processData: false,
    //     type: 'POST',
    //     success: function (result) {
    //         layer.msg(data.message);
    //     }
    // });
</script>
</body>
</html>