package com.jt.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.search.pojo.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tarena on 2016/11/16.
 */
@Component
public class RabbitItemService {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitItemService.class);
    private static final ObjectMapper MAPPER =new ObjectMapper();
    @Autowired
    private SearchService searchService;
    @Autowired
    private HttpClientService httpClientService;
    private String MANAGE_TAOTAO="http://manage.jt.com";

    public void saveOrUpdateItem(long itemId){
        LOGGER.info("saveOrUpdateItem接收到的消息为{}",itemId);
        String url=MANAGE_TAOTAO+"/item/query/"+itemId;
        Item item=null;
        try {
            String jsonResult=httpClientService.doGet(url);
            SysResult sysResult=MAPPER.readValue(jsonResult,SysResult.class);
            item= (Item) sysResult.getData();
        } catch (Exception e) {
            LOGGER.error("跟新Solr数据出错，itemId="+itemId);
        }
        if(null!=item){
            searchService.update(item);
        }
    }
}
