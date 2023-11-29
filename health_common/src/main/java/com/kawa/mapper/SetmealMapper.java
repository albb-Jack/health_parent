package com.kawa.mapper;

import com.github.pagehelper.Page;
import com.kawa.entity.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kawa.pojo.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_setmeal】的数据库操作Mapper
* @createDate 2023-11-24 12:29:23
* @Entity com.kawa.entity.Setmeal
*/
public interface SetmealMapper extends BaseMapper<Setmeal> {

    //分页查询套餐信息
    Page<Setmeal> findPage(QueryPageBean qo);

    //添加 t_setmeal_checkgroup 套餐id 和检查组id
    void addSetmealCheckgroup(@Param("setmealId") Integer id, @Param("checkgroupId") Integer checkgroupId);

    //添加套餐信息
    void add(Setmeal setmeal);

    //根据ID查询套餐信息
    Setmeal findById(@Param("id")Integer id);

    //根据套餐id查询和该套餐绑定的检查组id集合
    List<Integer> findCheckGroupIdsBySetmealId(@Param("setmealId") Integer setmealId);

    //编辑套餐信息
    void edit(Setmeal setmeal);

    //根据ID删除套餐信息
    void deleteBySetmealId(@Param("setmealId") Integer setmealId);

    //编辑套餐(解绑)
    void deleteBycheckgroupId(@Param("id") Integer id);

    //移动端 查询套餐列表
    List<Setmeal> findAll();

}




