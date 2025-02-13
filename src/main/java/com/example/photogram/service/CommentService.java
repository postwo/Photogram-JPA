package com.example.photogram.service;

import com.example.photogram.domain.comment.Comment;
import com.example.photogram.domain.comment.CommentRepository;
import com.example.photogram.domain.image.Image;
import com.example.photogram.domain.user.User;
import com.example.photogram.domain.user.UserRepository;
import com.example.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment 댓글쓰기(String content, int imageId, int userId) {

        //유저나 이미지 오브젝트 이므로 가짜 객체를 만들면 된다. = 이렇게 아이디값만 뽑아오면 된다 = 데이터베이스에는 아이디 값만 들어가면 된다
        //Tip(객체를 만들 떄 id값만 담아서 insert 할 수 있다)
        // 대신 다른 정보는 아무것도 안가지고 온다 만약에 모든정보를 세팅 하면 이방식은 사용하면 안 된다
        // 대신 return 시에 image 객체와 user객체는 id 값만 가지고 있는 빈 객체를 받는다
        Image image = new Image();
        image.setId(imageId);

        // 유저는 다른정보인 username도 필요하기 때문에 이방식은 사용못한다
//        User user = new User();
//        user.setId(userId);

        //다른 정보도 필요하면 이방식을 사용해야 한다
        User userEntity = userRepository.findById(userId).orElseThrow(() ->{
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }

    @Transactional
    public void 댓글삭제 (int id) {
        try{
            commentRepository.deleteById(id);
        } catch (Exception e) { //CustomException은 html 파일 반환 해주는 컨트롤러 , CustomApiException 데이터를 리턴해주는 컨트롤러, validation은 처음 값을 받을떄 그럴때 사용
            throw new CustomApiException(e.getMessage());
        }

    }


}
