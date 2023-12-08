$(function(){

    $("#purchaseBtn").click(function(){
        var item = $("#item").val();
        var totalPrice = $("#totalPrice").val();
        var totalCnt = $("#totalCnt").val();

        $.ajax({
                    url: "/purchase/payment",
                    type: "GET",
                    data:{
                        "item": item,
                        "totalPrice": totalPrice,
                        "totalCnt": totalCnt,
                    },
                    success:function(response)
                    {
                        location.href = response.next_redirect_pc_url;
                    }
                });
    });

});