package com.xiongyc.sequence.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiongyc.cache.RedisUtil;
import com.xiongyc.sequence.service.SequenceService;
import com.xiongyc.sequence.utils.DateUtil;
import com.xiongyc.sequence.utils.SequenceUtil;

/**
 * 
 * @author YouCai.Xiong
 * @Date 2019年1月14日 - 下午6:15:14
 * @Info 初始版本
 * @Version 1.0
 */
@Service
public class SequenceServiceImpl implements SequenceService {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public String getUpdateQuerySeq(String prefix, String tableName) {
		String journalCode = null;
		try {
			Long seq = redisUtil.getSeq(tableName, 1l);
			journalCode = prefix + DateUtil.formatDate(Calendar.getInstance().getTime(), DateUtil.FORMAT_DATE_YYYYMMDD) + SequenceUtil.addLeftZero(seq + "", 9);
		} catch (Exception e) {
			throw e;
		}
		return journalCode;
	}

	@Override
	public String getUpdateQuerySeq(String prefix, String tableName, Integer suffixLength) {
		String journalCode = null;
		try {
			Long seq = redisUtil.getSeq(tableName, 1l);
			journalCode = prefix + DateUtil.formatDate(Calendar.getInstance().getTime(), DateUtil.FORMAT_DATE_YYYYMMDD) + SequenceUtil.addLeftZero(seq + "", suffixLength);
		} catch (Exception e) {
			throw e;
		}
		return journalCode;
	}

}
