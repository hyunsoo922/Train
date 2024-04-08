$(function(){
    $(".cancelBtn").click(function(){
        var book = $(this).val();
        console.log(book)

        if(confirm("정말 환불하시겠습니까? ★사용하신 마일리지는 환불되지 않습니다.★"))
        {
            $.ajax({
                        url: "/purchase/refund",
                        type: "POST",
                        data:{
                            "book": book,
                        },
                        success:function(response)
                        {
                            location.reload(true);

                        }
                    });
        }
    })
})
 function receipt() {
        window.location.href = "/student/purchase/receipt";
    }