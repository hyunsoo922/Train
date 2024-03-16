$(function(){
    $("#cancleBtn").click(function(){
        var selectedBooks = [];
        $(".bookCheckBox:checked").each(function () {
            selectedBooks.push($(this).val());
        });

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



    })




})