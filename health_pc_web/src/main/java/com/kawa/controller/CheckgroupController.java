package com.kawa.controller;

import com.kawa.constant.MessageConstant;
import com.kawa.entity.Checkgroup;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.pojo.Result;
import com.kawa.service.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Autowired
    private CheckgroupService checkgroupService;

    /**
     * 分页查询检查组
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean qo){
        PageResult pageResult = checkgroupService.findPage(qo);
        return pageResult;
    }

    /**
     *添加检查组
     */
    @PostMapping("/add")
    public Result add(@RequestParam List<Integer>checkitemIds, @RequestBody Checkgroup checkgroup){
        try {
            checkgroupService.add(checkitemIds,checkgroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据ID查询检查组
     */
    @GetMapping("/findById")
    public Result findById(Integer id){

        Checkgroup checkgroup = checkgroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroup);
    }

    /**
     * 根据检查组id查询和该检查组绑定的检查项目id集合
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
        public Result findCheckItemIdsByCheckGroupId(Integer checkGroupId){
        List<Integer> list = checkgroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }

    /**
     * 编辑检查组信息
     */
    @PostMapping("/edit")
    public Result edit(@RequestParam List<Integer>checkitemIds,@RequestBody Checkgroup checkgroup){
        try {
            checkgroupService.edit(checkitemIds,checkgroup);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 删除检查组信息
     */
    @GetMapping("/delete")
    public Result delete(Integer id){
        Result result = checkgroupService.delete(id);
        return result;
    }

    /**
     * 查询所有检查组信息
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<Checkgroup>findAll = checkgroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,findAll);
    }

}
