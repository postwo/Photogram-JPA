package com.example.photogram.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    //fromuserid=  로그인한 아이디
    @Query(value = "select * from image where userid in(select touserid from subscribe where fromuserid= :principalid)", nativeQuery = true)
    List<Image> mStory(int principalid);
}
