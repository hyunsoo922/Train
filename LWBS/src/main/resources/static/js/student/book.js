$(function () {
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
});
