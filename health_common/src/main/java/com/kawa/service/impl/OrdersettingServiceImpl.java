package com.kawa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawa.constant.MessageConstant;
import com.kawa.entity.Ordersetting;
import com.kawa.pojo.Result;
import com.kawa.service.OrdersettingService;
import com.kawa.mapper.OrdersettingMapper;
import com.kawa.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author kuli
* @description 针对表【t_ordersetting】的数据库操作Service实现
* @createDate 2023-11-27 15:24:27
*/
@Service
@Slf4j
public class OrdersettingServiceImpl extends ServiceImpl<OrdersettingMapper, Ordersetting>
    implements OrdersettingService{

    @Autowired
    private OrdersettingMapper ordersettingMapper;

    /**
     * 根据月份查询预约设置列表信息
     * @param date
     * @return
     */
    @Override
    public List<Ordersetting> getOrderSettingByMonth(String date) {

        //1_组装参数
        String startTime = date + "-1";
        String endTime = date + "-30";
        List<Ordersetting> ordersettingList = ordersettingMapper.getOrderSettingByMonth(startTime,endTime);

        //给 月中几号 属性赋值
        for (Ordersetting ordersetting : ordersettingList) {
            ordersetting.setDate(ordersetting.getOrderDate().getDate());
        }
        log.info("查询到的数据{}",ordersettingList);
        return ordersettingList;


    }

    /**
     * 根据时间单个设置 可预约的人数
     * @param ordersetting
     */
    @Override
    public void editNumberByDate(Ordersetting ordersetting) {
        try {
            //paramDate 参数  2023-11-29
            //会出现bug  入参2023-11-28  00:00:00  != 2023-11-28  09:30:30
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date paramDate = sdf.parse(sdf.format(ordersetting.getOrderDate()));
            Date now = sdf.parse(sdf.format(new Date()));

            System.out.println("paramDate = " + paramDate);

            System.out.println("now = " + now);

            //只比较年月日
            if (!paramDate.after(now)) {   //https://www.zhihuclub.com/180268.shtml
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //判断当前时间 是否已经存在了数据
        List<Ordersetting> ordersettingList = ordersettingMapper.getNumberByDate(ordersetting);

        if (ordersettingList.size() > 0) {
            //如果有数据 说明是更新请求
            Integer reservations = ordersettingList.get(0).getReservations();
            Integer id = ordersettingList.get(0).getId();
            ordersetting.setReservations(reservations);
            ordersetting.setId(id);
            ordersettingMapper.updateNumberByDate(ordersetting);
        } else {
            //如果没用数据 说明添加请求
            //只能保存未来的时间
            ordersetting.setReservations(0);
            ordersettingMapper.insertNumberByDate(ordersetting);
        }

    }

    /**
     * 批量预约设置
     * @param excelFile
     * @return
     */
    @Override
    public Result upload(MultipartFile excelFile) {

        try {
            //1_调用工具类将文件转成流
            List<String[]> listStrArr = POIUtils.readExcel(excelFile);

            //2_将流转换成集合
            List<Ordersetting> ordersettingList = new ArrayList<>();
            for (String[] strings : listStrArr) {
                //初始对象
                Ordersetting ordersetting = new Ordersetting();
                //给预约设置对象 赋值
                ordersetting.setOrderDate(new Date(strings[0]));
                ordersetting.setNumber(Integer.valueOf(strings[1]));

                //添加到集合
                ordersettingList.add(ordersetting);
            }

            //3_ List<Ordersetting> 批量保存 到数据中 t_ordersetting
            for (Ordersetting ordersetting : ordersettingList) {
                this.editNumberByDate(ordersetting);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.UPLOAD_SUCCESS);
    }

}




