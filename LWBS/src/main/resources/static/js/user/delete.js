$(function(){
        var result = confirm("회원 탈퇴하시겠습니까?");
        console.log(result);
        if(result)
        {
            console.log("삭제합니다.")
            $("#deleteFrm").submit();
        }

});