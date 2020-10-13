package com.school.dao;

import com.school.model.Pics;
import com.school.model.PicsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PicsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    long countByExample(PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int deleteByExample(PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int insert(Pics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int insertSelective(Pics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    Pics selectOneByExample(PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    Pics selectOneByExampleSelective(@Param("example") PicsExample example, @Param("selective") Pics.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    List<Pics> selectByExampleSelective(@Param("example") PicsExample example, @Param("selective") Pics.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    List<Pics> selectByExample(PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    Pics selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Pics.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    Pics selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    Pics selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Pics record, @Param("example") PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Pics record, @Param("example") PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Pics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Pics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") PicsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pics
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}