// $(function () {
//     $("#buyBtn").click(function () {
//
//         var selectedBooks = [];
//         $(".bookCheckBox:checked").each(function () {
//             selectedBooks.push($(this).val());
//         });
//
//         // 배열을 문자열로 변환
//         var booksString = selectedBooks.join(',');
//
//         var mileage = $("#mileagePoint");
//
//         // hidden input 필드에 설정
//         $("#books").val(booksString);
//
//
//         // form 제출
//         if (mileage.val().trim() !== "") {
//             $("#bookFrm").submit();
//         }
//         else {
//             alert("사용하실 마일리지를 입력해주세요");
//             mileage.focus();
//         }
//     });
// });
$(document).ready(function () {
    $("#buyBtn").click(function () {

        var selectedBooks = [];
        $(".bookCheckBox:checked").each(function () {
            selectedBooks.push($(this).val());
        });

        // 배열을 문자열로 변환
        var booksString = selectedBooks.join(',');

        var mileage = $("#mileagePoint");

        // hidden input 필드에 설정
        $("#books").val(booksString);


        // form 제출
        if (mileage.val().trim() !== "") {
            $("#bookFrm").submit();
        }
        else {
            alert("사용하실 마일리지를 입력해주세요");
            mileage.focus();
        }
    });

    // 구매 버튼 초기 상태 : 비활성화
    $('#buyBtn').prop('disabled', true);

    // 체크 박스 및 receiveDay 변경 시 구매 버튼 활성화/비활성화
    $('.bookCheckBox, #receiveDay').change(function () {
        // 체크된 체크 박스의 개수가 0개 이상일 때
        var anyChecked = $('.bookCheckBox:checked').length > 0;
        // "수령 날짜 선택"이 아닐 때
        var receiveDaySelected = $('#receiveDay').val() !== "수령 날짜 선택";
        $('#buyBtn').prop('disabled', !(anyChecked && receiveDaySelected));
    });

    $('#buyBtn').click(function () {
        $('#bookFrm').submit();
    });
});

function removeCartItem(element) {
    var cartId = element.parentNode.querySelector('.cartId').innerText;
    var queryString = "?cartId=" + encodeURIComponent(cartId);
    window.location.href = "/student/purchase/delete" + queryString;
}