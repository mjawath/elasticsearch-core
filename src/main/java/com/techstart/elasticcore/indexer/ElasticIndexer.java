package com.techstart.elasticcore.indexer;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by jawa on 11/4/2020.
 */
@Component//sort of repository
public class ElasticIndexer<T> {

    private String index;
    private IndexCoordinates indexCoordinates;//=IndexCoordinates.of(index);
    private Class<T> aClass;

    private static final int paginationSize = 20;

    public ElasticIndexer() {
        System.out.println("ElasticIndexer");
//        ReflectionUtil.getParameterisedBusinessClass(this.getClass());
    }

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    private ElasticsearchRestTemplate restTemplate;

    public String create(T dto) {

        System.out.println("create elastic");
        //elastic specific

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withObject(dto)
                .build();
        String documentId = elasticsearchOperations.index(indexQuery, indexCoordinates);
        return documentId;
    }


    public void update(T dto) {
//        restTemplate.update()
        System.out.println("update elastic");
//        UpdateQuery  indexQuery =  UpdateQuery.builder("")
//                .withDocument(new Document().)
//                .build();
        final T save = elasticsearchOperations.save(dto, indexCoordinates);

    }

    public T findById(String id) {
        T ob = elasticsearchOperations
                .get(id, aClass, indexCoordinates);
        return ob;
    }

    public List<T> findAll() {
        String filter =
                "   {\n" +
                        "        \"match_all\": {}\n" +
                        "    }\n";

        Query query = new StringQuery(filter).setPageable(PageRequest.of(0, paginationSize));

        SearchHits<T> searchHits = elasticsearchOperations.search(query, aClass, indexCoordinates);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());//forEach(tSearchHit ->   ls.add(tSearchHit.getContent()));
    }

    public Object findByProp(String id) {
        Object ob = elasticsearchOperations
                .get(id, OutletDTO.class);

        return ob;
    }

    public void setIndex(String index) {
        this.index = index;
        this.indexCoordinates = IndexCoordinates.of(index);
    }

    /**
     * GEO query to find near item
     * Should refector
     *
     * @param pin
     * @return
     */

    public List<T> findNearBy(LocationDTO pin, String range) {

        String distance = "12";
        distance = range == null ? distance : range;

//        String filter = "{\"bool\": {" +
//                "      \"must\": { " +
//                "        \"match_all\": {}" +
//                "      }," +
//                "      \"filter\": {" +
//                "        \"geo_distance\": {" +
//                "          \"distance\": \"" + distance + "\"," +
//                "          \"location.geoPoint\": { \"lat\":" + pin.getGeoPoint().lat() + " ," +
//                "                                   \"lon\": " + pin.getGeoPoint().lon() + " } " +
//                "        }" +
//                "      }" +
//                "    }}";
//
//        Query query = new StringQuery(filter).setPageable(PageRequest.of(0, paginationSize));
//        GeoDistanceFilterBuilder filter = FilterBuilders.geoDistanceFilter("location").point(latitude, longitude).distance(distance, DistanceUnit.KILOMETERS);

//        GeoDistanceQueryBuilder  filter = new GeoDistanceQueryBuilder("location.geoPoint")
//                .distance(distance)geoDistanceFilter("location").point(latitude, longitude).distance(distance, DistanceUnit.KILOMETERS);

        final String fieldName = "location.geoPoint";
        Query queryx = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withFilter(new GeoDistanceQueryBuilder(fieldName)
                        .distance(distance, DistanceUnit.KILOMETERS).point(pin.getGeoPoint()))
                .withSort(SortBuilders.geoDistanceSort(fieldName,pin.getGeoPoint()).order(SortOrder.ASC))
                .build();


        SearchHits<T> searchHits = elasticsearchOperations.search(queryx, aClass, indexCoordinates);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());//forEach(tSearchHit ->   ls.add(tSearchHit.getContent()));
    }


    public List<T> findNearBy(String lat, String lon, String range) {

        String distance = "12km";

        String filter = "{\"bool\": {\n" +
                "      \"must\": {\n" +
                "        \"match_all\": {}\n" +
                "      },\n" +
                "      \"filter\": {\n" +
                "        \"geo_distance\": {\n" +
                "          \"distance\": \"" + range + "\",\n" +
                "          \"location.location\": {\" lat:" + lat + ", long:" + lon + "\"\n}" +
                "        }\n" +
                "      }\n" +
                "    }}";

        Query query = new StringQuery(filter).setPageable(PageRequest.of(0, paginationSize));

        SearchHits<T> searchHits = elasticsearchOperations.search(query, aClass, indexCoordinates);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());//forEach(tSearchHit ->   ls.add(tSearchHit.getContent()));
    }


    public void setaClass(Class<T> aClass) {
        this.aClass = aClass;
    }

}
