package com.kawa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kawa.constant.MessageConstant;
import com.kawa.entity.Checkgroup;
import com.kawa.entity.Checkitem;
import com.kawa.entity.Setmeal;
import com.kawa.mapper.CheckgroupMapper;
import com.kawa.pojo.PageResult;
import com.kawa.pojo.QueryPageBean;
import com.kawa.pojo.Result;
import com.kawa.service.CheckgroupService;
import com.kawa.service.CheckitemService;
import com.kawa.service.SetmealService;
import com.kawa.mapper.SetmealMapper;
import com.kawa.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
* @author kuli
* @description 针对表【t_setmeal】的数据库操作Service实现
* @createDate 2023-11-24 12:29:23
*/
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CheckgroupMapper checkgroupMapper;

    @Autowired
    private CheckgroupService checkgroupService;

    @Autowired
    private CheckitemService checkitemService;

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @Override
    public String upload(MultipartFile imgFile) {

        //1_文件路径 或 文件的字节数组
        byte[]fileBytes = new byte[0];

        try {
            fileBytes = imgFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(Arrays.toString(fileBytes));

        //2_上传到服务器上给该文件取个名字
        String filename = imgFile.getOriginalFilename();
        log.info("上传文件的名称为{}",filename);

        //获取后缀 得到文件的扩展名
        String extension = StringUtils.getFilenameExtension(filename);
        log.info("后缀为{}",extension);

        //生成随机文字
        String randomName = UUID.randomUUID().toString();
        log.info("生成的随机名字{}",randomName);

        //拼接
        String fullName = randomName + "." + extension;

        qiniuUtils.upload2Qiniu(fileBytes,fullName);

        log.info("文件网络路径{}",qiniuUtils.domainName+fullName);

        return qiniuUtils.domainName+fullName;

    }

    /**
     * 分页查询套餐信息
     * @param qo
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean qo) {
        //设置分页参数
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        //如果是分页 返回值 固定是Page import  com.github.pagehelper
        Page<Setmeal>setmeals = setmealMapper.findPage(qo);
        return new PageResult(setmeals.getTotal(),setmeals.getResult());
    }

    /**
     * 添加套餐信息
     * @param groupIds
     * @param setmeal
     */
    @Override
    public void add(List<Integer> groupIds, Setmeal setmeal) {

        setmealMapper.add(setmeal);

        log.info("添加套餐成功,它的主键id为{}",setmeal.getId());

        //添加
        if (setmeal.getId()!=null){
            for (Integer groupId : groupIds) {
                setmealMapper.addSetmealCheckgroup(setmeal.getId(), groupId);
            }
        }

    }

    /**
     * 根据ID查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
       return setmealMapper.findById(id);
    }

    /**
     * 根据套餐id查询和该套餐绑定的检查组id集合
     * @param setmealId
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId) {

        return setmealMapper.findCheckGroupIdsBySetmealId(setmealId);
    }

    /**
     * 编辑套餐信息
     * @param groupIds
     * @param setmeal
     */
    @Override
    public void edit(List<Integer> groupIds, Setmeal setmeal) {
        setmealMapper.edit(setmeal);
        //2_解绑 套餐和检查组关系
        setmealMapper.deleteBycheckgroupId(setmeal.getId());

//        setmealMapper.deleteBysetmealId(setmeal.getId());

        if (setmeal.getId()!=null){

            for (Integer groupId : groupIds) {
                setmealMapper.addSetmealCheckgroup(setmeal.getId(), groupId);
            }
        }

    }

    /**
     * 根据ID删除套餐信息
     * @param setmealId
     * @return
     */
    @Override
    public Result deleteBySetmealId(Integer setmealId) {
        try {
            setmealMapper.deleteBySetmealId(setmealId);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL_ALREADY_HAS_CHECKGROUP);
        }
    }

    /**
     * 移动端 查询套餐列表
     * @return
     */
    @Override
    public List<Setmeal> getSermeal() {
        return setmealMapper.findAll();

    }

    /**
     * 移动端 根据套餐ID 查询 关联的 检查组+检查项 信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findBySetmealId(Integer id) {

        //1_根据套餐id 查询套餐信息
          Setmeal setmeal = setmealMapper.findById(id);
        //2_如果套餐信息存在
        if (setmeal==null){
            return setmeal;
        }
        //3_根据 套餐id 查询 t_setmeal_checkgroup 检查组id集合
         List<Integer> checkGroupIds = setmealMapper.findCheckGroupIdsBySetmealId(id);
        //4_根据 检查组id 批量查询 检查组信息 得到检查组对象列表
         List<Checkgroup> checkgroupList = checkgroupService.batchFindById(checkGroupIds);
        //5_for遍历 检查组对象列表 得到单个的检查组 拿到检查组id
        for (Checkgroup checkgroup : checkgroupList) {
            Integer checkgroupId = checkgroup.getId();
            //6_根据 检查组id 查询 t_checkgroup_checkitem 得到检查项列表集合
            List<Integer> checkItemIds = checkgroupMapper.findCheckItemIdsByCheckGroupId(checkgroupId);
            List<Checkitem> checkitemList = checkitemService.batchFindById(checkItemIds);

            checkgroup.setCheckitemList(checkitemList);
        }

        //7_将 检查项列表 和检查组列表 set到套餐对象中 新建的属性(checkgroupList / checkitemList)
        setmeal.setCheckgroupList(checkgroupList);

        return setmeal;
    }
}




