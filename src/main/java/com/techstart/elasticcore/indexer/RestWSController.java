package com.techstart.elasticcore.indexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by jawa on 11/8/2020.
 */
public class RestWSController<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Indexer<T> indexer;

    @PostMapping(path = {"","/"})
    public ResponseEntity create(@RequestBody T dto) {
        log("create post data relieved " + dto);
        String resultObject =  indexer.createIndex(dto);
        return new ResponseEntity(resultObject, HttpStatus.CREATED);
    }

    @PutMapping(path = {"","/"})
    public ResponseEntity update(@RequestBody T dto) {
        log("update " + dto);
        indexer.updateIndex(dto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<T>  get(@PathVariable("id") String id) {
        T outlet = indexer.findOutlet(id);
        if(outlet==null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(outlet);
        }
    }

    private void log(String msg){
        logger.info(msg);
    }

    @GetMapping("/")
    public ResponseEntity<List<T>> getFindAll(){
        log("getFindAll");
        final List<T> all = indexer.findAll();

        if(all.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    public void setIndexer(Indexer<T> indexer) {
        this.indexer = indexer;
    }
}
