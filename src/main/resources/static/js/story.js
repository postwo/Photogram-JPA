/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

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

                        <div id="storyCommentList-1">

                            <div class="sl__item__contents__comment" id="storyCommentItem-1"">
                                <p>
                                    <b>Lovely :</b> 부럽습니다.
                                </p>

                                <button>
                                    <i class="fas fa-times"></i>
                                </button>

                            </div>

                        </div>

                        <div class="sl__item__input">
                            <input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />
                            <button type="button" onClick="addComment()">게시</button>
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
function toggleLike(imageid) {
	let likeIcon = $(`#storyLikeIcon-${imageid}`);
	if (likeIcon.hasClass("far")) {
		likeIcon.addClass("fas");
		likeIcon.addClass("active");
		likeIcon.removeClass("far");
	} else {
		likeIcon.removeClass("fas");
		likeIcon.removeClass("active");
		likeIcon.addClass("far");
	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2"">
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







