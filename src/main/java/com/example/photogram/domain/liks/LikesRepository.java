package com.example.photogram.domain.liks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes,Integer> {

    @Modifying
    @Query(value = "insert into likes(imageId,userId,createDate) values(:imageId, :principalId , now())",nativeQuery = true)
    int mLikes(int imageId,int principalId);

    @Modifying
    @Query(value = "delete from likes where imageId = :imageId and userId = :principalId",nativeQuery = true)
    int mUnLikes(int imageId,int principalId);
}
