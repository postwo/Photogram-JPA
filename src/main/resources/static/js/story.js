/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (0) 현재 로그인한 사용자 아이디
let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page =0;

function storyLoad() {
    $.ajax({ //type get인데 디폴트 여서 안넣어도 된다
     url:`/api/image?page=${page}`,
     dataType:"json"
    }).done(res=>{
    console.log(res);
        res.data.content.forEach((image)=>{
            let storyItem = getStoryItem(image); //그림 그려준거
            $("#storyList").append(storyItem); // storyList는 story.jsp 에 있는 태그 아이디다
        });
    }).fail(error=>{
    console.log(error);
    })
}

storyLoad(); //호출

//그림 그리기
//{image.user.profileImageUrl} 이렇게 가지고 오는 이유는 조인해서 가지고 오기 때문이다
function getStoryItem(image) {
    let item = `<div class="story-list__item">
                    <div class="sl__item__header">
                        <div>
                            <img class="profile-image" src="/upload/${image.user.profileImageUrl}"
                                onerror="this.src='/images/person.jpeg'" />
                        </div>
                        <div>${image.user.username}</div>
                    </div>

                    <div class="sl__item__img">
                        <img src="/upload/${image.postImageUrl}" />
                    </div>

                    <div class="sl__item__contents">
                        <div class="sl__item__contents__icon">

                            <button>`;
                                if(image.likeState){
                                   item +=`<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
                                }else{
                                   item +=`<i class="far fa-heart " id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
                                }


                            item +=`
                            </button>
                        </div>

                        <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

                        <div class="sl__item__contents__content">
                            <p>${image.caption}</p>
                        </div>

                        <div id="storyCommentList-${image.id}">`;

                            image.comments.forEach((comment)=>{
                                item +=` <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
                                            <p>
                                                <b>${comment.user.username} :</b> ${comment.content}
                                            </p>`;

                                            if(principalId == comment.user.id){ // 잃치할떄만  댓글 삭제 버튼 생성
                                                item += ` <button onclick="deleteComment(${comment.id})">
                                                             <i class="fas fa-times"></i>
                                                         </button>`;
                                            }

                                            item +=`
                                        </div>`;
                            });



                        item += `
                        </div>

                        <div class="sl__item__input">
                            <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
                            <button type="button" onClick="addComment(${image.id})">게시</button>
                        </div>

                    </div>
                </div>`;

                return item;
}

// (2) 스토리 스크롤 페이징하기
//  윈도우 스크롤탑 ==  문서의 높이 - 윈도우 높이
$(window).scroll(() => {
//    console.log("윈도우 스크롤탑",$(window).scrollTop());
//    console.log("문서의 높이",$(document).height());
//    console.log("윈도우 높이",$(window).height());

    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    console.log(checkNum);

    if(checkNum < 1 && checkNum > -1){
        page++;
        storyLoad();
    }
});


// (3) 좋아요, 안좋아요
// "" 로 하면 $를 못받아서 `` 으로 변경
function toggleLike(imageId) {

let likeIcon = $("#storyLikeIcon-" + imageId);
	if (likeIcon.hasClass("far")) { //좋아요 하겠다
		$.ajax({
			type: "POST",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res => { //$(`#storyLikeCount-${imageId} 해당아이디로 접근해서 그내부에 있는 text를 가지고 온다는 뜻이다
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;  // Number= 문자열을 숫자로 변환
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error => {
		    console.log("오류",error);
		});



	} else { // 좋아요 취소
		$.ajax({
			type: "DELETE",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res => {
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error => {
             console.log("오류",error);
        });

	}

}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
        imageId: imageId,
		content: commentInput.val()
	}

// 이부분을 주석처리하면 valdation 테스트를 할 수 있다
// 프론트단에서 막아주기 때문에 서버에서 에러가 뜰경우는 거의 없다
	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

    $.ajax({
        type: "POST",
        url: `/api/comment`,
        data: JSON.stringify(data), // 통신하기 편하게 data를 json형태로 변환 = Json.stringify
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(res => { //res는 항상 통신 결과 있다
        console.log("성공",res);

        let comment = res.data; // 삭제하기위해서 id 값이 필요하기 때문에 사용

        let content = `
          <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
            <p>
              <b>${comment.user.username} :</b>
              ${comment.content}
            </p>
            <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
          </div>
        `;
        commentList.prepend(content);

    }).fail(error => {
        console.log("오류",error.responseJSON.data.content);
        alert(error.responseJSON.data.content); //포스트맨으로 뚫거나 할때 발생
    })


	commentInput.val(""); //인풋 필드를 깨끗하게 비워준다
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type: "delete",
		url: `/api/comment/${commentId}`,
		dataType: "json"
	}).done(res=>{
		console.log("성공", res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("오류", error);
	});
}







