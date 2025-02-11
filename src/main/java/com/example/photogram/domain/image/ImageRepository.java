package com.example.photogram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    //fromuserid=  로그인한 아이디
    @Query(value = "select * from image where userid in(select touserid from subscribe where fromuserid= :principalid) ORDER BY id DESC", nativeQuery = true)
    Page<Image> mStory(int principalid, Pageable pageable);
}
