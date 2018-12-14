package com.liaoin.message;


import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liujun on 2015/1/19.
 */
public class PageResult<T>{
    /**
     * 符合条件的总记录数
     */
    private long totalRecord = 0;

    /**
     * 数据
     */
    private List<T> list;

    /**
     * 每页条数
     */
    private int eachPageCount;

    /**
     * 总页数
     */
    private int totalPageCount;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 首页链接
     * @return
     */
    private String firstPageUrl;

    /**
     * 尾页链接
     * @return
     */
    private String endPageUrl;

    /**
     * 上一页
     * @return
     */
    private String beforePageUrl;

    /**
     * 下一页
     * @return
     */
    private String nextPageUrl;

    /**
     * 跳页
     * @return
     */
    private String searchPageUrl;

    /**
     * 每页显示的页数
     */
    private int rollPage = 5;

    private List<Integer> pageNumList = new ArrayList<Integer>();

    private String url;

    public PageResult() {
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public List<T> getList() {
        return list;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getEachPageCount() {
        return eachPageCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setEachPageCount(int eachPageCount) {
        this.eachPageCount = eachPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    public String getEndPageUrl() {
        return endPageUrl;
    }

    public void setEndPageUrl(String endPageUrl) {
        this.endPageUrl = endPageUrl;
    }

    public String getBeforePageUrl() {
        return beforePageUrl;
    }

    public void setBeforePageUrl(String beforePageUrl) {
        this.beforePageUrl = beforePageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String

    getSearchPageUrl() {
        return searchPageUrl;
    }

    public void setSearchPageUrl(String searchPageUrl) {
        this.searchPageUrl = searchPageUrl;
    }

    /**
     * 设置链接
     */
    public PageResult<T> setPageUrl(HttpServletRequest request, int currentPage, int pageCount, Page<T> page){
        currentPage = currentPage + 1;
        String httpUrl = request.getRequestURL().toString();
        //总页数
        int totalPage = page.getTotalPages();

        PageResult<T> pageResult = new PageResult<T>();
        //设置总条数
        pageResult.setTotalRecord(page.getTotalElements());
        //设置数据
        pageResult.setList(page.getContent());
        //设置当前页
        pageResult.setCurrentPage(currentPage);
        //设置总页数
        pageResult.setTotalPageCount(totalPage);
        //设置每页条数
        pageResult.setEachPageCount(pageCount - 1);

        pageResult.setUrl(httpUrl + "?");
        //设置首页
        pageResult.setFirstPageUrl(httpUrl + "?p=1");
        //设置尾页
        pageResult.setEndPageUrl(httpUrl + "?p=" + totalPage);
        //设置上一页
        if(currentPage > 1){
            pageResult.setBeforePageUrl(httpUrl + "?p=" + (currentPage - 1));
        } else {
            pageResult.setBeforePageUrl(httpUrl + "?p=1");
            pageResult.setNextPageUrl(httpUrl + "?p=1");
        }
        //设置下一页
        if(currentPage < totalPage){
            pageResult.setNextPageUrl(httpUrl + "?p=" + (currentPage + 1));
        }else{
            pageResult.setNextPageUrl(httpUrl + "?p=" + totalPage);
        }
        pageResult.setSearchPageUrl(httpUrl + "?");
        Map<String ,String[]> map = request.getParameterMap();
        if(map.size() > 0){
            for (String key : map.keySet()) {
                if(!key.equals("p")){

                    String[] strings = map.get(key);
                    String searcher = "";
                    try{
                        searcher = new String(strings[0].getBytes("ISO-8859-1"),"UTF-8");
                    }catch ( Exception e){

                    }
                    pageResult.setFirstPageUrl(pageResult.getFirstPageUrl() + "&" + key + "=" + searcher);
                    pageResult.setBeforePageUrl(pageResult.getBeforePageUrl() + "&" + key + "=" + searcher);
                    pageResult.setNextPageUrl(pageResult.getNextPageUrl() + "&" + key + "=" + searcher);
                    pageResult.setEndPageUrl(pageResult.getEndPageUrl() + "&" + key + "=" + searcher);
                    pageResult.setSearchPageUrl(pageResult.getSearchPageUrl() + "&" + key + "=" + searcher);
                    pageResult.setUrl(pageResult.getUrl() + "&" + key + "=" + searcher);
                }
            }
        }

        pageResult.getPageNumList();
        return  pageResult;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRollPage() {
        return rollPage;
    }

    public void setRollPage(int rollPage) {
        this.rollPage = rollPage;
    }

    public void setPageNumList(List<Integer> pageNumList) {
        this.pageNumList = pageNumList;
    }

    // 得到页面列表
    public List<Integer> getPageNumList() {
        this.pageNumList.removeAll(this.pageNumList);// 设置之前先清空
        if (this.totalPageCount > this.rollPage) {
            int halfSize = this.rollPage / 2;
            int first = 1;
            int end = 1;
            if (this.currentPage - halfSize < 1) { // 当前页靠近最小数1
                first = 1;
                end = this.rollPage;
            } else if (this.totalPageCount - this.currentPage < halfSize) { // 当前页靠近最大数
                first = this.totalPageCount - this.rollPage + 1;
                end = this.totalPageCount;
            } else {
                first = this.currentPage - halfSize;
                end = this.currentPage + halfSize;
            }
            for (int i = first; i <= end; i++) {
                this.pageNumList.add(i);
            }
        } else {
            for (int i = 0; i < this.totalPageCount; i++) {
                this.pageNumList.add(i + 1);
            }
        }

        return pageNumList;
    }
}
