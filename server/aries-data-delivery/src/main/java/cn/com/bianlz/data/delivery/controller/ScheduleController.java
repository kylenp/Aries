package cn.com.bianlz.data.delivery.controller;

import cn.com.bianlz.common.vo.Result;
import cn.com.bianlz.data.delivery.api.vo.DataDeliveryApiProtocolCode;
import cn.com.bianlz.data.delivery.api.vo.Schedule;
import cn.com.bianlz.data.delivery.service.ScheduleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bianlanzhou on 17/10/17.
 * Description
 */
@RestController
public class ScheduleController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping(value={"/schedule/list/{pageNum}/{pageSize}/{day}/{uuid}","/schedule/list/{pageNum}/{pageSize}/{day}"})
    public Result get(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,@PathVariable("day") String day,@PathVariable(value = "uuid",required = false) String uuid){
        Long start = System.currentTimeMillis();
        Result<Map<String,Object>> result = new Result<Map<String, Object>>();
        result.setCode(DataDeliveryApiProtocolCode.SUCCESS.getCode());
        result.setCode(DataDeliveryApiProtocolCode.FAIL.getMessage());
        try{
            Map<String,Object> rtn = new HashMap<String, Object>();
            Schedule schedule = new Schedule();
            if(uuid!=null){
                schedule.setUuid(uuid);
            }
            schedule.setDateStamp(day);
            Page page = PageHelper.offsetPage(((pageNum-1)*pageSize), pageSize,true);
            List<Schedule> data = scheduleService.get(schedule);
            rtn.put("data",data);
            rtn.put("total",page.getTotal());
            rtn.put("pages",page.getPages());
            result.setCode("DD10000");
            result.setData(rtn);
        }catch (Exception ex){
            result.setCode(DataDeliveryApiProtocolCode.FAIL.getCode());
            result.setMessage(DataDeliveryApiProtocolCode.FAIL.getMessage());
        }
        logger.debug("scheule list cast :" + (System.currentTimeMillis() - start ));
        return result;
    }

}
