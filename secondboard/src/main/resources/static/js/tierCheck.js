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

const tiercheckModal = (checkmodal) => {
    const tier = $("#tier"+checkmodal).text()
    console.log(tier)

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
}

//
// function tiercheckModal() {
//     let tiercheck = $("#tier1").text()
//     let tiercheck2 = $("#tier2").text()
//
//     console.log(tiercheck)
//     console.log(tiercheck2)
//
//     if( tiercheck == "IRON") {
//         $(".profile").attr("src", "/images/iron.png")
//     } else if (tiercheck == "BRONZE") {
//         $(".profile").attr("src", "/images/bronze.png")
//     } else if (tiercheck == "SILVER") {
//         $(".profile").attr("src", "/images/silver.png")
//     } else if (tiercheck == "GOLD") {
//         $(".profile").attr("src", "/images/gold.png")
//     } else if (tiercheck == "PLATINUM") {
//         $(".profile").attr("src", "/images/platinum.png")
//     } else if (tiercheck == "DIAMOND") {
//         $(".profile").attr("src", "/images/diamond.png")
//     } else if (tiercheck == "MASTER") {
//         $(".profile").attr("src", "/images/master.png")
//     } else if (tiercheck == "GRANDMASTER") {
//         $(".profile").attr("src", "/images/grandmaster.png")
//     } else if (tiercheck == "CHALLENGER") {
//         $(".profile").attr("src", "/images/challenger.png")
//     }
//
//     if( tiercheck2 == "IRON") {
//         $(".profile").attr("src", "/images/iron.png")
//     } else if (tiercheck2 == "BRONZE") {
//         $(".profile").attr("src", "/images/bronze.png")
//     } else if (tiercheck2 == "SILVER") {
//         $(".profile").attr("src", "/images/silver.png")
//     } else if (tiercheck2 == "GOLD") {
//         $(".profile").attr("src", "/images/gold.png")
//     } else if (tiercheck2 == "PLATINUM") {
//         $(".profile").attr("src", "/images/platinum.png")
//     } else if (tiercheck2 == "DIAMOND") {
//         $(".profile").attr("src", "/images/diamond.png")
//     } else if (tiercheck2 == "MASTER") {
//         $(".profile").attr("src", "/images/master.png")
//     } else if (tiercheck2 == "GRANDMASTER") {
//         $(".profile").attr("src", "/images/grandmaster.png")
//     } else if (tiercheck2 == "CHALLENGER") {
//         $(".profile").attr("src", "/images/challenger.png")
//     }
// }
