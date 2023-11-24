package com.kawa.controller;

import com.kawa.constant.MessageConstant;
import com.kawa.entity.Checkitem;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.pojo.Result;
import com.kawa.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckitemController {

    @Autowired
    private CheckitemService checkitemService;

    /**
     * 分页查询检查项
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean qo){
        PageResult pageResult = checkitemService.findPage(qo);
        return pageResult;
    }

    /**
     * 添加检查项
     */
    @PostMapping("/add")
    public Result add(@RequestBody Checkitem checkitem){
        Integer result = checkitemService.add(checkitem);
        return new Result(result >=1, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 编辑检查项
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody Checkitem checkitem){
         Integer result = checkitemService.edit(checkitem);
         return new Result(result >=1,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //查询单个检查项
    @GetMapping("/findById")
    public Result findById(Integer id){
        Checkitem checkitem =  checkitemService.findById(id);
        return new Result(checkitem!=null,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitem);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(Integer id){

        Integer result = null;
        try {
            result = checkitemService.delete(id);
            return new Result(result >= 1,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<Checkitem> list = checkitemService.findAll();

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }































}
