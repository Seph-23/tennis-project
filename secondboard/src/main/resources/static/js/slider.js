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
