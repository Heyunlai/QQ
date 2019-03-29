$(function () {
    $("#face").click(function () {
        $('#faceFile').click();
    });
    $('#faceFile').change(function () {
        var face=this.files[0];
        if(face!=null){
            //base65编码解析
            var reader=new FileReader();
            reader.readAsDataURL(face);//解析需要时间
            reader.onload=function (e) {
                var data=e.target.result;
                $('#face').attr('src',data);
            };
        }
    });
})