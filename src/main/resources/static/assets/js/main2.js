///////////////////////// select box 설정 (지역, 매매기간) /////////////////////////
let date = new Date();

window.onload = function () {
  // 브라우저가 열리면 시도정보 얻기.
  sendRequest("sido", "*00000000");
};


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
//
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
