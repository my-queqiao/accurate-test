package com.boc.accuratetest.biz.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boc.accuratetest.biz.ChangeCodeBiz;
import com.boc.accuratetest.mappers.ChangeCodeMapper;
import com.boc.accuratetest.pojo.ChangeCode;
import com.boc.accuratetest.pojo.ChangeCodeExample;

@Service
public class ChangeCodeBizImpl implements ChangeCodeBiz{
	@Autowired
	private ChangeCodeMapper changeCodeMapper;
	@Override
	public List<ChangeCode> page(Integer pageNumber, Integer pageSize, Integer search,Byte dataOfPart,String productionTaskNumber) {
		// SELECT * FROM table LIMIT 5,10;  // 检索记录行 6-15
		int limit = 0;
		if(pageNumber.intValue() == 1) {
			limit = 0;
		}else {
			limit = (pageNumber-1)*pageSize;
		}
		List<ChangeCode> list = changeCodeMapper.page(search,limit,pageSize, dataOfPart,productionTaskNumber);
		return list;
	}

	@Override
	public Integer findTotal(Integer search,Byte dataOfPart,String productionTaskNumber) {
		int total = changeCodeMapper.findTotal(search, dataOfPart,productionTaskNumber);
		return total;
	}

	@Override
	public void insertBatch(List<ChangeCode> ccs) {
		changeCodeMapper.insertBatch(ccs);
	}

	@Override
	public List<ChangeCode> countByChangeType(String productionTaskNumber) {
		return changeCodeMapper.countByChangeType(productionTaskNumber);
	}

	@Override
	public List<ChangeCode> findChangeCodeLinkTestExample(String productionTaskNumber) {
		return changeCodeMapper.findChangeCodeLinkTestExample(productionTaskNumber);
	}

	@Override
	public void deleteByProductionTaskNumber(String productionTaskNumber) {
		ChangeCodeExample e = new ChangeCodeExample();
		e.createCriteria().andProductionTaskNumberEqualTo(productionTaskNumber);
		changeCodeMapper.deleteByExample(e);
	}

	@Override
	public ChangeCode getById(Integer id) {
		return changeCodeMapper.selectByPrimaryKey(id);
	}

}
