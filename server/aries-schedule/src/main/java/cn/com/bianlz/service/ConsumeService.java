package cn.com.bianlz.service;

import java.util.List;
import java.util.Map;

/**
 * Created by bianlanzhou on 17/10/20.
 * Description
 */
public interface ConsumeService {
    List<Map<String,Long>> getConsume();

    void saveConsume(List<Map<String,Long>> list);
}
