$(function(){
    $("#cancleBtn").click(function(){
        var selectedBooks = [];
        $(".bookCheckBox:checked").each(function () {
            selectedBooks.push($(this).val());
        });

        var booksString = selectedBooks.join(',');
        $.ajax({
                    url: "/purchase/refund",
                    type: "POST",
                    data:{
                        "books": booksString,
                    },
                    success:function(response)
                    {
                        location.reload(true);
                    }
                });



    })




})