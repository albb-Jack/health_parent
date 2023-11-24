package com.kawa.mapper;

import com.github.pagehelper.Page;
import com.kawa.entity.Checkitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kawa.pojo.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
* @author kuli
* @description 针对表【t_checkitem】的数据库操作Mapper
* @createDate 2023-11-23 09:39:14
* @Entity com.kawa.entity.Checkitem
*/
public interface CheckitemMapper extends BaseMapper<Checkitem> {

    //分页查询检查项
    Page<Checkitem> findPage(QueryPageBean qo);

    //添加检查项
    Integer add(Checkitem checkitem);

    //编辑检查项
    Integer edit(Checkitem checkitem);

    //查询单个检查项
    Checkitem findById(@Param("id") Integer id);

    //删除检查项
    Integer deleteCheckitem(@Param("id") Integer id);

    //查询所有检查项
    List<Checkitem> findAll();
}




