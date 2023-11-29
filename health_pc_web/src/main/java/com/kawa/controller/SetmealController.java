package com.kawa.controller;

import com.kawa.constant.MessageConstant;
import com.kawa.entity.Setmeal;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.pojo.Result;
import com.kawa.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile imgFile){
         String url = setmealService.upload(imgFile);
         return new Result(true, MessageConstant.UPLOAD_SUCCESS,url);
    }

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean qo){
       PageResult pageResult = setmealService.findPage(qo);
       return pageResult;
    }

    /**
     * 添加套餐信息
     */
    @PostMapping("/add")
    public Result add(@RequestParam List<Integer>groupIds,@RequestBody Setmeal setmeal){
            setmealService.add(groupIds,setmeal);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 根据ID查询套餐信息
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

    /**
     * 根据套餐id查询和该套餐绑定的检查组id集合
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(Integer setmealId){
        List<Integer> list = setmealService.findCheckGroupIdsBySetmealId(setmealId);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    /**
     * 编辑套餐信息
     */
    @PostMapping("/edit")
    public Result edit(@RequestParam List<Integer>groupIds,@RequestBody Setmeal setmeal){
        try {
            setmealService.edit(groupIds,setmeal);

            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    /**
     * 根据ID删除套餐信息
     */
    @GetMapping("/delete")
    public Result delete(Integer setmealId){
        Result result = setmealService.deleteBySetmealId(setmealId);
        return result;
    }
}
