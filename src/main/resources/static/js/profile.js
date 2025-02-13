/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소   touserId = pageuserid 이다
function toggleSubscribe(toUserId,obj) {
	if ($(obj).text() === "구독취소") {
	    $.ajax({
            type: "delete",
            url:"/api/subscribe/"+toUserId,
            dataType:"json"
	    }).done(res=>{
            $(obj).text("구독하기");
            $(obj).toggleClass("blue");
	    }).fail(error => {
            console.log("구독취소실패",error);
	    });


	} else {

	    $.ajax({
                type: "post",
                url:"/api/subscribe/"+toUserId,
                dataType:"json"
    	    }).done(res=>{
                $(obj).text("구독취소");
                $(obj).toggleClass("blue");
    	    }).fail(error => {
                console.log("구독하기실패",error);
    	    });
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(PageUserId) { //PageUserId 그페이지 주인 아이디
	$(".modal-subscribe").css("display", "flex");

    $.ajax({
        url: `/api/user/${PageUserId}/subscribe`,
        dataType: "json"
    }).done(res => {
        console.log(res.data);

        res.data.forEach((u) => {
            let item = getSubscribeModalItem(u); // u는 유저 정보를 뜻합니다
            $("#subscribeModalList").append(item);
        });
    }).fail(error => {
        console.log("구독 정보 불러오기 오류", error);
    });
}

function getSubscribeModalItem(u) { //여기는 그림을 그리는곳
    let item = `
          <div class="subscribe__item" id="subscribeModalItem-${u.id}">
              <div class="subscribe__img">
                  <img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
              </div>
              <div class="subscribe__text">
                  <h2>${u.username}</h2>
              </div>
              <div class="subscribe__btn">`;

      if (!u.equalUserState) { // 동일한 유저가 아닐 때 버튼이 만들어져야 합니다
          if (u.subscribeState) { // 구독한 상태
              item += `<button class="cta blue" onclick="toggleSubscribe(${u.id},this)">구독취소</button>`;
          } else { // 구독 안 한 상태
              item += `<button class="cta" onclick="toggleSubscribe(${u.id},this)">구독하기</button>`;
          }
      }

      item += `
              </div>
          </div>`;

      return item;
}


// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {

//    console.log("pageUserId",pageUserId);
//    console.log("principalId",principalId);

    if(pageUserId != principalId){
        alert("프로필 사진을 수정할 수 없는 유저입니다");
        return; //이렇게 return을 걸면 밑의껄 실행을 안한다
    }

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버에 이미지를 전송
        let profileImageForm = $("#userProfileImageForm")[0]; // 배열로 리턴해주가 때문에 [0] 이거를 붙여준거다
        console.log(profileImageForm);

        //formdata 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다
        let formData = new FormData(profileImageForm); //이거는 사진을 전송 하는거여서 이렇게 꼭 사용한다

        $.ajax({
            type:"put",
            url:`/api/user/${principalId}/profileImageUrl`,
            data:formData,
            contentType: false, // 필수: x-www-form-urlencoded로 파싱되는것을 방지, x-www-formdata 이걸쓰면 사진을 전송 못하기 때문에 false 로 설정
            processData: false, // 필수: contentType을 false로 줬을 때 QueryString 자동 설정됨. 해제
            enctype:"multipart/form-data",
            dataType:"json"
        }).done(res => {
            // 사진 전송 성공시 이미지 변경
            let reader = new FileReader();
            reader.onload = (e) => {
                $("#userProfileImage").attr("src", e.target.result);
            }
            reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
        }).fail(error => {
            console.log("오류",error);
        })


	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






