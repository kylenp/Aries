package cn.com.bianlz.web.client;

import cn.com.bianlz.common.vo.Result;
import cn.com.bianlz.user.api.user.User;
import cn.com.bianlz.web.common.ServiceHelper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bianlanzhou on 17/10/17.
 * Description
 */
@FeignClient(name = ServiceHelper.DATA_DELIVERY_SERVICE)
public interface DeliveryServiceClient {
    @RequestMapping(value="/data/delivery/schedule/{pageNum}/{pageSize}/{day}",method = RequestMethod.GET)
    public Result getSchedule(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,@PathVariable("day") String day);

    @RequestMapping(value="/data/delivery/schedule/{pageNum}/{pageSize}/{day}/{uuid}",method = RequestMethod.GET)
    public Result getScheduleById(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,@PathVariable("day") String day,@PathVariable("uuid") String uuid);
}