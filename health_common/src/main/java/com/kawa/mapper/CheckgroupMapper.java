package com.kawa.mapper;

import com.github.pagehelper.Page;
import com.kawa.entity.Checkgroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kawa.pojo.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_checkgroup】的数据库操作Mapper
* @createDate 2023-11-23 21:43:12
* @Entity com.kawa.entity.Checkgroup
*/
public interface CheckgroupMapper extends BaseMapper<Checkgroup> {

    //分页查询检查组
    Page<Checkgroup> findPage(QueryPageBean qo);

    //添加 t_checkgroup_checkitem 检查组id 和检查项目id
    void addCheckgroupCheckitem(@Param("checkgroupId") Integer id, @Param("checkitemId") Integer checkitemId);

    //添加检查组
    void add(Checkgroup checkgroup);

    //根据ID查询检查组
    Checkgroup findById(Integer id);

    //根据检查组id查询和该检查组绑定的检查项目id集合
    List<Integer> findCheckItemIdsByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);
}




