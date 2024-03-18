$(function(){
    $("#cancleBtn").click(function(){
        var selectedBooks = [];
        $(".bookCheckBox:checked").each(function () {
            selectedBooks.push($(this).val());
        });

        if(confirm("정말 환불하시겠습니까? ★사용하신 마일리지는 환불되지 않습니다.★"))
        {
            var books = selectedBooks.join(',');
            $.ajax({
                        url: "/purchase/refund",
                        type: "POST",
                        data:{
                            "books": books,
                        },
                        success:function(response)
                        {
                            location.reload(true);

                        }
                    });
        }


    })




})