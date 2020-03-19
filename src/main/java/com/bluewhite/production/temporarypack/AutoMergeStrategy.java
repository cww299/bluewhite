package com.bluewhite.production.temporarypack;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;

/**
 * 相同内容自动合并行，动态写入需要合并行数 根据内容相同的数量，输入需要合并的行数
 * 
 * @author zhangliang
 *
 */
public class AutoMergeStrategy extends AbstractMergeStrategy {

	private List<QuantitativePoi> list;
	private List<Integer> groupCount;
	private Sheet sheet;

	public AutoMergeStrategy(List<QuantitativePoi> list, List<Integer> groupCount) {
		this.list = list;
		this.groupCount = groupCount;
	}

	// 将该列全部合并成一个单元格
	private void mergeCommonColumn(Integer index) {
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, list.size(), index, index);
		sheet.addMergedRegionUnsafe(cellRangeAddress);
	}

	// 按照分组将各种类别分别合并成一个单元格
	private void mergeGroupColumn(Integer index) {
		Integer rowCnt = 1;
		for (Integer count : groupCount) {
			if(count>1) {
				CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCnt, rowCnt + count - 1, index, index);
				sheet.addMergedRegionUnsafe(cellRangeAddress);
			}
			rowCnt += count;
		}
	}

	@Override
	protected void merge(Sheet sheet, Cell cell, Head head, int relativeRowIndex) {
		this.sheet = sheet;
		if (cell.getRowIndex() == 1) {
			switch (cell.getColumnIndex()) {
			case 0:
				this.mergeGroupColumn(0);
				break;
			case 1:
				this.mergeGroupColumn(1);
				break;
			case 2:
                this.mergeGroupColumn(1);
                break;
			default:
				break;
			}
		}
	}

}
