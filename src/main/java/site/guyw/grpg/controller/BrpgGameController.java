package site.guyw.grpg.controller;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.guyw.grpg.cache.Person;
import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;
import site.guyw.grpg.enums.WeChatMsgTypeEnum;
import site.guyw.grpg.manager.gateway.DefaultGatewayServiceProvider;
import site.guyw.grpg.service.dealMsg.DealWeChatMessageService;

import javax.annotation.Resource;

/**
 * @author conangu(顾永威)
 * @createTime 2019-11-04 16:15
 * @description 游戏
 */
@Controller
@EnableCaching
public class BrpgGameController {
    /** 服务网关提供商 */
    @Resource
    private DefaultGatewayServiceProvider gatewayServiceProvider;

    /**
     * 微信消息处理
     */
    @RequestMapping(value = "/brpg", method = RequestMethod.POST)
    @ResponseBody
    public OutMsgEntity handleMessage(@RequestBody InMsgEntity msg) {

        //获取接收的消息类型

        DealWeChatMessageService messageService = gatewayServiceProvider.getMsgService(WeChatMsgTypeEnum.getEnumByCode(msg.getMsgType()));
        return messageService.invoke(msg);

    }

    /**
     * 微信消息处理
     */
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    @ResponseBody
    public Person person(@RequestParam("openid") String openid) {

        return new Person();
    }

}
