var userName=$("#userName").val();
var url ='ws://'+window.location.host+'/chat?userName='+userName;
var ws = new WebSocket(url);//建立ws长连接

ws.onmessage=function (e) {
    var messageJson=JSON.parse(e.data);
    var str;
    if(messageJson.type==-1)str=onlineMessage(messageJson);
    if(messageJson.type==0)str=systemMessage(messageJson);
    if(messageJson.type==1)str=userMessage(messageJson);
    if(messageJson.type==2)str=douMessage(messageJson);
    $("#record").append(str);
    var scrollHeight=$("#record").prop("scrollHeight");
    $("#record").scrollTop(scrollHeight);
}
function systemMessage(messageJson) {
    var strArray=new Array();
    strArray.push("<div class='system_message'>");
    strArray.push(messageJson.message);
    strArray.push("</div>");
    return strArray.join("");
}
function onlineMessage(messageJson) {
    $('#online_people').empty();
    var strArray=new Array();
    var userNames=JSON.parse(messageJson.message);
    for(var i=0;i<userNames.length;i++){
        strArray.push("<li><span><img src=");
        strArray.push("http://localhost:8080/getHead/"+userNames[i]);
        strArray.push("></span><span>");
        strArray.push(userNames[i]);
        strArray.push("</span></li>");
    }
    $('#online_people').append(strArray.join(""));
}
function userMessage(messageJson) {
    if(messageJson.message!=null&&messageJson.message.indexOf('\0')>=0){
        // var chats=$(".chat");
        var m=messageJson.message.substring(1);

        $(".chat").each(function () {
            if($(this).attr("cid")==m){
                $(this).parent().parent().remove();
            }
        });
        return;
        // console.log(chats[0].getAttribute("cid"));
        // for(var i=0;i<chats.length;i++){
        //     if(chats[i].getAttribute("cid")==m){
        //         if(userName==messageJson.userName){
        //             $(chats[i]).parent().parent().remove();
        //             return;
        //         }else{
        //             return;
        //         }
        //     }
        // }
    }
    var strArray=new Array();
    strArray.push("<div><img src='http://localhost:8080/getHead/"+messageJson.userName+"'");
    strArray.push(" class='chatFace'>");
    strArray.push('<div class="float_left">');
    strArray.push("<span>"+messageJson.userName+"</span>");
    strArray.push("<span class='chat block' cid='"+messageJson.cid+"'>"+messageJson.message+"</span>");
    strArray.push("</div><div style='clear:both;'></div></div>");
    return strArray.join("");
}
function douMessage(messageJson) {

}

$('#sendBtn').click(function () {
    var message=$.trim($("#textInput").html());
    if(!message)return;
    ws.send(message);
    $("#textInput").empty().focus();

})

var first = document.getElementById("title");
var app =document.getElementById("win");
var x,y;//用户偏移量
first.ondragstart =  function (ev) {
    x = ev.offsetX;
    y = ev.offsetY;//获取偏差值
}
first.ondrag = function (ev) {
    //鼠标释放时，鼠标会归为 0 0
    if (ev.pageX == 0 && ev.pageY == 0) return;
    app.style.left = ev.pageX -x +"px";
    app.style.top = ev.pageY -y +"px";
}
$(document).keydown(function (e) {
    switch(e.keyCode){
        case 16:$(document).keydown(function (e1) {
            switch(e1.keyCode){
                case 83:$('#sendBtn').click();break;
            }
        });break;
    }
})
//表情包
$("#textInput").emoji({
    button:'#emojiBtn',
    showTab:true,
    animation:"fade",
    icons:[{
        name:"OO表情",
        path:"/emoji/dist/img/qq/",
        maxNum:92,
        file:".gif"
    },{
        name:"贴吧表情",
        path:"/emoji/dist/img/tieba/",
        maxNum:50,
        file:".jpg"
    },{
        name:"我的表情",
        path:"/emoji/dist/img/myimg/",
        maxNum:1,
        file:".jpg"
    }]
});
//鼠标右键撤回
document.oncontextmenu = function (e) {
    return false;
}
$(document).on("mousedown",function (e) {
    if(e.which!==3){
        $("#backup").hide();
        $("#record").css("overflow-y","auto");
    }
})
$("#record").on("mousedown",".chat",function (e) {
    var messageName=$(this).prev().html();
    if(e.which==3 && userName==messageName){
        var x=e.pageX;
        var y=e.pageY;
        $("#cid").val($(this).attr("cid"));
        $("#backup").css({left:x,top:y}).show();
        $("#record").css("overflow-y","hidden");
        // unScroll($("#record"));
    }
})
//禁止滚动
function unScroll(el) {
    var top=el.scrollTop;
    el.on("scroll.unable",function (e) {
         el.scrollTop(top);
    })
}
$('#backup').on("mousedown",function () {
    var cid=$("#cid").val();
    var command="\0"+cid;
    ws.send(command);
    // $("#record").css("overflow-y","auto");
})
