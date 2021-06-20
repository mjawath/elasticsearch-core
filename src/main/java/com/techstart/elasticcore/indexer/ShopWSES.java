package com.techstart.elasticcore.indexer;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//import com.techstart.elasticcore.util.ReflectionUtil;
//import static com.test.bootapp.controller.APIConstant.SHOPSWS_API_PATH;

/**
 * Created by jawa on 11/7/2020.
 */
@RestController("ShopESController")
@RequestMapping("/shops")
public class ShopWSES extends RestWSController<OutletDTO> {

    @Autowired
    private OutletIndexer indexer;

    private ShopWSES(OutletIndexer indexer){
        this.indexer = indexer;
        setIndexer(indexer);
    }


    @RequestMapping("/pin/{pin}")
    public ResponseEntity<List<OutletDTO>>  getByGPS(@PathVariable("pin") String pin,@RequestParam(required = false) String range) {
        //parse pin
        LocationDTO pin1 = new LocationDTO();
        if(pin.contains("@") && pin.contains(",") && pin.matches("^@([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$")){
            final String[] gpss = pin.split(",");
            pin1.setLat(Double.parseDouble(gpss[0].replace("@","")));
            pin1.setLng(Double.parseDouble(gpss[1]));
            pin1.setGeoPoint(new GeoPoint(pin1.getLat(),pin1.getLng()));
        }
        List<OutletDTO> dtos = indexer.findOutletsNearby(pin1,range);
        if(dtos==null || dtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(dtos);
        }
    }

}
