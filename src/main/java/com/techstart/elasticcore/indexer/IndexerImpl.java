package com.techstart.elasticcore.indexer;

import com.techstart.elasticcore.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jawa on 11/7/2020.
 */
public class IndexerImpl<T> implements Indexer<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ElasticGenericRepository<T> elasticGenericRepository;
    protected ElasticsearchRepository<T, ?> repository;

    public IndexerImpl() {
    }

    public IndexerImpl(ElasticsearchRepository<T, ?> repository) {
        this.repository = repository;
    }

    public <S extends T> S createIndex(S index) {
        log("createIndex .............................");
        S uid = repository == null ? elasticGenericRepository.create(index) : repository.save(index);
        log("end createIndex  ............................." + uid);
        return uid;
    }

    public void updateIndex(T index) {
        log("updateIndex.............................");
        elasticGenericRepository.update(index);
        log("end updateIndex .............................");
    }

    public List<T> findAll() {
        List<T> tmp = new ArrayList<>();
        Iterable<T> all = new ArrayList<>();
        if (repository != null) {
            all = repository.findAll();
        } else if (elasticGenericRepository != null) {
            all = elasticGenericRepository.findAll();
        } else throw new RuntimeException("no repository specified");
        all.forEach(tmp::add);
        log("items found #" + tmp.size());
        return tmp;
    }

    public T findOutlet(String byId) {
        T out = elasticGenericRepository.findById(byId);
        log(String.valueOf(out));
        return out;

    }

    private void log(String msg) {
        logger.info(msg);
    }

    @PostConstruct
    private void postCon() {
//        elasticGenericRepository = new ElasticGenericRepository<>();
//        elasticGenericRepository.setIndexDetails();
        elasticGenericRepository.setIndexDetails(getClass());

    }

}
