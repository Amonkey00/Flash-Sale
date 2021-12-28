package com.example.flashsale.dao;

import com.example.flashsale.pojo.Page;
import com.example.flashsale.pojo.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper {

    // GET
    public Record getRecordById(@Param("recordId")int id);
    public Record getRecordByOrderId(@Param("orderId")int id);
    public List<Record> getRecordList();
    public List<Record> getRecordListWithPage(Page page);
    public List<Record> getRecordListByUserId(@Param("userId")int id);
    public List<Record> getRecordListByProductId(@Param("productId")int id);
    public int countRecord();
    public int countRecordByUserId(@Param("userId")int id);
    public int countRecordByProductId(@Param("productId")int id);

    // INSERT
    public int addRecord(Record record);

    // UPDATE
    public int updateRecord(Record record);
}
