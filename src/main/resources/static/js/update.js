// (1) 회원정보 수정
function update(userId,event) {
     event.preventDefault(); //폼태그 액션을 막기!!
    let data=$("#profileUpdate").serialize(); //제이쿼리 //이렇게 하면 데이터가 다 담긴다,

    console.log(data);

    $.ajax({
        type: "put",
        url:`/api/user/${userId}`,
        data:data, //이거는 위에 데이터를 의미
        contentType: "application/x-www-form-urlencoded; charset=utf-8", // 데이터 생긴거를 뜻한다.
        dataType:"json" //응답 받을 형태
    }).done(res=>{
        console.log("성공",res);
         location.href = "/user/" + userId; // 자기 원래 페이지로 이동
    }).fail(error=>{
        console.log("실패",error);
    });
}