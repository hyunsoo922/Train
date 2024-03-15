$(function(){
    $("#cancleBtn").click(function(){
        var selectedBooks = [];
        $(".bookCheckBox:checked").each(function () {
            selectedBooks.push($(this).val());
        });
        console.log("api진입전")
        var books = selectedBooks.join(',');
        $.ajax({
                    url: "/purchase/refund",
                    type: "POST",
                    data:{
                        "books": books,
                    },
                    success:function(response)
                    {
//                        location.reload(true);
                        console.log("api통신완료")
                    }
                });



    })




})