package com.kawa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kawa.entity.Checkitem;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.service.CheckitemService;
import com.kawa.mapper.CheckitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_checkitem】的数据库操作Service实现
* @createDate 2023-11-23 09:39:14
*/
@Service
public class CheckitemServiceImpl extends ServiceImpl<CheckitemMapper, Checkitem>
    implements CheckitemService{
    @Autowired
    private CheckitemMapper checkitemMapper;

    /**
     * 分页查询检查项
     * @param qo
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean qo) {

        //设置分页参数
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());

        //如果是分页 返回值 固定是 Page import  com.github.pagehelper
        Page<Checkitem> checkitemList = checkitemMapper.findPage(qo);

        return new PageResult(checkitemList.getTotal(),checkitemList.getResult());
    }

    /**
     * 添加检查项
     * @param checkitem
     * @return
     */
    @Override
    public Integer add(Checkitem checkitem) {

       Integer result = checkitemMapper.add(checkitem);
        return result;
    }

    /**
     * 编辑检查项
     * @param checkitem
     * @return
     */
    @Override
    public Integer edit(Checkitem checkitem) {
        return checkitemMapper.edit(checkitem);
    }

    /**
     * 查询单个检查项
     * @param id
     * @return
     */
    @Override
    public Checkitem findById(Integer id) {
        return checkitemMapper.findById(id);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @Override
    public Integer delete(Integer id) {
        return checkitemMapper.deleteCheckitem(id);
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<Checkitem> findAll() {
        return checkitemMapper.findAll();
    }

    /**
     * //根据检查项id集合批量查询检查项信息
     * @param checkItemIds
     * @return
     */
    @Override
    public List<Checkitem> batchFindById(List<Integer> checkItemIds) {

        return checkitemMapper.batchFindById(checkItemIds);
    }


}




