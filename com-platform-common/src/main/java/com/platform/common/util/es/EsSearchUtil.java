package com.platform.common.util.es;


import com.alibaba.fastjson.JSON;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EsSearchUtil {
    private static final RequestOptions defaultOptions = RequestOptions.DEFAULT;


    /**
     * 查询
     *
     * @param indices     索引名
     * @param params      map参数
     * @param filterQuery 字符串参数
     * @param rangeQuery  区间参数
     * @param sort        排序
     * @param pageIndex   第X页(从1开始)
     * @param pageSize    每页条数
     * @return
     */
    public static <T> EsResult<T> search(String indices, Map<String, Object> params, String filterQuery, List<RangeQueryEntity> rangeQuery, List<SortEntity> sort, int pageIndex, int pageSize, Class<T> clazz) {
        EsSearchResult esSearchResult = search(indices, params, sort, filterQuery, null, null, SearchContants.ANALYZER_IK_SMART, rangeQuery, pageIndex, pageSize);
        EsResult<T> esResult = new EsResult<>();
        esResult.setTotal(esSearchResult.getTotal());
        //if(esSearchResult.getData()!=null && esSearchResult.getData().size()>0){
        //
        //}
        esResult.setData(new ArrayList<>());
        for (int i = 0; i < esSearchResult.getData().size(); i++) {
            try {
                T obj = JSON.parseObject(esSearchResult.getData().get(i), clazz);
                esResult.getData().add(obj);
            } catch (Exception ex) {
                throw new RuntimeException("ES转换查询结果异常");
            }

        }
        return esResult;

    }

    /**
     * 查询
     *
     * @param indices          索引名
     * @param params           map参数
     * @param sort             排序
     * @param filterQuery      字符串参数
     * @param nestParams       嵌套map参数
     * @param nestFilterString 嵌套字符串参数
     * @param anaylzer         查询分词器
     * @param pageIndex        第X页(从1开始)
     * @param pageSize         每页条数
     * @return
     */
    public static EsSearchResult search(String indices, Map<String, Object> params, List<SortEntity> sort, String filterQuery, Map<String, Map<String, Object>> nestParams, Map<String, String> nestFilterString, String anaylzer, List<RangeQueryEntity> rangeQuery, int pageIndex, int pageSize) {
        RestHighLevelClient client = RestClientUtil.highClient();
        SearchRequest request = new SearchRequest(indices);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from((pageIndex - 1) * pageSize);
        builder.size(pageSize);
        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();

        if (params != null && !params.isEmpty()) {
            String query = queryStr(params);
            QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(query);
            if (!StringUtils.isEmpty(anaylzer)) {
                queryStringQueryBuilder.analyzer(anaylzer);
            }
            booleanQueryBuilder.must(queryStringQueryBuilder);
        }
        if (!StringUtils.isEmpty(filterQuery)) {
            QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(filterQuery);
            if (!StringUtils.isEmpty(anaylzer)) {
                queryStringQueryBuilder.analyzer(anaylzer);
            }
            booleanQueryBuilder.must(queryStringQueryBuilder);
        }
        if (nestParams != null && !nestParams.isEmpty()) {
            nestParams.entrySet().forEach((nest) -> {
                String query = queryStr((Map) nest.getValue());
                booleanQueryBuilder.must(QueryBuilders.nestedQuery((String) nest.getKey(), QueryBuilders.queryStringQuery(query), ScoreMode.None));
            });
        }
        if (nestFilterString != null && !nestFilterString.isEmpty()) {
            nestFilterString.entrySet().forEach((nest) -> {
                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery((String) nest.getKey(), QueryBuilders.queryStringQuery((String) nest.getValue()), ScoreMode.None);
                booleanQueryBuilder.must(nestedQueryBuilder);
            });
        }
        if (rangeQuery != null && rangeQuery.size() > 0) {
            for (RangeQueryEntity rangeQueryEntity : rangeQuery) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeQueryEntity.getFieldName());
                if (rangeQueryEntity.getMinValue() != null) {
                    rangeQueryBuilder.gte(rangeQueryEntity.getMinValue());
                }
                if (rangeQueryEntity.getMaxValue() != null) {
                    rangeQueryBuilder.lte(rangeQueryEntity.getMaxValue());
                }
                booleanQueryBuilder.must(rangeQueryBuilder);
            }

        }

        builder.query(booleanQueryBuilder);
        if (sort != null && !sort.isEmpty()) {
            sort(builder, sort);
        }

        request.source(builder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(request, defaultOptions);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        EsSearchResult esSearchResult = new EsSearchResult();
        if (searchResponse == null) {
            esSearchResult.setTotal(0);
            esSearchResult.setData(new ArrayList<>());
            return esSearchResult;
        }

        SearchHits hits = searchResponse.getHits();
        esSearchResult.setTotal(hits.getTotalHits().value);
        //打印结果
        List<String> sources = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            sources.add(hit.getSourceAsString());
        }
        esSearchResult.setData(sources);
        return esSearchResult;
    }


    public static String queryStr(Map<String, Object> params) {
        StringBuilder qs = new StringBuilder();
        Iterator var2 = params.entrySet().iterator();

        while (true) {
            String key;
            Object[] values;
            do {
                while (true) {
                    Object o;
                    do {
                        if (!var2.hasNext()) {
                            return qs.toString();
                        }

                        Map.Entry<String, Object> m = (Map.Entry) var2.next();
                        o = m.getValue();
                        key = (String) m.getKey();
                    } while (StringUtils.isEmpty(o));

                    if (o.getClass().isArray()) {
                        values = (Object[]) ((Object[]) o);
                        break;
                    }

                    if (qs.length() != 0) {
                        qs.append(" AND ");
                    }

                    qs.append(key + ":" + o);
                }
            } while (values.length == 0);

            if (qs.length() > 0) {
                qs.append(" AND ");
            }

            qs.append("( ");

            for (int i = 0; i < values.length; ++i) {
                if (i > 0) {
                    qs.append(" OR ");
                }

                qs.append(key + ":" + values[i]);
            }

            qs.append(")");
        }
    }

    private static void sort(SearchSourceBuilder builder, List<SortEntity> sort) {
        for (SortEntity sortEntity : sort) {
            SortBuilder sortBuilder = SortBuilders.fieldSort(sortEntity.getKey()).order(sortEntity.getSortOrder());
            builder.sort(sortBuilder);
        }
    }

    public static <T> EsResult<T> search(String indices, String field, List<String> values, Class<T> clazz) {
        EsResult<T> esResult = new EsResult<>();
        if (values == null || values.size() == 0) {
            esResult.setTotal(0);
            esResult.setData(new ArrayList<>());
            return esResult;
        }
        RestHighLevelClient client = RestClientUtil.highClient();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        booleanQueryBuilder.should(new TermsQueryBuilder(field, values));
        builder.from(0);
        builder.size(values.size());
        builder.query(booleanQueryBuilder);
        SearchRequest request = new SearchRequest(indices);
        request.source(builder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(request, defaultOptions);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (searchResponse == null) {
            esResult.setTotal(0);
            esResult.setData(new ArrayList<>());
            return esResult;
        }
        SearchHits hits = searchResponse.getHits();
        esResult.setTotal(hits.getTotalHits().value);
        esResult.setData(new ArrayList<>());
        for (SearchHit hit : hits.getHits()) {
            try {
                T obj = JSON.parseObject(hit.getSourceAsString(), clazz);
                esResult.getData().add(obj);
            } catch (Exception ex) {
                throw new RuntimeException("ES转换查询结果异常");
            }
        }
        return esResult;
    }

}
