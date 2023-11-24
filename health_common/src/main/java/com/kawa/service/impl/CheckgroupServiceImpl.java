package com.kawa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kawa.entity.Checkgroup;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.service.CheckgroupService;
import com.kawa.mapper.CheckgroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_checkgroup】的数据库操作Service实现
* @createDate 2023-11-23 21:43:12
*/
@Service
@Slf4j
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup>
    implements CheckgroupService{

    @Autowired
    private CheckgroupMapper checkgroupMapper;

    /**
     * 分页查询检查组
     * @param qo
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean qo) {
        //设置分页参数
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        //如果是分页 返回值 固定是 Page import  com.github.pagehelper
        Page<Checkgroup> checkgroups =  checkgroupMapper.findPage(qo);
        return new PageResult(checkgroups.getTotal(),checkgroups.getResult());
    }

    /**
     * 添加检查组
     * @param checkitemIds
     * @param checkgroup
     */
    @Override
    public void add(List<Integer> checkitemIds, Checkgroup checkgroup) {
        //1_添加 t_checkgroup 并返回 自动生成的主键id
        checkgroupMapper.add(checkgroup);

        //打印 checkgroup 是否有id属性
        log.info("添加检查组成功,它的主键id为{}",checkgroup.getId());

        //2_添加 t_checkgroup_checked
        if (checkgroup.getId()!=null){
            for (Integer checkitemId : checkitemIds) {
                checkgroupMapper.addCheckgroupCheckitem(checkgroup.getId(),checkitemId);
                // checkgroup = 1   checkitemIds = [5,6]
                //循环 checkitemIds
                //第一次操作 添加 1  5
                //第二次操作 添加 1  6
            }
        }
    }

    /**
     * 根据ID查询检查组
     * @param id
     * @return
     */
    @Override
    public Checkgroup findById(Integer id) {
        return checkgroupMapper.findById(id);
    }

    /**
     * 根据检查组id查询和该检查组绑定的检查项目id集合
     * @param checkGroupId
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkgroupMapper.findCheckItemIdsByCheckGroupId(checkGroupId);
    }
}




