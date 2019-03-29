    var up="1",down="2";
    var el=$("#main"),el_back=$("#main_back");
    $("#main").on({
        mousedown: function (e) {
            el.addClass("del_d");
            el_back.addClass("del_d");
            var os = el.offset();
            var dx = e.pageX - os.left, dy = e.pageY - os.top;
            $(document).on('mousemove.drag', function (e) {
                    el.offset({top: e.pageY - dy, left: e.pageX - dx});
                    $('#main_back').offset({top: e.pageY - dy, left: e.pageX - dx});
                }
            );
        },
        mouseup: function (e) {
            $(document).off('mousemove.drag');
            el.removeClass("del_d");
            el_back.removeClass("del_d");
        }
    });
    $("#main_back").on({
        mousedown: function (e) {
            el.addClass("del_d");
            el_back.addClass("del_d");
            var os = el_back.offset();
            var dx = e.pageX - os.left, dy = e.pageY - os.top;
            $(document).on('mousemove.drag', function (e) {
                    el_back.offset({top: e.pageY - dy, left: e.pageX - dx});
                    $('#main').offset({top: e.pageY - dy, left: e.pageX - dx});
                }
            );
        },
        mouseup: function (e) {
            $(document).off('mousemove.drag');
            el_back.removeClass("del_d");
            el.removeClass("del_d");
        }
    });
    $('.exit').click(function () {
        $("#main").fadeOut();
        $("#main_back").fadeOut();
        $("#job_main").fadeOut();
    });

    $('#desktop>li').click(function () {
        $('#desktop>li').removeClass("choose");
        $(this).addClass("choose");
    })
    $('#desktop>li').dblclick(function () {
        $('#desktop>li').removeClass("choose");
        var name=this.getAttribute("name");
        $('#'+name).css("z-index",down);
        $("#"+name+"_back").css("z-index",up);
        $("#"+name).fadeIn();
        $("#"+name+"_back").fadeIn();
        $("#job_"+name).show().addClass("op2");
    });
    $(".setting").click(function () {
        $('#main').addClass("reverse");
        $('#main_back').addClass("reverse");
        var me=this;
        setTimeout(function () {
            var name=me.getAttribute("name");
            if(name=="main"){
                $('#main').removeClass("reverse").css("z-index",up);
                $("#main_back").removeClass("reverse").css("z-index",down);
            }else{
                $('#main').removeClass("reverse").css("z-index",down);
                $("#main_back").removeClass("reverse").css("z-index",up);
            }
        },2000);
    });

    $('.min').click(function () {
        $("#main").addClass("small");
        $("#main_back").addClass("small");
        $("#job_main").removeClass("op2");
    });
    $(".stop_p").mousedown(function (e) {
        e.stopPropagation();
    });
    $('#job_main').click(function () {
        $('#main').removeClass("small");
        $('#main_back').removeClass("small");
        $(this).addClass('op2');
    })
    $(".height1>span").click(function () {
       $(this).toggleClass("checked");
    })
    $('input[name=userName]').on("input propertychange",function () {
        $('#u_head').attr('src',"http://localhost:8080/getHead/"+this.value);
    })
    $('#u_head').on("error",function () {
        $('#u_head').attr('src','/images/head.jpg');
    })

    // $('input[name=userName]').change(function () {
    //     var userName=$(this).val();
    //     alert(userName);
    //     // $.ajax({
    //     //     url:"/getHead/"+userName,
    //     //     type:"get",
    //     //     success:function (data) {
    //     //
    //     //     }
    //     // })
    // })
