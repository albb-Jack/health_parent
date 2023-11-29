package com.kawa.controller;

import com.kawa.constant.MessageConstant;
import com.kawa.entity.Setmeal;
import com.kawa.pojo.Result;
import com.kawa.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 移动端 查询套餐列表
     * @return
     */
    @PostMapping("/getSetmeal")
    public Result getSermeal(){
       List<Setmeal> setmealList = setmealService.getSermeal();
       return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmealList);
    }

    /**
     * 移动端 根据套餐ID 查询 关联的 检查组+检查项 信息
     * @param id
     * @return
     */
    @PostMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
