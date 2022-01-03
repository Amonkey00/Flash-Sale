package com.example.flashsale.controller;

import com.example.flashsale.pojo.Record;
import com.example.flashsale.service.RecordService;
import com.example.flashsale.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping("/getList")
    public JsonResult getList(){
        List<Record> recordList = recordService.getRecordList();
        return new JsonResult<>(recordList, "Operation succeed", 0);
    }
}
