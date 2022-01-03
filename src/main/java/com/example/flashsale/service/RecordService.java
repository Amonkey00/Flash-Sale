package com.example.flashsale.service;

import com.example.flashsale.dao.RecordMapper;
import com.example.flashsale.pojo.Product;
import com.example.flashsale.pojo.Record;
import com.example.flashsale.pojo.User;
import com.example.flashsale.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    RecordMapper recordMapper;

    public int addRecord(Record record){
        return recordMapper.addRecord(record);
    }

    /**
     * Methods to get products
     */
    public List<Record> getRecordList(){
        return recordMapper.getRecordList();
    }
    public List<Record> getRecordList(User user){
        return recordMapper.getRecordListByUserId(user.getUid());
    }
    public List<Record> getRecordList(Product product){
        return recordMapper.getRecordListByProductId(product.getPid());
    }
}
