package com.jt.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarena on 2016/11/16.
 */
@Service
public class SearchService {
    public static Integer SEARCH_TAOTAO_COUNT=30;
    private static final ObjectMapper MAPPER=new ObjectMapper();
    @Autowired
    private HttpClientService httpClientService;
    public SysResult search(String keyWords,Integer page){
        Map<String,String> map=new HashMap();
        map.put("keyWords",keyWords);
        map.put("page",page+"");
        map.put("rows",SEARCH_TAOTAO_COUNT+"");
        try {
            String json= httpClientService.doPost("http://search.jt.com/search",map);
            System.out.println("WEb-----------------search"+json);
            if(json!=null){
                return SysResult.formatToList(json,Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(500,"搜索错误~！");
    }
}
