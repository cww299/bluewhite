package com.bluewhite.common.entity;

import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 分页工具类
 * @author zhangliang
 *
 */
public class PageUtil {

	/**
	 * map参数转成分页
	 * @param params
	 * @return
	 */
	public static PageParameter mapToPage(Map<String, Object> params) {
		PageParameter page = new PageParameter();
		if(params.containsKey("page") && params.containsKey("size")){
			Sort sort = new Sort(Sort.Direction.DESC,"id");
			if(params.containsKey("direction") && params.containsKey("sort")){
				Direction direction = Direction.fromString(params.get("direction").toString());
				String sorts = params.get("sort").toString();
				sort = new Sort(direction,sorts);
				params.remove("direction");
				params.remove("sort");
			}
			page = new PageParameter(Integer.parseInt(params.get("page").toString())-1,
					Integer.parseInt(params.get("size").toString()),sort);
			params.remove("page");
			params.remove("size");
		}
		return page;
	}

}
