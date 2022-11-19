var swiper = new Swiper(".mySwiper", {
    slidesPerView: 7,
    spaceBetween: 10,
    slidesPerGroup: 7,
    loop: false,
    loopFillGroupWithBlank: true,
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});

$(document).ready(function () {
    $(".hidden_btn[value=" + '토' + "]").css("color", "blue")
    $(".hidden_btn[value=" + '일' + "]").css("color", "red")
    const clickDate = $("#m-date").attr("value")
    $(".swiper-slide").removeClass("click-active")
    $("#click-" + clickDate).addClass("click-active")
    $("#click-" + clickDate).children("button").addClass("click-active")
    $("#click-" + clickDate).children("button").css("color", "white")
})