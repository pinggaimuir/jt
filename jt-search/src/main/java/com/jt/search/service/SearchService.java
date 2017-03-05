package com.jt.search.service;

import com.jt.common.vo.SysResult;
import com.jt.search.pojo.Item;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by tarena on 2016/11/16.
 */
@Service
public class SearchService {
    @Autowired
    private HttpSolrServer httpSolrServer;
    public SysResult search(String keyWords,Integer page,Integer rows){
        //构造搜索对象
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery(keyWords);
        //设置分页
        solrQuery.setStart((Math.max(1,page)-1)*rows);
        solrQuery.setRows(rows);
        //设置高亮显示
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<span class=\"red\">");
        solrQuery.setHighlightSimplePost("</span>");
        solrQuery.addHighlightField("title");

        try {
            QueryResponse queryResponse=httpSolrServer.query(solrQuery);
            List<Item> items=queryResponse.getBeans(Item.class);
            if(items==null||items.isEmpty()){
                return SysResult.build(200,"没有搜索到数据！");
            }
            Map<String,Map<String,List<String>>> map=queryResponse.getHighlighting();
            for(Map.Entry<String,Map<String,List<String>>> entry:map.entrySet()){
                for(Item item:items){
                    if(!entry.getKey().equals(item.getId().toString())){
                        continue;
                    }
                    item.setTitle(StringUtils.join(entry.getValue().get("title"),""));
                    break;
                }
            }
            return SysResult.build(200,String.valueOf(queryResponse.getResults().getNumFound()),items);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"搜索错误！");
    }
    /**
     * 更新solr中的错误
     */
    public SysResult update(Item item){
        try {
            httpSolrServer.addBean(item);
            httpSolrServer.commit();
            return SysResult.ok();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"更新solr数据失败！");
    }
}
