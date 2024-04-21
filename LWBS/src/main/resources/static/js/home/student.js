$(document).ready(function () {
    document.addEventListener("DOMContentLoaded", function () {
        var dropdownMenu = document.getElementById("dropdown-menu");
        dropdownMenu.addEventListener("click", function (event) {
            if (event.target.tagName === 'A') {
                var selectedDepartment = event.target.textContent.trim();
                filterByDepartment(selectedDepartment);
            }
        });

        var bookListElement = document.querySelector('.w-full.md\\:w-2\\/3');
        var categoriesElement = document.querySelector('.categories');
        categoriesElement.style.height = bookListElement.offsetHeight + 'px';

        var dropdownMenu = document.getElementById("dropdown-menu");
        dropdownMenu.parentElement.addEventListener("click", function () {
            dropdownMenu.classList.toggle("show");
        });
        window.addEventListener("click", function (event) {
            if (!dropdownMenu.parentElement.contains(event.target)) {
                dropdownMenu.classList.remove("show");
            }
        });
    });

    function handleDepartmentSelection(department) {
        filterByDepartment(department);
    }

    function filterByDepartment(department) {
        var bookItems = document.querySelectorAll('.p-4');
        bookItems.forEach(function (item) {
            var bookDepartment = item.querySelector('span:nth-child(5)').textContent.trim();
            if (department === '전체 교재' || bookDepartment === department) {
                item.style.display = '';
            } else {
                item.style.display = 'none';
            }
        });
    }
    function redirectToCart(element) {
        var bookID = element.parentNode.querySelector(".bookID").innerText;
        var queryString = "?bookID=" + encodeURIComponent(bookID);
        // /cart 엔드포인트로 리디렉션
        window.location.href = "/cart" + queryString;
    }

    var userIDElement = document.getElementById("userID");
    var userID = userIDElement.textContent.trim();

    function openPopup() {
        // 팝업과 배경 레이어 표시
        document.getElementById('popup').style.display = 'block';
        document.getElementById('overlay').style.display = 'block';
    }

    function closePopup() {
        // 팝업과 배경 레이어 숨김
        document.getElementById('popup').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }

    function checkPopupStatus() {
        // 페이지 로드 시 팝업 상태를 확인하여 필요에 따라 팝업을 열거나 닫습니다.
        if (getCookie("hidePopup") !== "true") {
            openPopup();
        }
    }

    function getCookie(name) {
        var cookieArr = document.cookie.split(";");
        for (var i = 0; i < cookieArr.length; i++) {
            var cookiePair = cookieArr[i].split("=");
            if (name === cookiePair[0].trim()) {
                return decodeURIComponent(cookiePair[1]);
            }
        }
        return null;
    }

    document.addEventListener("DOMContentLoaded", function () {
        // 이미지에 hover하면 덧씌울 이미지와 장바구니 추가 문자열을 보여줍니다.
        var bookImageContainers = document.querySelectorAll('.bookImageContainer');
        bookImageContainers.forEach(function (container) {
            var overlayImage = container.querySelector('.overlayImage');
            var addToCartText = container.querySelector('.addToCartText');
            container.addEventListener('mouseenter', function () {
                overlayImage.style.display = 'block';
                addToCartText.style.display = 'block';
            });
            container.addEventListener('mouseleave', function () {
                overlayImage.style.display = 'none';
                addToCartText.style.display = 'none';
            });
        });
    });

    function redirectToCart(element) {
        var bookID = element.parentNode.querySelector(".bookID").textContent;
        var queryString = "?bookID=" + encodeURIComponent(bookID);
        // /cart 엔드포인트로 리디렉션
        window.location.href = "/cart" + queryString;
    }

    function submitForm() {
        document.getElementById("dateForm").submit();
    }

    const formContainer = document.getElementById('formContainer');
    const receiveDaySpan = document.getElementById('receiveDay');
    const myinfoImg = document.getElementById('myinfoImg');

    myinfoImg.addEventListener('click', function() {
        if (formContainer.style.display === 'none') {
            formContainer.style.display = 'block';
            receiveDaySpan.style.display = 'none';
            myinfoImg.style.display = 'none';
        } else {
            formContainer.style.display = 'none';
            receiveDaySpan.style.display = 'block';
            myinfoImg.style.display = 'inline-block'; // 이미지를 다시 보이게 하려면 inline-block으로 설정해야 합니다.
        }
    });

    window.onload = function() {
        var receiveDayValue = document.getElementById('receiveDay').innerText;
        var myinfoImg = document.getElementById('myinfoImg');
        if (receiveDayValue === "구매하신 교재가 없습니다.") {
            myinfoImg.style.display = 'none';
        }
    };

    function webscraping() {
        // 추가된 alert 메시지
        alert("수강 교재 정보를 가져오겠습니다.\n웹페이지를 닫지 말고 완료 메시지를 기다려주세요.\n확인을 누르시면 시작합니다.");

        // AJAX 요청 보내기
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/webscraping/" + userID, true);
        document.body.style.pointerEvents = "none";
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                alert("수강 신청 정보 수집이 완료되었습니다!");
                document.body.style.pointerEvents = "auto";
                location.reload();
            }
        };
        xhr.send();
    }

// 필터링 함수
    function filterBooks() {
        var selectedDepartment = document.getElementById('department').value;
        var books = document.getElementsByClassName('book');

        // 전체를 선택했을 경우 모든 책을 보여줌
        if (selectedDepartment === 'all') {
            for (var i = 0; i < books.length; i++) {
                books[i].style.display = 'block';
            }
            return;
        }

        // 선택된 학과에 해당하는 책만 보여줌
        for (var i = 0; i < books.length; i++) {
            var bookDepartment = books[i].querySelector('strong:nth-of-type(2)').innerText;
            if (bookDepartment.trim() === selectedDepartment.trim()) {
                books[i].style.display = 'block';
            } else {
                books[i].style.display = 'none';
            }
        }
    }

    function viewAllBooks() {
        // enrollBooks를 display:none으로 변경
        document.getElementById('enrollBooks').style.display = 'none';
        // allBooks를 display:block으로 변경
        document.getElementById('allBooks').style.display = 'block';
        // info를 display:none으로 변경
        document.getElementById('student_info').style.display = 'none';
        // 공지를 display:none으로 변경
        document.getElementById('notification').style.display = 'none';

        // page1 클래스를 가진 요소들의 display를 none으로 변경
        var page1Elements = document.querySelectorAll('.page1');
        page1Elements.forEach(function(element) {
            element.style.display = 'block';
        });
    }

// 페이지가 로드될 때 실행되는 함수
    window.onload = function() {
        // 모든 버튼 요소를 선택합니다.
        var buttons = document.querySelectorAll('.side_drop_down_menu_panel_button');
        var homeButton = null; // 홈 버튼을 저장할 변수

        // 각 버튼에 초기 스타일을 적용합니다.
        buttons.forEach(function(button) {
            var text = button.querySelector('.side_drop_down_menu_text');
            var icon = button.querySelector('.side_drop_down_menu_icon img');

            // 홈 버튼일 경우 초기 스타일 적용
            if (text.textContent.trim() === '홈') {
                homeButton = button;
                button.style.backgroundColor = 'rgb(0, 54, 121)';
                text.style.color = '#ffffff';
                icon.style.filter = 'brightness(0) invert(1)';
            }

            // 클릭 이벤트를 추가합니다.
            button.addEventListener('click', function() {
                // 모든 버튼의 배경색, 텍스트 색상, 아이콘 필터를 초기 상태로 변경합니다.
                buttons.forEach(function(btn) {
                    btn.style.backgroundColor = '';
                    btn.querySelector('.side_drop_down_menu_text').style.color = '';
                    btn.querySelector('.side_drop_down_menu_icon img').style.filter = '';
                });

                // 현재 선택된 버튼의 배경색을 변경합니다.
                this.style.backgroundColor = 'rgb(0, 54, 121)';

                // 현재 선택된 버튼에 selected 클래스를 추가합니다.
                this.classList.add('selected');

                // 선택된 버튼의 텍스트 색상을 하얀색으로 변경합니다.
                this.querySelector('.side_drop_down_menu_text').style.color = '#ffffff';

                // 선택된 버튼의 이미지 필터를 적용합니다.
                this.querySelector('.side_drop_down_menu_icon img').style.filter = 'brightness(0) invert(1)';
            });
        });
    };

    function reload() {
        location.reload();
    }

    function myInfoUpdate() {
        var authorityElement = document.querySelector('.authority');
        console.log(authorityElement);
        if (authorityElement) {
            var authority = parseInt(authorityElement.textContent); // 요소의 내용을 정수형으로 변환하여 가져옴
            console.log(authority);
            // 내 정보 수정 페이지로 이동하는 로직 작성
            if (authority === 1) {
                window.location.href = "/mypage/myInfoUpdate/student";
            } else if (authority === 2) {
                window.location.href = "/mypage/myInfoUpdate/bookStore";
            }
        }
    }
    function mileage() {
        // "마일리지 내역 조회" 버튼 클릭 시 "/mypage/mileage"로 이동
        window.location.href = "/mypage/mileage/" + userID;
    }

    function receipt() {
        window.location.href = "/student/purchase/receipt";
    }
});

