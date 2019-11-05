package site.guyw.grpg.service.dealMsg;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import site.guyw.grpg.cache.Person;
import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;
import site.guyw.grpg.common.PersonActRequest;
import site.guyw.grpg.common.PersonActResponse;
import site.guyw.grpg.enums.PersonStatusEnum;
import site.guyw.grpg.enums.WeChatMsgTypeEnum;
import site.guyw.grpg.manager.annotation.MsgGateway;
import site.guyw.grpg.manager.gateway.DefaultGatewayServiceProvider;
import site.guyw.grpg.service.personAct.PersonActService;

import javax.annotation.Resource;

import static site.guyw.grpg.cache.SimpleCache.getCache;
import static site.guyw.grpg.cache.SimpleCache.setCache;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:31
 * @description 文本消息处理
 */
@Service
@MsgGateway(method = WeChatMsgTypeEnum.TEXT)
public class TextMessageImpl extends MessageAbstractService implements DealWeChatMessageService {
    /** 服务网关提供商 */
    @Resource
    private DefaultGatewayServiceProvider gatewayServiceProvider;

    @Override
    public OutMsgEntity invoke(InMsgEntity msg) {
        OutMsgEntity out = buildOutMsgEntity(msg);
        String outContent;
        Person person = getPerson(msg);
        PersonActService messageService = gatewayServiceProvider.getPersonService(person.getStatus());
        PersonActRequest request = new PersonActRequest();
        request.setContent(msg.getContent());
        PersonActResponse response = messageService.invoke(request);
        //公众号回复的内容
        outContent = response.getContent();

        //设置消息的响应类型
        out.setMsgType("text");
        out.setContent(outContent);
        return out;
    }



}
