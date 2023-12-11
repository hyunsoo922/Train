$(function () {
    alert("등록되지 않은 회원입니다. 회원가입 페이지로 이동합니다.")

    $('input[name="kind"]').change(function () {

        var selectKind = $(this).val();

        $(".student-container, .bookStore-container").addClass('hidden');

        $("." + selectKind + "-container").removeClass('hidden');

        if(selectKind == "student")
        {
            $("#publisherId").val("");
        }
        else if(selectKind == "bookStore")
        {
            $("#LMSID").val("");
            $("#LMSPW").val("");
        }
    });
});