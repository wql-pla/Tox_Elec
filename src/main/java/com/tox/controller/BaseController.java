package com.tox.controller;



import com.tox.bean.Message;
import com.tox.utils.MessageCode;
import org.apache.commons.lang.StringUtils;
import sun.misc.resources.Messages_es;

/**
 *  Controller基类,简单封装的成功返回的消息和失败返回的消息
 */
public class BaseController {

    /**
     * 成功返回的消息
     * @param message

     * @return
     */
    protected Message createSuccessMsg(Message  message){
        message.setCode(MessageCode.SUCCESS);

        return message;
    }

    /**
     *  失败返回的消息
     * @param message
     * @param msg
     * @return
     */
    protected Message createFail(Message message,String msg) {
        message.setCode(MessageCode.FAILURE);
        if(StringUtils.isEmpty(msg)){
            message.setMsg("请求异常");
        }else{
            message.setMsg(msg);
        }
        return message;
    }


}
