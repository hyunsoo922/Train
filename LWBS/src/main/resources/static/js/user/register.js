$(function () {
    alert("등록되지 않은 회원입니다. 회원가입 페이지로 이동합니다.")

    $('input[name="kind"]').change(function () {

        var selectKind = $(this).val();

        $(".student-container, .bookStore-container").addClass('hidden');

        $("." + selectKind + "-container").removeClass('hidden');

        if (selectKind == "student") {
            $("#publisherId").val("");
            $(".container").css("height","480px");
            $("#lmsCh").removeClass('hidden');

        }
        else if (selectKind == "bookStore") {
            $("#LMSID").val("");
            $("#LMSPW").val("");
            $(".container").css("height","350px");
            $("#lmsCh").addClass('hidden');


        }
    });

    $("#regBtn").click(function () {
        const frm = $("#register");
        const kind = $("input[name='kind']:checked").val();
        const ver = $("#ver").val().trim();
        if (kind === "student") {
            const ID = $("#LMSID").val().trim();
            const PW = $("#LMSPW").val().trim();
            if (ID === "" || PW === "") {
                alert("아이디와 비밀번호를 입력해주세요.");
            }
            else {
                if(ver === "t")
                {
                    alert("회원가입이 완료되었습니다. 환영합니다.");
                    frm.submit();
                }
                else
                {
                    alert("LMS계정 확인을 해주세요.");
                }
            }
        }
        else {
            const publishID = $("#publisherId").val().trim();
            if (publishID === "") {
                alert("가맹점ID를 입력해주세요.")
            }
            else {
                    alert("회원가입이 완료되었습니다. 환영합니다.");
                    frm.submit();
            }
        }



    });

});