/**
 *
 */

package com.weservice.catering.wtakeout.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 *
 *
 */
@ApiModel("分页信息")
@Data
@Slf4j
public class PageUtils<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    @ApiModelProperty
    private int totalCount;
    /**
     * 每页记录数
     */
    @ApiModelProperty
    private int pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty
    private int totalPage;
    /**
     * 当前页数
     */
    @ApiModelProperty
    private int currPage;
    /**
     * 列表数据
     */
    @ApiModelProperty("数据")
    private List<T> list;


    /**
     * 分页
     * @param list        列表数据
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param currPage    当前页数
     */
    public PageUtils(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public PageUtils(IPage<T> page) {
        this.list = page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }

    @SuppressWarnings("nochecked")
    public static <T> Page<T> generatePage(Map map) {
        log.info("page param:{}", map);
        Object page = map.get("page");
        Object limit = map.get("limit");
        if (null != page && null != limit) {
            Integer pageSize = Integer.valueOf((String) limit);
            Integer pageIndex = Integer.valueOf((String) page);
            return new Page<>(pageIndex, pageSize);
        }
        return new Page<>(1, 10);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
