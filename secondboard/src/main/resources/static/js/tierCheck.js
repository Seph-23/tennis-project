let tier = $(".tier").text()

if( tier == "IRON") {
    $(".profile").attr("src", "/images/iron.png")
} else if (tier == "BRONZE") {
    $(".profile").attr("src", "/images/bronze.png")
} else if (tier == "SILVER") {
    $(".profile").attr("src", "/images/silver.png")
} else if (tier == "GOLD") {
    $(".profile").attr("src", "/images/gold.png")
} else if (tier == "PLATINUM") {
    $(".profile").attr("src", "/images/platinum.png")
} else if (tier == "DIAMOND") {
    $(".profile").attr("src", "/images/diamond.png")
} else if (tier == "MASTER") {
    $(".profile").attr("src", "/images/master.png")
} else if (tier == "GRANDMASTER") {
    $(".profile").attr("src", "/images/grandmaster.png")
} else if (tier == "CHALLENGER") {
    $(".profile").attr("src", "/images/challenger.png")
}

