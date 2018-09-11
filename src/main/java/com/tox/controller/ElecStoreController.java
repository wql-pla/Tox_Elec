package com.tox.controller;

import com.tox.bean.ElecStore;
import com.tox.bean.Message;
import com.tox.bean.PageResponse;
import com.tox.service.ElecStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value = "/store")
public class ElecStoreController extends  BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ElecStoreController.class);
    @Autowired
    private ElecStoreService storeService;

    /**
     * 新增和编辑
     * @param elecStore
     * @return
     */
    @RequestMapping(value = "addOrEdit")
    @ResponseBody
    public  Message<ElecStore> addElecStore(@RequestBody ElecStore elecStore){
            Message<ElecStore>msg=new Message<>();
            try{
                elecStore =  storeService.addOrEdit(elecStore);
                msg.setData(elecStore);
            }catch (Exception e){
                e.printStackTrace();
                return createFail(msg,e.getMessage());
            }
        return createSuccessMsg(msg);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public Message<ElecStore> deleteElecStore(@PathVariable Integer id){
        Message<ElecStore>msg=new Message<>();
        try {
            storeService.del(id);
        }catch (Exception e){
            e.printStackTrace();
            return createFail(msg,e.getMessage());
        }

        return createSuccessMsg(msg);
    }

    /**
     * 查询列表
     * @param elecStore
     * @return
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public Message<PageResponse<ElecStore>> list(@RequestBody ElecStore elecStore){
        Message<PageResponse<ElecStore>> msg=new Message<>();

        try {
           PageResponse<ElecStore> pageResponse=storeService.list(elecStore);
           msg.setData(pageResponse);
        }catch (Exception e){
            e.printStackTrace();
            return createFail(msg,e.getMessage());
        }

        return createSuccessMsg(msg);
    }

    /**
     * 门店名称列表
     * @return
     */
    @RequestMapping(value = "nameList")
    @ResponseBody
    public Message<List<ElecStore>> nameList(){
    Message<List<ElecStore>>msg=new Message<>();
        try {
          List<ElecStore>list=storeService.nameList();
           msg.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            return createFail(msg,e.getMessage());
        }
        return  createSuccessMsg(msg);
    }



}
