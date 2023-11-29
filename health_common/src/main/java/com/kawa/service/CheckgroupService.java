package com.kawa.service;

import com.kawa.entity.Checkgroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.pojo.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_checkgroup】的数据库操作Service
* @createDate 2023-11-23 21:43:12
*/
public interface CheckgroupService extends IService<Checkgroup> {

    //分页查询检查组
    PageResult findPage(QueryPageBean qo);

    //添加检查组
    void add(List<Integer> checkitemIds, Checkgroup checkgroup);

    //根据ID查询检查组
    Checkgroup findById(Integer id);

    //根据检查组id查询和该检查组绑定的检查项目id集合
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    //编辑检查组信息
    void edit(List<Integer> checkitemIds, Checkgroup checkgroup);

    //删除检查组信息
    Result delete(Integer id);

    //查询所有检查组信息
    List<Checkgroup> findAll();

    //根据检查组id集合
    List<Checkgroup> batchFindById(List<Integer> checkGroupIds);
}
