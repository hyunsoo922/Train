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