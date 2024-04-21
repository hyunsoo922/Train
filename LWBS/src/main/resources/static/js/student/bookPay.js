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
function formatAuthors() {
    var elements = document.querySelectorAll(".author"); // 모든 author 클래스 요소 선택
    elements.forEach(function(element) { // 각 요소에 대해 처리
        var authors = element.innerText.split("^"); // ^을 기준으로 저자들을 분리
        if (authors.length > 1) { // 저자가 여러 명인 경우
            var additionalAuthorsCount = authors.length - 1; // 추가 저자 수 계산
            element.innerText = authors[0] + " 외 " + additionalAuthorsCount + "명"; // 형식에 맞게 변경
        }
    });
}
document.addEventListener('DOMContentLoaded', function() {
    // 처리할 요소들을 선택합니다.
    var mileageElements = document.querySelectorAll('.myMileage, .earnMileage');

    // 각 요소를 처리합니다.
    mileageElements.forEach(function(element) {
        var mileageText = element.textContent;
        var mileageValue = parseFloat(mileageText);
        var formattedMileage = mileageValue.toLocaleString();
        element.textContent = formattedMileage;
    });
});