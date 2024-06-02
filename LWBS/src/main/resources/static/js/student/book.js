$(function () {
    $("#buyBtn").click(function () {
        console.log("버튼이 눌림");
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
});

function showPopup(element) {
    var cartId = element.parentNode.querySelector('.cartId').innerText;
    var popup = document.getElementById("popup");
    var overlay = document.getElementById("overlay");
    var cartIdDisplay = document.getElementById("cartIdDisplay");
    popup.style.display = "block";
    overlay.style.display = "block";
    // cartId 값을 설정하여 표시
    cartIdDisplay.innerText = cartId;
}

function closePopup() {
    var popup = document.getElementById("popup");
    var overlay = document.getElementById("overlay");
    popup.style.display = "none";
    overlay.style.display = "none";
}

function confirmDelete() {
    var cartId = document.querySelector('.cartIdDisplay').innerText;
    removeCartItem(cartId); // 삭제 함수 호출
    closePopup();
    localStorage.removeItem('input_' + cartId);
    localStorage.removeItem('total_' + cartId);
}

function removeCartItem(cartId) {
    console.log("삭제 진입");
    var queryString = "?cartId=" + encodeURIComponent(cartId);
    window.location.href = "/student/purchase/delete" + queryString;
}

document.addEventListener('DOMContentLoaded', function() {
    // 모든 total-price 요소를 가져옵니다.
    var totalPriceElements = document.querySelectorAll('.total-price');

    // 각 totalPrice 요소에 초기값을 설정합니다.
    totalPriceElements.forEach(function(totalPriceElement) {
        // 가격을 가져와서 초기값으로 설정합니다.
        var price = parseFloat(totalPriceElement.previousElementSibling.textContent);
        // 쉼표로 구분된 형식으로 포맷합니다.
        var formattedPrice = price.toLocaleString();
        totalPriceElement.textContent = formattedPrice + '원';
    });
});

document.addEventListener('DOMContentLoaded', function() {
    var plusButtons = document.querySelectorAll('.spinner .plus');
    var minusButtons = document.querySelectorAll('.spinner .minus');
    var inputs = document.querySelectorAll('.spinner input[type="number"]');
    var priceElements = document.querySelectorAll('.price .val_target');
    var totalPriceElements = document.querySelectorAll('.total-price');
    var sumPriceElement = document.querySelector('.sumPrice');
    var cartIds = document.querySelectorAll('.cartId'); // 모든 cartId 요소 가져오기
    var checkboxes = document.querySelectorAll('.bookCheckBox');

    // 페이지가 로드될 때, 초기 합계를 계산합니다.
    calculateSumPrice();
    // 페이지가 로드될 때, localStorage에 저장된 값을 읽어와서 각 totalPrice를 업데이트합니다.
    updateTotalPricesFromLocalStorage();
    // 페이지가 로드될 때, localStorage에 저장된 값을 읽어와서 총 합계를 업데이트합니다.

    // 페이지가 로드될 때, 모든 체크 박스를 자동으로 체크하고 sumPrice를 계산합니다.
    checkboxes.forEach(function(checkbox) {
        checkbox.checked = true; // 모든 체크 박스를 체크함
        calculateCheckboxTotalPrice(checkbox); // 해당 체크 박스의 total-price를 sumPrice에 더함
    });

    plusButtons.forEach(function(plusButton, index) {
        plusButton.addEventListener('click', function() {
            inputs[index].value = parseInt(inputs[index].value) + 1;
            updateTotalPrice(index);
            var inputCartId = cartIds[index].innerText; // 현재 인덱스에 해당하는 input_cartId 가져오기
            var totalCartId = 'total_' + inputCartId; // total_cartId 생성
            localStorage.setItem('input_' + inputCartId, inputs[index].value); // input_cartId를 키로 사용하여 localStorage에 저장
            localStorage.setItem('total_' + inputCartId, totalPriceElements[index].innerText); // total_cartId를 키로 사용하여 localStorage에 저장
            calculateSumPrice(); // 합계 재계산
        });
    });

    minusButtons.forEach(function(minusButton, index) {
        minusButton.addEventListener('click', function() {
            if (parseInt(inputs[index].value) > 1) {
                inputs[index].value = parseInt(inputs[index].value) - 1;
                updateTotalPrice(index);
                var inputCartId = cartIds[index].innerText; // 현재 인덱스에 해당하는 input_cartId 가져오기
                var totalCartId = 'total_' + inputCartId; // total_cartId 생성
                localStorage.setItem('input_' + inputCartId, inputs[index].value); // input_cartId를 키로 사용하여 localStorage에 저장
                localStorage.setItem('total_' + inputCartId, totalPriceElements[index].innerText); // total_cartId를 키로 사용하여 localStorage에 저장
                calculateSumPrice(); // 합계 재계산
            }
        });
    });

    function updateTotalPrice(index) {
        // 각 cart 객체의 수량이 변경될 때 가격(total-price)를 업데이트
        var price = parseFloat(priceElements[index].textContent.replace('원', ''));
        var quantity = parseInt(inputs[index].value);
        var totalPrice = price * quantity;
        totalPriceElements[index].textContent = totalPrice.toLocaleString() + '원'; // 형식화된 문자열로 설정
    }

    function calculateSumPrice() {
        // 체크박스가 체크된 객체의 total-price를 가져와 summary 후 sumPrice에 전달(출력)
        var sum = 0;
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                var totalPriceElement = checkbox.closest('tr').querySelector('.total-price');
                var price = parseFloat(totalPriceElement.textContent.replace('원', '').replace(/,/g, ''));
                sum += price;
            }
        });
        sumPriceElement.innerText = sum.toLocaleString() + "원"; // 형식화된 문자열로 출력
    }

    function updateTotalPricesFromLocalStorage() {
        // total-price를 배열로 만들어 가져오고, 그 배열을 순회하며 브라우저 로컬 저장소에 카트 객체에 해당하는 캐시를 생성
        totalPriceElements.forEach(function(element, index) {
            var inputCartId = cartIds[index].innerText; // 현재 인덱스에 해당하는 input_cartId 가져오기
            var inputValue = localStorage.getItem('input_' + inputCartId); // localStorage에서 해당 input_cartId에 대한 저장된 값 가져오기
            if (inputValue !== null) {
                // 저장된 값이 있다면 totalPrice를 업데이트합니다.
                inputs[index].value = inputValue;
                updateTotalPrice(index);
            }
        });
    }

    // 체크 박스의 상태가 변경될 때마다 해당 객체의 total-price를 sumPrice에 더하거나 뺍니다.
    checkboxes.forEach(function(checkbox) {
        console.log(sumPriceElement);
        checkbox.addEventListener('change', function() {
            calculateCheckboxTotalPrice(checkbox);
        });
    });

    // 체크 박스의 total-price를 sumPrice에 더하거나 뺍니다.
    function calculateCheckboxTotalPrice(checkbox) {
        var totalPriceElement = checkbox.closest('tr').querySelector('.total-price');
        var price = parseFloat(totalPriceElement.textContent.replace('원', '').replace(/,/g, ''));
        if (checkbox.checked) {
            console.log("체크 박스 선택됨에 따라 값 더함");
            // 체크 박스가 체크되면 total-price를 sumPrice에 더합니다.
            sumPriceElement.innerText = (parseFloat(sumPriceElement.innerText.replace('원', '').replace(/,/g, '')) + price).toLocaleString() + '원';
        } else {
            console.log("체크 박스 해제됨에 따라 값 차감");
            // 체크 박스가 체크 해제되면 sumPrice에서 total-price를 차감합니다.
            sumPriceElement.innerText = (parseFloat(sumPriceElement.innerText.replace('원', '').replace(/,/g, '')) - price).toLocaleString() + '원';
        }
    }
});
document.addEventListener('DOMContentLoaded', function() {
    var inputMileagePoint = document.getElementById('mileagePoint'); // input 요소를 참조합니다.
    var sumPointElement = document.querySelector('.myMileage'); // .myMileage 클래스를 가진 요소를 참조합니다.
    var sumPointText = sumPointElement.innerText; // sumPoint 요소의 텍스트를 가져옵니다.
    var sumPointValue = parseInt(sumPointText);
    var sumPriceElement = document.querySelector('.sumPrice').innerText; // .sumPrice 클래스를 가진 요소의 텍스트를 가져옵니다.
    var calPriceElement = document.querySelector('.calPrice'); // .calPrice 클래스를 가진 요소를 참조합니다.
    // inputMileagePoint의 값이 변경될 때마다 이벤트 리스너를 추가합니다.
    inputMileagePoint.addEventListener('input', function() {
        // inputMileagePoint의 값을 가져옵니다.
        var mileageValue = parseFloat(inputMileagePoint.value.replace(/,/g, ''));

        if (mileageValue > sumPointValue) {
            // 입력란의 값을 최대값으로 설정합니다.
            inputMileagePoint.value = sumPointValue.toLocaleString().replace(/,/g, '');
            // 마일리지 값도 최대값으로 설정합니다.
            mileageValue = sumPointValue;
        }

        // 상품 금액을 가져와서 마일리지를 차감합니다.
        var originalPrice = parseFloat(sumPriceElement.replace(/[^\d.-]/g, ''));
        var discountedPrice = originalPrice - mileageValue;

        // 만약 discountedPrice가 NaN이라면 0으로 설정합니다.
        if (isNaN(discountedPrice)) {
            calPriceElement.innerText = sumPriceElement;
        } else {
            // 마일리지 차감 후의 결제 예정 금액을 업데이트합니다.
            calPriceElement.innerText = discountedPrice.toLocaleString() + '원';
        }
    });
});
document.addEventListener('DOMContentLoaded', function() {
    var sumPriceElement = document.querySelector('.sumPrice').innerText; // .sumPrice 클래스를 가진 요소의 텍스트를 가져옵니다.
    var calPriceElement = document.querySelector('.calPrice'); // .calPrice 클래스를 가진 요소를 참조합니다.

    // 페이지가 로드될 때, sumPrice의 값과 calPrice의 값이 동일하도록 설정합니다.
    calPriceElement.innerText = sumPriceElement;
});
document.addEventListener('DOMContentLoaded', function() {
    var sumPriceElement = document.querySelector('.sumPrice');
    var calPriceElement = document.querySelector('.calPrice');
    var earnMileageElement = document.querySelector('.earnMileage');

    // 페이지가 로드될 때, sumPrice, calPrice, earnMileage의 값이 초기화됩니다.
    updatePrices();

    // sumPrice의 값이 변경될 때마다 calPrice와 earnMileage의 값도 업데이트됩니다.
    sumPriceElement.addEventListener('DOMSubtreeModified', updatePrices);

    function updatePrices() {
        var sumPriceText = sumPriceElement.innerText;
        var sumPrice = parseFloat(sumPriceText.replace(/[^\d.-]/g, ''));

        // calPrice 업데이트
        calPriceElement.innerText = sumPrice.toLocaleString() + '원';

        // earnMileage 업데이트
        var earnMileage = sumPrice / 10;
        earnMileageElement.innerText = earnMileage.toFixed(0);
    }
});
document.addEventListener('DOMContentLoaded', function() {
    var sumPriceElement = document.querySelector('.sumPrice');
    var earnMileageElement = document.querySelector('.earnMileage');

    // 페이지가 로드될 때, earnMileage의 값이 초기화됩니다.
    updateEarnMileage();

    function updateEarnMileage() {
        var sumPrice = parseFloat(sumPriceElement.innerText.replace(/[^\d.-]/g, ''));
        var earnMileage = sumPrice / 10; // sumPrice를 10으로 나눈 값

        earnMileageElement.innerText = earnMileage.toFixed(0); // 쉼표 없이 정수값으로 표시
    }
});

function prepareFormData() {
    var form = document.getElementById("bookFrm");
    var checkedCheckboxes = document.querySelectorAll('.bookCheckBox:checked');

    // 선택된 상품의 정보를 담을 배열
    var selectedBooks = [];

    // 선택된 각 상품에 대해 정보를 수집하여 배열에 추가
    checkedCheckboxes.forEach(function(checkbox) {
        var parentRow = checkbox.closest('tr');
        var bookId = checkbox.value;
        var quantity = parentRow.querySelector('.spinner input[type="number"]').value;

        // 선택된 상품의 정보를 id를 quantity만큼 반복하여 배열에 추가
        for (var i = 0; i < quantity; i++) {
            selectedBooks.push(bookId);
        }
    });

    // 선택된 상품 정보 배열을 쉼표로 구분된 문자열로 변환하여 form 데이터에 추가
    var booksInput = document.createElement("input");
    booksInput.setAttribute("type", "hidden");
    booksInput.setAttribute("name", "books");
    booksInput.setAttribute("value", selectedBooks.join());

    // form에 추가한 input 요소를 추가
    form.appendChild(booksInput);

    // form을 전송
    form.submit();
}
function receipt() {
    window.location.href = "/student/purchase/receipt";
}
function truncateText() {
    var maxWidth = 1280; // 최소 가로 길이
    if (window.innerWidth <= maxWidth) { // 현재 가로 길이가 최소 가로 길이보다 큰 경우에만 작동
        var maxLength = 10; // 최대 글자 수
        var elements = document.querySelectorAll(".bookName"); // 모든 bookName 클래스 요소 선택
        elements.forEach(function(element) { // 각 요소에 대해 처리
            var text = element.innerText;
            if (text.length > maxLength) {
                text = text.substring(0, maxLength) + "...";
            }
            element.innerText = text;
        });
    }
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
function formatPrice() {
    var priceElements = document.querySelectorAll(".prodPrice"); // 모든 price 클래스 요소 선택
    priceElements.forEach(function(element) { // 각 요소에 대해 처리
        var price = parseInt(element.innerText); // 텍스트를 숫자로 변환
        element.innerText = new Intl.NumberFormat('ko-KR').format(price) + "원"; // 한국 통화 형식으로 변환하여 설정
    });
}
document.addEventListener("DOMContentLoaded", function() {
    truncateText();
    formatAuthors();
    formatPrice();
});
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