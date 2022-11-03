$(document).ready(function () {
    $(".courtType").click(function () {
        const matching = document.getElementsByClassName("matching_area");
        const selectedCategory = $(this).attr("title");

        Array.from(document.getElementsByClassName("category")).forEach(category => category.classList.remove("active"));
        document.getElementById($(this).attr("id")).classList.add("active");

        card.hide();

        if (selectedCategory === $("#list_all").attr("title")) {
            card.show();
        } else {
            $(".card_area[title=" + selectedCategory + "]").show();
        }
    });
});








