// (1) 회원정보 수정
function update(userId,event) {
     event.preventDefault(); //폼태그 액션을 막기!!
    let data=$("#profileUpdate").serialize(); //제이쿼리 //이렇게 하면 데이터가 다 담긴다, // form 데이터를 전송할때는 serialize 사용하면 된다.

    console.log(data);

    $.ajax({
        type: "put",
        url:`/api/user/${userId}`,
        data:data, //이거는 위에 데이터를 의미
        contentType: "application/x-www-form-urlencoded; charset=utf-8", // 데이터 생긴거를 뜻한다.
        dataType:"json" //응답 받을 형태
    }).done(res=>{ //HTTPStatus 상태코드 200번대
        console.log("성공",res); //메시지를 보고싶으면 페이지이동을 안하면된다. 밑의 location.herf를 죽석처리 하고 테스트해보면된다.
         location.href = "/user/" + userId; // 자기 원래 페이지로 이동
    }).fail(error=>{ //HTTPStatus 상태코드 200번대가 아닐때
        if(error.data == null){
        alert(error.responseJSON.message);
        }else{
        alert(JSON.stringify(error.responseJSON.data)); //JSON.stringify 는 object를 json문자열로 변경
        }

//        console.log("실패",error.responseJSON.data);
    });
}