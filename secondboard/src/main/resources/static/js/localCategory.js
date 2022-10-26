$(document).ready(function () {
    $(".category").click(function () {
        const card = document.getElementsByClassName("card_area");
        const selectedCategory = $(this).attr("title");

        Array.from(document.getElementsByClassName("category")).forEach(category => category.classList.remove("active"));
        document.getElementById($(this).attr("id")).classList.add("active");

        $(card).fadeOut("fast");

        if (selectedCategory === $("#list_all").attr("title")) {
            $(card).fadeIn("fast");
        } else {
            $(".card_area[title=" + selectedCategory + "]").fadeIn("fast");
        }
    });
});








