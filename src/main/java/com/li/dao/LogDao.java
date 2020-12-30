package com.li.dao;

import com.li.entity.modeldo.LogDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 */
@Mapper
public interface LogDao {

    LogDo get(Long autoId);

    List<LogDo> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(LogDo log);

    int update(LogDo log);

    int remove(Long id);

    int batchRemove(Long[] ids);

    int delLogData(int nDay);
}
