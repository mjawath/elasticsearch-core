package com.techstart.elasticcore.indexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by jawa on 11/7/2020.
 */
public interface Indexer<T>  {



    public  <S extends T> S createIndex(S index);

    public void updateIndex(T index) ;
    public List<T> findAll() ;

    public T findOutlet(String byId);

}
