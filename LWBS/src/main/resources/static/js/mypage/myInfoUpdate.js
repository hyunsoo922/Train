function submitForm() {
    document.getElementById("loadingMessage").style.display = "block";
    document.body.style.pointerEvents = "none";
    // 사용자가 입력한 ID와 PW 가져오기
    var lmsId = document.getElementById("studentId").value;
    var lmsPw = document.getElementById("studentPw").value;
    var frm = document.getElementById("verification");
    console.log(lmsId, lmsPw)
    // AJAX를 통해 서버로 데이터 전송
    $.ajax({
        type: "GET",
        url: "/verification", // 서버의 엔드포인트 경로
        data: {
            id: lmsId,
            pw: lmsPw
        },
        success: function (result) {
            console.log(result);
            // 서버로부터의 응답을 처리하는 코드
            if (result) {
                alert("LMS 계정 정보가 변경되었습니다.");
                document.getElementById("loadingMessage").style.display = "none";
                document.body.style.pointerEvents = "auto";
                frm.submit();
            } else {
                alert("LMS계정 확인을 해주세요.");
                document.getElementById("loadingMessage").style.display = "none";
                document.body.style.pointerEvents = "auto";
            }
        },
        error: function () {
            alert("서버 오류 발생!");
            document.getElementById("loadingMessage").style.display = "none";
            document.body.style.pointerEvents = "auto";
        }
    });
}
function reload() {
    window.location.href = '/home/student';
}

window.onload = function() {
    var buttons = document.querySelectorAll('.side_drop_down_menu_panel_button');
    var homeButton = null;

    buttons.forEach(function(button) {
        var text = button.querySelector('.side_drop_down_menu_text');
        var icon = button.querySelector('.side_drop_down_menu_icon img');

        if (text.textContent.trim() === '내 정보 수정') {
            homeButton = button;
            button.style.backgroundColor = 'rgb(0, 54, 121)';
            text.style.color = '#ffffff';
            icon.style.filter = 'brightness(0) invert(1)';
        }

        button.addEventListener('click', function() {
            buttons.forEach(function(btn) {
                btn.style.backgroundColor = '';
                btn.querySelector('.side_drop_down_menu_text').style.color = '';
                btn.querySelector('.side_drop_down_menu_icon img').style.filter = '';
            });

            this.style.backgroundColor = 'rgb(0, 54, 121)';

            this.classList.add('selected');

            this.querySelector('.side_drop_down_menu_text').style.color = '#ffffff';

            this.querySelector('.side_drop_down_menu_icon img').style.filter = 'brightness(0) invert(1)';
        });
    });
};

