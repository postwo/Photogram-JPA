package com.example.photogram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    //fromuserid=  로그인한 아이디
    @Query(value = "select * from image where userid in(select touserid from subscribe where fromuserid= :principalid) ORDER BY id DESC", nativeQuery = true)
    Page<Image> mStory(int principalid, Pageable pageable);

    // 인기 사진 == 좋아요가 있는 사진만
    @Query(value ="select i.* from image i inner join (select imageId, count(imageId) likeCount from likes group by imageId) c on i.id = c.imageid order by likecount desc ",nativeQuery = true)
    List<Image> mPopular();
}
