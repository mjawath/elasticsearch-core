package com.techstart.elasticcore.indexer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jawa on 11/4/2020.
 */
@Service
public class OutletIndexer extends Indexer<OutletDTO> {

    public List<OutletDTO> findOutletsNearby(LocationDTO pin,String range){
        logger.info(pin + range);
        return elasticIndexer.findNearBy(pin,range);

    }


    @Autowired
    protected void setElasticIndexer(ElasticIndexer elasticIndexer) {
        super.setElasticIndexer(elasticIndexer);
//        elasticIndexer.setIndex("shops");
    }
}