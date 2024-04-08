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

document.addEventListener("DOMContentLoaded", function() {
    calculateSumPrice();
});

// 합계 가격 및 적립 마일리지 계산 함수
function calculateSumPrice() {
    var sum = 0;
    // 각 객체의 가격과 수량을 곱하여 합산
    document.querySelectorAll('.bookItem').forEach(function(element) {
        var price = parseFloat(element.querySelector('.bookPrice').textContent.replace(/[^\d.-]/g, ''));
        var quantity = parseInt(element.querySelector('.quantity').textContent);
        sum += price * quantity;
    });
    // 합계를 표시하는 요소에 결과 텍스트 설정
    var sumPriceElement = document.querySelector('.sumPrice');
    sumPriceElement.textContent = sum.toLocaleString() + '원';

    // 적립 마일리지 계산
    var earnMileageElement = document.querySelector('.earnMileage');
    var mileage = Math.floor(sum / 10); // 소수점 이하 버림
    earnMileageElement.textContent = mileage + 'P';
}