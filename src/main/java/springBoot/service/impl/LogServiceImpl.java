package springBoot.service.impl;

import com.github.pagehelper.PageHelper;
import springBoot.constant.WebConst;
import springBoot.dao.LogVoMapper;
import springBoot.modal.vo.LogVo;
import springBoot.modal.vo.LogVoExample;
import springBoot.service.ILogService;
import util.DateKit;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangj
 * @date 2018/1/21 20:52
 */
public class LogServiceImpl implements ILogService{

    @Resource
    private LogVoMapper logDao;

    @Override
    public void insertLog(LogVo logVo) {
        logDao.insert(logVo);
    }

    @Override
    public void insertLog(String action, String data, String ip, Integer authorId) {
        LogVo logs = new LogVo();
        logs.setAction(action);
        logs.setData(data);
        logs.setIp(ip);
        logs.setAuthorId(authorId);
        logs.setCreated(DateKit.getCurrentUnixTime());
        logDao.insert(logs);
    }

    @Override
    public List<LogVo> getLogs(int page, int limit) {
        if (page <= 0) {
            page = 1;
        }
        if (limit < 1 || limit > WebConst.MAX_POSTS) {
            limit = 10;
        }
        LogVoExample logVoExample = new LogVoExample();
        logVoExample.setOrderByClause("id desc");
        PageHelper.startPage((page - 1) * limit, limit);
        List<LogVo> logVos = logDao.selectByExample(logVoExample);
        return logVos;
    }
}