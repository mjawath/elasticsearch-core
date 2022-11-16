package com.techstart.elasticcore.indexer;

import org.springframework.stereotype.Service;

/**
 * Created by jawa on 11/7/2020.
 */
@Service
public class IndexerTest {


//    @Autowired
    private ElasticGenericRepository<OutletDTO> elasticGenericRepository;


//    @Autowired
    private OutletIndexer outletIndexer;

    public void createIndex(){
        System.out.println("indexer .............................");


        OutletDTO out = new OutletDTO();
//        out.setId("100");
        out.setDescription("coffee shop best coffee");
        out.setCategory("coffee");
        out.setName("star1");

        LocationDTO lc = new LocationDTO();
//        lc.setCity("Bedok");
//        lc.setCountry("SG");
//        lc.setLocation("w21zmrea");
        elasticGenericRepository.create(out);


        OutletDTO dto = new OutletDTO();
        dto.setDescription("periaya object");
        LocationDTO lc1 = new LocationDTO();
//        lc1.setCity("bedok");
//        lc1.setCountry("SG");
//        lc1.setLatitude(40.265635d);
//        lc1.setLongitude(40.255635d);
//        lc1.setLocation("w21zmrdhy");
        dto.setLocation(lc1);

        OutletDTO uid = elasticGenericRepository.create(dto);
        System.out.println("end indexer .............................");
        System.out.println(uid);

    }

    public void update(){
//        outletIndexer.up
    }
}
