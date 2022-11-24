$(function () {
  fnInit();
});

function frm_check() {
  saveid();
}

function fnInit() {
  var cookieid = getCookie("saveid");
  console.log(cookieid);
  if (cookieid != "") {
    $("input:checkbox[id='saveId']").prop("checked", true);
    $('#login_id').val(cookieid);
  }
}

function setCookie(name, value, expiredays) {
  var todayDate = new Date();
  todayDate.setTime(todayDate.getTime() + 0);
  if (todayDate > expiredays) {
    document.cookie = name + "=" + escape(value) + "; path=/; expires=" + expiredays + "; domain=gogotennis.co.kr;"
  } else if (todayDate < expiredays) {
    todayDate.setDate(todayDate.getDate() + expiredays);
    document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString()
        + "; domain=gogotennis.co.kr;"
  }
  console.log(document.cookie);
}

function getCookie(Name) {
  var search = Name + "=";
  console.log("search : " + search);

  if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
    offset = document.cookie.indexOf(search);
    console.log("offset : " + offset);
    if (offset != -1) { // 쿠키가 존재하면
      offset += search.length;
      // set index of beginning of value
      end = document.cookie.indexOf(";", offset);
      console.log("end : " + end);
      // 쿠키 값의 마지막 위치 인덱스 번호 설정
      if (end == -1) {
        end = document.cookie.length;
      }
      console.log("end위치  : " + end);
      return unescape(document.cookie.substring(offset, end));
    }
  }
  return "";
}

function saveid() {
  var expdate = new Date();
  if ($("#saveId").is(":checked")) {
    expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);
    setCookie("saveid", $("#login_id").val(), expdate);
  } else {
    expdate.setTime(expdate.getTime() - 1000 * 3600 * 24 * 30);
    setCookie("saveid", $("#login_id").val(), expdate);
  }
}