package com.bluewhite.common.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.bluewhite.common.ServiceException;
import com.bluewhite.common.utils.NumUtils;

/**
 * 分页统计结果集，凡是使用到统计类的。必须手动触发一下统计工作。{@link #count()}
 *
 * @author zhangliang
 **/
public class PageResultStat<T> extends PageResult<T> {

    /**
     * 用于统计工具类
     */
    private Map<String,String> statisticsFields = new HashMap<>();

    private List<?> totalData = null;

    public PageResultStat() {
        super();
    }


    /**
     * 通过该构造方法，工具里手动处理分页
     * @param pageResult
     * @param pp
     */
    public PageResultStat(Page<T> pageResult, PageParameter page) {
        int endPosition = page.getOffset() + page.getPageSize();
        if (endPosition > pageResult.getContent().size()) {
            endPosition = pageResult.getContent().size();
        }
        setTotal(pageResult.getTotalElements());
        setTotalPages((int) Math.ceil((double) pageResult.getTotalElements() / (double)page.getSize()));
		setPageNum(page.getPage()+2);
        setRows(  pageResult.getContent().size() <= page.getPageSize() ? pageResult.getContent() : pageResult.getContent().subList(page.getOffset(),endPosition));
        totalData = pageResult.getContent();
    }

    /**
     * 统计数据
     */
    private Map<String, Object> statData = new HashMap<>();

    /**
     * 添加统计数据
     *
     * @param statCount  数量
     * @param statAmount 金额
     */
    public void setStateData(int statCount, Double statAmount) {
        statData.put("stateCount", statCount);
        statData.put("statAmount", statAmount);
    }

    /**
     * 传入自动统计的两个字段，第一个对应数量的字段名，第二个对应金额的字段名
     * @param statCountField 传NULL代表不统计该字段
     * @param stateAmountField 传NULL代表不统计该字段
     */
    public void setAutoStateField(String statCountField, String stateAmountField) {
        if (statCountField != null) {
            addAutoStateField("stateCount",statCountField);
        }
        if (stateAmountField != null) {
            addAutoStateField("statAmount",stateAmountField);
        }
    }

    /**
     * 注意：第一个参数是展示给前端的字段名，第二个参数是你要自动统计的javabean的字段名!
     * @param columnName JSON FIELD
     * @param field YOUR OBJECT FIELD
     */
    public void addAutoStateField(String columnName, String field) {
        statisticsFields.put(columnName, field);
    }
    
    
    /**
     * 通过传入的字段，统计总数。可以传入多个字段
     * @param statCountField 传NULL代表不统计该字段
     * @param stateAmountField 传NULL代表不统计该字段
     */
    public void setAutoStateField(String...properties) {
    	List<String> ignoreList = null;
		if(properties != null){
			ignoreList = Arrays.asList(properties);
	    }
		List<String> ignoreListRe = ignoreList;
		for(String ignore : ignoreListRe){
			addAutoStateField(ignore,ignore);
		}
    }
    
    


    /**
     * 计算，当有使用自动统计字段时调用
     */
    public void count() {
        if (statisticsFields.size() == 0) {
            throw new ServiceException("错误，没有使用自动统计字段，无法统计.");
        }
        Object[] countValues = StatisticsUtils.c(statisticsFields.values().toArray(new String[]{})).count(totalData);
        int i = 0;
        for (String statisticsField : statisticsFields.keySet()) {
            addStateData(statisticsField,NumUtils.round(Double.valueOf(countValues[i++].toString()), 2));
        }
    }
    
    
    

    /**
     * 添加统计数据，特定情况，指定key->value
     *
     * @param key
     * @param value
     */
    private void addStateData(String key, Object value) {
        statData.put(key, value);
    }

    public Map<String, Object> getStatData() {
        return statData;
    }

}
