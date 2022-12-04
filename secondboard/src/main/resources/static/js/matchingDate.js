$(document).ready(function () {
    var now_utc = Date.now() + 86400000;
    var timeOff = new Date().getTimezoneOffset() * 60000;
    // var timeOff = new Date().getTimezoneOffset() * 120000;
    var today = new Date(now_utc - timeOff).toISOString().split("T")[0];
    // document.getElementById("matchingDate").setAttribute("min", today);
    $("#matchingDate").attr({"min" : today });
    $("#single_btn").prop("checked", true);
    if ($("#teamASize").val() === "2") {
        $("#double_btnB").prop("checked", true);
    } else {
        $("#double_btnA").prop("checked", true);
    }
});


$('#matchingStartTime').timepicker({
    timeFormat: 'HH:mm',
    interval: 10,
    minTime: '02:00',
    maxTime: '21:50',
    dynamic: false,
    dropdown: true,
    scrollbar: true,
    zindex: 9999
});

$('#matchingEndTime').mouseover(function () {
    // 시작 시간 선택 후 종료시간 최소 +10분 설정
    var stime = $('#matchingStartTime').val();
    var sgetTime = stime.split(":"); //split time by colon
    var sminutes = parseInt(sgetTime[1])+10; //add two hours
    var sNewTime = sgetTime[0] +":"+sminutes;
    console.log(sNewTime);
    $('#matchingEndTime').timepicker('option', 'minTime', sNewTime);
    $('#matchingEndTime').timepicker('option', 'startTime', sNewTime);

    // 시작 시간 선택 후 종료시간 최대 +2시간 설정
    var time = $('#matchingStartTime').val();
    var getTime = time.split(":"); //split time by colon
    var hours = parseInt(getTime[0])+2; //add two hours
    //set new time
    var newTime = hours+":"+getTime[1];
    //set time picker
    console.log(newTime);
    $("#matchingEndTime").timepicker('option','maxTime', newTime);

    // 시작 시간 2시간 전 설정
    var beforeTwoHour = (parseInt(getTime[0])-2) +":"+getTime[1];
    beforeTwoHour = String(beforeTwoHour).padStart(5, "0");
    $("#beforeTwoHour").val(beforeTwoHour);

    // 시작 시간 1시간 전 설정
    var beforeHour = (parseInt(getTime[0])-1) +":"+getTime[1];
    beforeHour = String(beforeHour).padStart(5, "0");
    $("#beforeHour").val(beforeHour);
});


$('#matchingEndTime').timepicker({
    timeFormat: 'HH:mm',
    interval: 10,
    dynamic: false,
    dropdown: true,
    scrollbar: true,
    zindex: 9999
});

$(".readonly").keydown(function(e){
    e.preventDefault();
});








