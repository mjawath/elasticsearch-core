package com.techstart.elasticcore.indexer;

//import com.techstart.commons.com.techstart.elasticcore.util.ReflectionUtil;
import com.techstart.elasticcore.util.Meta;
import com.techstart.elasticcore.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jawa on 11/7/2020.
 */
public class Indexer<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected ElasticIndexer<T> elasticIndexer;

    public String createIndex(T index){
        log("createIndex .............................");
        String uid = elasticIndexer.create(index);
        log("end createIndex  ............................."+uid);
        return uid;
    }

    public void updateIndex(T index){
        log("updateIndex.............................");
        elasticIndexer.update(index);
        log("end updateIndex .............................");
    }
    public List<T> findAll(){
        List<T> out = elasticIndexer.findAll();
        log(String.valueOf(out));
        return out;

    }

    public T findOutlet(String byId){
        T out = elasticIndexer.findById(byId);
        log(String.valueOf(out));
        return out;

    }
    public List<T> findOutlets(String category){
        elasticIndexer.findByProp(category);
        return null;

    }

    private void log(String msg){
        logger.info(msg);
    }

    @Autowired
    protected void setElasticIndexer(ElasticIndexer elasticIndexer) {
        this.elasticIndexer = elasticIndexer;
        final Class parameterisedBusinessClass = ReflectionUtil.getParameterisedBusinessClass(getClass());
        this.elasticIndexer.setaClass(parameterisedBusinessClass);


        Annotation[] annotations = parameterisedBusinessClass.getDeclaredAnnotations();
        if(annotations!=null && annotations.length>0 &&  annotations[0]!=null){
            final Annotation annotation = annotations[0];
            if(annotation!=null) {
                Meta meta = (Meta) annotation;
                this.elasticIndexer.setIndex(meta.index());
            }
        }
    }
}
