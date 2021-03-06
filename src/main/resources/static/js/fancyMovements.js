$("#menu").click(function () {
    var domain = window.location.origin;
    var domainHello = domain;
    var domainManage = domain;
    domainHello += "/frames/hello.html";
    domainManage += "/frames/summary.html";
    if ((document.getElementById("contentFrame").src !== domainHello && document.getElementById("contentFrame").src !== domainManage) && document.getElementById("sidebar").style.display === "none") {
        $("#content").animate({'width': '1130px'}, 400, function () {
            $("#sidebar").toggle("slide");
        });
    }
    else if ((document.getElementById("contentFrame").src === domainHello || document.getElementById("contentFrame").src === domainManage) && document.getElementById("sidebar").style.display !== "none") {
        $("#sidebar").toggle("slide", function () {
            $("#content").animate({'width': '1320px'}, 400);
        });
    }
});

$("#mapFold").click(function () {
    if (document.getElementById("topbar").style.display === "none") {
        $("iframe").animate({'min-height': '410px'}, 400);
        $("#content").animate({'min-height': '480px'}, 400);
        $("#sidebar").animate({'min-height': '480px'}, 400, function () {
            $("#topbar").slideToggle("slow");
        });
    }
    else {
        $("#topbar").slideToggle("slow", function () {
            $("#content").animate({'min-height': '640px'}, 400);
            $("#sidebar").animate({'min-height': '640px'}, 400);
            $("iframe").animate({'min-height': '570px'}, 400);
        });
    }
});
