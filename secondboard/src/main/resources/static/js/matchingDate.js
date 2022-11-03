$(document).ready(function () {
    var now_utc = Date.now()
    var timeOff = new Date().getTimezoneOffset() * 60000;
    var today = new Date(now_utc - timeOff).toISOString().split("T")[0];
    // document.getElementById("matchingDate").setAttribute("min", today);
    $("#matchingDate").attr({"min" : today });
});


$('#matchingStartTime').timepicker({
    timeFormat: 'HH:mm',
    interval: 10,
    minTime: '00:00',
    maxTime: '23:50',
    dynamic: false,
    dropdown: true,
    scrollbar: true,
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
});

$("#matchingEndTime").mouseover(function(){
    // 시작 시간 선택 후 종료시간 최대 +2시간 설정
    var time = $('#matchingStartTime').val();
    var getTime = time.split(":"); //split time by colon
    var hours = parseInt(getTime[0])+2; //add two hours
    //set new time
    var newTime = hours+":"+getTime[1];
    //set time picker
    console.log(newTime);
    $("#matchingEndTime").timepicker('option','maxTime', newTime);
})

$('#matchingEndTime').timepicker({
    timeFormat: 'HH:mm',
    interval: 10,
    dynamic: false,
    dropdown: true,
    scrollbar: true,
});


