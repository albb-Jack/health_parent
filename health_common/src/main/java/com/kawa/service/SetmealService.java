package com.kawa.service;

import com.kawa.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.pojo.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_setmeal】的数据库操作Service
* @createDate 2023-11-24 12:29:23
*/
public interface SetmealService extends IService<Setmeal> {

    //上传图片
    String upload(MultipartFile imgFile);

    //分页查询套餐信息
    PageResult findPage(QueryPageBean qo);

    //添加套餐信息
    void add(List<Integer> groupIds, Setmeal setmeal);

    //根据ID查询套餐信息
    Setmeal findById(Integer id);

    //根据套餐id查询和该套餐绑定的检查组id集合
    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    //编辑套餐信息
    void edit(List<Integer> groupIds, Setmeal setmeal);

    //根据ID删除套餐信息
    Result deleteBySetmealId(Integer setmealId);

    //移动端 查询套餐列表
    List<Setmeal> getSermeal();

    //移动端 根据套餐ID 查询 关联的 检查组+检查项 信息
    Setmeal findBySetmealId(Integer id);
}
