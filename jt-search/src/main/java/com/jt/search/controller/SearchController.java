package com.jt.search.controller;

import com.jt.common.vo.SysResult;
import com.jt.search.pojo.Item;
import com.jt.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tarena on 2016/11/16.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 通过关键字搜索
     * @param keyWords 关键字
     * @param page 当前页
     * @param rows 每页记录数
     * @return
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public SysResult search(String keyWords,Integer page,Integer rows){
        return searchService.search(keyWords,page,rows);
    }

    /**
     *  更新solr数据
     * @return
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
    @ResponseBody
    public SysResult update(@RequestBody Item item){
        return searchService.update(item);
    }
}
