let items = document.querySelectorAll('#dateCarousel .carousel-item')

items.forEach((el) => {
    const minPerSlide = 6
    let next = el.nextElementSibling
    for (let i=1; i<minPerSlide; i++) {
        if (!next) {
            // wrap carousel by using first child
            next = items[0]
        }
        let cloneChild = next.cloneNode(true)
        el.appendChild(cloneChild.children[0])
        next = next.nextElementSibling
    }
})

// $(document).ready(function () {
//     $(".carousel-inner div:first-child").addClass("active");
//
//     const week = ['일', '월', '화', '수', '목', '금', '토'];
//
//     let day = $(".hidden_btn").text()
//
//     console.log(day)
//
//     const dayOfWeek = week[new Date(day).getDay()];
//
//     console.log(dayOfWeek)
//
//     let elementById = document.getElementById("dayOfWeek");
//
//     elementById.innerText = dayOfWeek
//
// });

