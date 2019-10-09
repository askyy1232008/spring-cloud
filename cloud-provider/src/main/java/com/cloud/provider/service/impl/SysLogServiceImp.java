package com.cloud.provider.service.impl;

import com.cloud.provider.dao.SysLogDao;
import com.cloud.provider.domain.SysLog;
import com.cloud.provider.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("SysLogService")
public class SysLogServiceImp implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public void saveSysLog(SysLog sysLog) {
        this.sysLogDao.saveSysLog(sysLog);
    }
}
