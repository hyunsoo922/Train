$(function(){
    $("#buyBtn").click(function () {
        console.log("Button clicked");

        var selectedBooks = [];
        $(".bookCheckBox:checked").each(function () {
            selectedBooks.push($(this).val());
        });

        // 배열을 문자열로 변환
        var booksString = selectedBooks.join(',');

        console.log(typeof booksString);

        // hidden input 필드에 설정
        $("#books").val(booksString);


        // form 제출
        $("#bookFrm").submit();
    });
});
