$(function(){

    $("#purchaseBtn").click(function(){
        var item = $("#item").val();
        var totalPrice = $("#totalPrice").val();
        var totalCnt = $("#totalCnt").val();
        console.log("item"+item);
        console.log("totalPrice"+totalPrice);
        console.log("totalCnt"+totalCnt);
        $.ajax({
                    url: "/purchase/payment",
                    type: "POST",
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