package com.kawa.service;

import com.kawa.entity.Checkitem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_checkitem】的数据库操作Service
* @createDate 2023-11-23 09:39:14
*/
public interface CheckitemService extends IService<Checkitem> {

    //分页查询检查项
    PageResult findPage(QueryPageBean qo);

    //添加检查项
    Integer add(Checkitem checkitem);

    //编辑检查项
    Integer edit(Checkitem checkitem);

    //查询单个检查项
    Checkitem findById(Integer id);

    //删除检查项
    Integer delete(Integer id);

    //查询所有检查项
    List<Checkitem> findAll();

    //根据检查项id集合批量查询检查项信息
    List<Checkitem> batchFindById(List<Integer> checkItemIds);
}
