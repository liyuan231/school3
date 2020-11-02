package com.school.dao;

import com.school.dto.LikeWithUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeWithUserMapper {

    public List<LikeWithUser> querySelective(
            @Param("year")Integer year,
            @Param("schoolName") String schoolName,
            @Param("page")Integer page,
            @Param("pageSize")Integer pageSize,
            @Param("sort") String sort,
            @Param("order") String order);

    Integer count(@Param("year") Integer year,@Param("schoolName") String likeSchoolName);

    List<LikeWithUser> querySelectiveDistinct(@Param("year")Integer year,
                                       @Param("schoolName") String schoolName,
                                       @Param("page")Integer page,
                                       @Param("pageSize")Integer pageSize,
                                       @Param("sort") String sort,
                                       @Param("order") String order);

    int countDistinct(@Param("year") Integer year,@Param("schoolName") String likeSchoolName);
}
