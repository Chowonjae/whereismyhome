///////////////////////// select box 설정 (지역, 매매기간) /////////////////////////
let date = new Date();

window.onload = function () {
  let yearEl = document.querySelector("#year");
  let yearOpt = `<option value="">매매년도선택</option>`;
  let year = date.getFullYear();
  for (let i = year; i > year - 20; i--) {
    yearOpt += `<option value="${i}">${i}년</option>`;
  }
  yearEl.innerHTML = yearOpt;

  // 브라우저가 열리면 시도정보 얻기.
  sendRequest("sido", "*00000000");
};

document.querySelector("#year").addEventListener("change", function () {
  let month = date.getMonth() + 1;
  let monthEl = document.querySelector("#month");
  let monthOpt = `<option value="">매매월선택</option>`;
  let yearSel = document.querySelector("#year");
  let m = yearSel[yearSel.selectedIndex].value == date.getFullYear() ? month : 13;
  for (let i = 1; i < m; i++) {
    monthOpt += `<option value="${i < 10 ? "0" + i : i}">${i}월</option>`;
  }
  monthEl.innerHTML = monthOpt;
});

// https://juso.dev/docs/reg-code-api/
// let url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
// let regcode = "*00000000";
// 전국 특별/광역시, 도
// https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000

// 시도가 바뀌면 구군정보 얻기.
document.querySelector("#sido").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    let regcode = this[this.selectedIndex].value.substr(0, 2) + "*00000";
    sendRequest("gugun", regcode);
  } else {
    initOption("gugun");
    initOption("dong");
  }
});

// 구군이 바뀌면 동정보 얻기.
document.querySelector("#gugun").addEventListener("change", function () {
  console.log("change exists")
  if (this[this.selectedIndex].value) {
    let regcode = this[this.selectedIndex].value.substr(0, 5) + "*";
    sendRequest("dong", regcode);
  } else {
    initOption("dong");
  }
});

function sendRequest(selid, regcode) {
  const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
  let params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";
  fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => addOption(selid, data));
}

function addOption(selid, data) {
  let opt = ``;

  initOption(selid);
  switch (selid) {
    case "sido":
      opt += `<option value="">시도선택</option>`;
      data.regcodes.forEach(function (regcode) {
        opt += `
        <option value="${regcode.code}">${regcode.name}</option>
        `;
      });
      break;
    case "gugun":
      opt += `<option value="">구군선택</option>`;
      for (let i = 0; i < data.regcodes.length; i++) {
        if (i != data.regcodes.length - 1) {
          if (
            data.regcodes[i].name.split(" ")[1] == data.regcodes[i + 1].name.split(" ")[1] &&
            data.regcodes[i].name.split(" ").length !=
              data.regcodes[i + 1].name.split(" ").length
          ) {
            data.regcodes.splice(i, 1);
            i--;
          }
        }
      }
      let name = "";
      data.regcodes.forEach(function (regcode) {
        if (regcode.name.split(" ").length == 2) name = regcode.name.split(" ")[1];
        else name = regcode.name.split(" ")[1] + " " + regcode.name.split(" ")[2];
        opt += `
        <option value="${regcode.code}">${name}</option>
        `;
      });
      break;
    case "dong":
      opt += `<option value="">동선택</option>`;
      let idx = 2;
      data.regcodes.forEach(function (regcode) {
        if (regcode.name.split(" ").length != 3) idx = 3;
        opt += `
        <option value="${regcode.code}">${regcode.name.split(" ")[idx]}</option>
        `;
      });
  }
  document.querySelector(`#${selid}`).innerHTML = opt;

}

function initOption(selid) {
  let options = document.querySelector(`#${selid}`);
  options.length = 0;
  // let len = options.length;
  // for (let i = len - 1; i >= 0; i--) {
  //   options.remove(i);
  // }
}

///////////////////////// 아파트 매매 정보 /////////////////////////
//document.querySelector("#list-btn").addEventListener("click", function () {
//  let url =
//    "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
//  let gugunSel = document.querySelector("#gugun");
//  let regCode = gugunSel[gugunSel.selectedIndex].value.substr(0, 5);
//  let yearSel = document.querySelector("#year");
//  let year = yearSel[yearSel.selectedIndex].value;
//  let monthSel = document.querySelector("#month");
//  let month = monthSel[monthSel.selectedIndex].value;
//  let dealYM = year + month;
//  console.log(regCode+" "+year+" "+month);
//  let queryParams =
//    encodeURIComponent("serviceKey") + "=" + "wiwXlCcZPUjIj0mMKDJ1P9vb%2BfMV84Z1EcifVqI4Q%2F%2BjBjWvnEW5SGN7tIdtBjHik%2BDp7dE9RnwEUHO1PE4Umw%3D%3D"; /*Service Key*/
//  queryParams +=
//    "&" +
//    encodeURIComponent("LAWD_CD") +
//    "=" +
//    encodeURIComponent(regCode); /*아파트소재 구군*/
//  queryParams +=
//    "&" + encodeURIComponent("DEAL_YMD") + "=" + encodeURIComponent(dealYM); /*조회년월*/
//  queryParams +=
//    "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent("1"); /*페이지번호*/
//  queryParams +=
//    "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent("50"); /*페이지당건수*/
//
//  fetch(`${url}?${queryParams}`)
//    .then((response) => response.text())
//    .then((data) => makeList(data));
//});
//
//function makeList(data) {
//  document.querySelector(".home-result-box").removeAttribute("hidden");
//  aptPoint = [];
//  var cnt = 0;
//  var map = new kakao.maps.Map(container, options);
//  document.querySelector("table").setAttribute("style", "display: ;");
//  // document.querySelector("#home-map").removeAttribute("hidden");
//  let tbody = document.querySelector("#aptlist");
//  let parser = new DOMParser();
//  bounds = new kakao.maps.LatLngBounds();
//  const xml = parser.parseFromString(data, "application/xml");
//  console.log(xml);
//  initTable();
//  let apts = xml.querySelectorAll("item");
//  apts.forEach((apt) => {
//    let tr = document.createElement("tr");
//    tr.setAttribute("id" , cnt++);
//    tr.addEventListener("click" , function(){
//      var len = parseInt(tr.id);
//      map.setCenter(aptPoint[len]);
//      map.setLevel(1);
//    });
//    let nameTd = document.createElement("div");
//    let name = document.createElement("a");
//    name.appendChild(document.createTextNode(apt.querySelector("아파트").textContent));
//    name.addEventListener("click",function(){
//
//    });
//    nameTd.appendChild(name);
//    nameTd.setAttribute("class","apt-name");
//    tr.appendChild(nameTd);
//
//    // let floorTd = document.createElement("div");
//    // floorTd.appendChild(document.createTextNode(apt.querySelector("층").textContent));
//    // tr.appendChild(floorTd);
//
//    let areaTd = document.createElement("div");
//    areaTd.appendChild(document.createTextNode("면적:"+apt.querySelector("전용면적").textContent));
//    areaTd.setAttribute("class","apt-space");
//    tr.appendChild(areaTd);
//
//    // let dongTd = document.createElement("td");
//    // dongTd.appendChild(document.createTextNode(apt.querySelector("법정동").textContent));
//    // tr.appendChild(dongTd);
//    console.log(apt);
//    let priceTd = document.createElement("div");
//    priceTd.appendChild(
//      document.createTextNode("거래금액:"+apt.querySelector("거래금액").textContent + "만원")
//    );
//    priceTd.setAttribute("class","apt-price");
//    let calendar = document.createElement("span");
//      calendar.innerHTML='<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" class="icon" viewBox="0 0 24 24"><path d="M17 12h-5v5h5v-5zM16 1v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2h-1V1h-2zm3 18H5V8h14v11z"></path></svg>'
//    let dateTd = document.createElement("div");
//    dateTd.appendChild(calendar);
//    dateTd.appendChild(document.createTextNode(apt.querySelector("년").textContent+"."+apt.querySelector("월").textContent+"."+apt.querySelector("일").textContent));
//    console.log(dateTd);
//    dateTd.setAttribute("class","apt-date");
//      
//    tr.appendChild(priceTd);
//    tr.appendChild(dateTd);
//    dateTd.classList.add("text-end");
//    console.log(tr);
//    tbody.appendChild(tr);
//    var geocoder = new kakao.maps.services.Geocoder();
//
//    // 주소로 좌표를 검색합니다
//    geocoder.addressSearch(
//      apt.querySelector("도로명").textContent +
//        String(Number(apt.querySelector("도로명건물본번호코드").textContent)),
//      function (result, status) {
//        // 정상적으로 검색이 완료됐으면
//        if (status === kakao.maps.services.Status.OK) {
//          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
//          //var message = String(apt.querySelector("거래금액").textContent) + "만원"; // 인포윈도우에 표시될 내용입니다
//          aptPoint.push(new kakao.maps.LatLng(result[0].y, result[0].x));
//          bounds.extend(new kakao.maps.LatLng(result[0].y, result[0].x));
//          map.setBounds(bounds);
//          var marker = new kakao.maps.Marker({
//            map: map,
//            position: coords,
//            image:markerImage,
//          });
//          markers.push(marker);
//  
//          // 인포윈도우를 생성합니다
//          // var infowindow = new kakao.maps.InfoWindow({
//          //   //content: message,
//          // });
//  
//          // // 인포윈도우를 마커위에 표시합니다
//          // infowindow.open(map, marker);
//        }
//      }
//    );
//      
//  });
//
//}

function deleteMark() {
  var marker_len = markers.length;
  for (let index = 0; index < marker_len; index++) {
    markers[index].setMap(null);
  }
  markers = [];
}

function initTable() {
  let tbody = document.querySelector("#aptlist");
  let len = tbody.rows.length;
  for (let i = len - 1; i >= 0; i--) {
    tbody.deleteRow(i);
  }
}
