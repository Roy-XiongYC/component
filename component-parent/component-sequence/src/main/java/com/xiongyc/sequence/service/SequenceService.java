package com.xiongyc.sequence.service;

/**
 * 
 * @author YouCai.Xiong
 * @Date 2019年1月14日 - 下午6:15:01
 * @Info 初始版本
 * @Version 1.0
 */
public interface SequenceService {

	public String getUpdateQuerySeq(String prefix, String tableName);

	public String getUpdateQuerySeq(String prefix, String tableName, Integer suffixLength);

}
