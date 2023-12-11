$(function(){
        var result = confirm("회원 탈퇴하시겠습니까?");
        if(result)
        {
            $("#deleteFrm").submit();
        }

});