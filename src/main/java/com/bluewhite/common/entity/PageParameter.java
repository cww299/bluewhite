package com.bluewhite.common.entity;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageParameter implements Pageable {

    /**
     * 页码
     */
    private int page;
    /**
     * 分页大小
     */
    private int size;
    /**
     * 查询排序实体
     */
    private Sort sort;

    public int getPage() {
        return page - 1;
    }

    public void setPage(int page) {
        this.page = page - 1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getPageSize() {
        return size;
    }

    public int getPageNumber() {
        return page;
    }

    public int getOffset() {
        return page * size;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    public PageParameter() {
        page = 0;
        size = 10;
        sort = new Sort(Sort.Direction.DESC, "id");
    }

    /**
     * Creates a new {@link PageRequest}. Pages are zero indexed, thus providing 0 for {@code page} will return the
     * first page.
     * 
     * @param page
     *            zero-based page index.
     * @param size
     *            the size of the page to be returned.
     */
    public PageParameter(int page, int size) {
        this(page, size, null);
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     * 
     * @param page
     *            zero-based page index.
     * @param size
     *            the size of the page to be returned.
     * @param direction
     *            the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties
     *            the properties to sort by, must not be {@literal null} or empty.
     */
    public PageParameter(int page, int size, Direction direction, String... properties) {
        this(page, size, new Sort(direction, properties));
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     * 
     * @param page
     *            zero-based page index.
     * @param size
     *            the size of the page to be returned.
     * @param sort
     *            can be {@literal null}.
     */
    public PageParameter(int page, int size, Sort sort) {
        if (page < 0) {
            throw new IllegalArgumentException("分页索引不能小于0");
        }

        if (size < 1) {
            throw new IllegalArgumentException("分页大小不能小于1");
        }
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }

    public Pageable next() {
        return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
    }

    public PageParameter previous() {
        return getPageNumber() == 0 ? this : new PageParameter(getPageNumber() - 1, getPageSize(), getSort());
    }

    public Pageable first() {
        return new PageParameter(0, getPageSize(), getSort());
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageParameter)) {
            return false;
        }

        PageParameter that = (PageParameter)obj;

        boolean sortEqual = this.sort == null ? that.sort == null : this.sort.equals(that.sort);

        return super.equals(that) && sortEqual;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + (null == sort ? 0 : sort.hashCode());
    }

    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(),
            sort == null ? null : sort.toString());
    }

}
