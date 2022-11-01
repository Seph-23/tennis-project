// active 옮기기
$('.category').click(function(){
    var clickedList = $(this).attr("id");
    const categories = Array.from(document.getElementsByClassName("category"));
    categories.forEach(category => category.classList.remove("active"));
    document.getElementById(clickedList).classList.add("active");
});

// category 별 목록 출력
$(document).ready(function(){
    $(".category").click(function(e) {
        e.preventDefault();
        var selectedCategory = $(this).attr("title");
        $(".card_area").fadeOut("fast");
        if (selectedCategory === "all") {
            $(".card_area").fadeIn("slow");
        }
        else {
            $(".card_area[title*="+selectedCategory+"]").fadeIn("slow");
        }
    });
});







