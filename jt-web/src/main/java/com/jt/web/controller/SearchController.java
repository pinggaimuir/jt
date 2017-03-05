package com.jt.web.controller;

import com.jt.common.vo.SysResult;
import com.jt.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

/**
 * Created by tarena on 2016/11/16.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping("search")
    public ModelAndView search(@RequestParam("q") String keyWords,
                               @RequestParam(value="page",defaultValue="1") Integer page){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("search");

        try {
            keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SysResult result=searchService.search(keyWords, page);
        mv.addObject("itemList",result.getData());

        Integer total = Integer.valueOf(result.getMsg());
        Integer pages = (total - 1 + SearchService.SEARCH_TAOTAO_COUNT) / SearchService.SEARCH_TAOTAO_COUNT;
        mv.addObject("pages", pages);
        mv.addObject("page", page);


        mv.addObject("query", keyWords);
        return mv;
    }
}
