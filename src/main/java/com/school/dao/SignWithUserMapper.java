package com.school.dao;

import com.school.dto.SignWithUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignWithUserMapper {
    public List<SignWithUser> querySelective(
            @Param("year")Integer year,
            @Param("schoolName") String schoolName,
            @Param("page")Integer page,
            @Param("pageSize")Integer pageSize,
            @Param("sort") String sort,
            @Param("order") String order);

    Integer count(@Param("year") Integer year,@Param("schoolName") String schoolName);

}
