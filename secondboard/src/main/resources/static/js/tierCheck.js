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

$(document).ready(function () {
    const topRank = $("#topRank").text()

    if( topRank == "IRON") {
        $(".profile_first").attr("src", "/images/iron.png")
    } else if (topRank == "BRONZE") {
        $(".profile_first").attr("src", "/images/bronze.png")
    } else if (topRank == "SILVER") {
        $(".profile_first").attr("src", "/images/silver.png")
    } else if (topRank == "GOLD") {
        $(".profile_first").attr("src", "/images/gold.png")
    } else if (topRank == "PLATINUM") {
        $(".profile_first").attr("src", "/images/platinum.png")
    } else if (topRank == "DIAMOND") {
        $(".profile_first").attr("src", "/images/diamond.png")
    } else if (topRank == "MASTER") {
        $(".profile_first").attr("src", "/images/master.png")
    } else if (topRank == "GRANDMASTER") {
        $(".profile_first").attr("src", "/images/grandmaster.png")
    } else if (topRank == "CHALLENGER") {
        $(".profile_first").attr("src", "/images/challenger.png")
    }

    const secondRank = $("#secondRank").text()

    if( secondRank == "IRON") {
        $(".profile_second").attr("src", "/images/iron.png")
    } else if (secondRank == "BRONZE") {
        $(".profile_second").attr("src", "/images/bronze.png")
    } else if (secondRank == "SILVER") {
        $(".profile_second").attr("src", "/images/silver.png")
    } else if (secondRank == "GOLD") {
        $(".profile_second").attr("src", "/images/gold.png")
    } else if (secondRank == "PLATINUM") {
        $(".profile_second").attr("src", "/images/platinum.png")
    } else if (secondRank == "DIAMOND") {
        $(".profile_second").attr("src", "/images/diamond.png")
    } else if (secondRank == "MASTER") {
        $(".profile_second").attr("src", "/images/master.png")
    } else if (secondRank == "GRANDMASTER") {
        $(".profile_second").attr("src", "/images/grandmaster.png")
    } else if (secondRank == "CHALLENGER") {
        $(".profile_second").attr("src", "/images/challenger.png")
    }

    let thirdRank = $("#thirdRank").text()

    if( thirdRank == "IRON") {
        $(".profile_third").attr("src", "/images/iron.png")
    } else if (thirdRank == "BRONZE") {
        $(".profile_third").attr("src", "/images/bronze.png")
    } else if (thirdRank == "SILVER") {
        $(".profile_third").attr("src", "/images/silver.png")
    } else if (thirdRank == "GOLD") {
        $(".profile_third").attr("src", "/images/gold.png")
    } else if (thirdRank == "PLATINUM") {
        $(".profile_third").attr("src", "/images/platinum.png")
    } else if (thirdRank == "DIAMOND") {
        $(".profile_third").attr("src", "/images/diamond.png")
    } else if (thirdRank == "MASTER") {
        $(".profile_third").attr("src", "/images/master.png")
    } else if (thirdRank == "GRANDMASTER") {
        $(".profile_third").attr("src", "/images/grandmaster.png")
    } else if (thirdRank == "CHALLENGER") {
        $(".profile_third").attr("src", "/images/challenger.png")
    }
});


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
