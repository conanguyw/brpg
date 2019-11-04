package site.guyw.grpg.service.dealMsg;

import org.springframework.stereotype.Service;
import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;
import site.guyw.grpg.common.WeChatMsgTypeEnum;
import site.guyw.grpg.manager.annotation.MsgGateway;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:31
 * @description 文本消息处理
 */
@Service
@MsgGateway(method = WeChatMsgTypeEnum.EVENT)
public class EventMessageImpl extends MessageAbstractService implements DealWeChatMessageService{
    @Override
    public OutMsgEntity invoke(InMsgEntity msg) {
        OutMsgEntity out = buildOutMsgEntity(msg);
        //用户发送的内容
        String inContent = msg.getContent();
        //判断关注事件
        if("subscribe".equals(msg.getEvent())){
            out.setContent("欢迎关注![愉快]");
            //设置消息的响应类型
            out.setMsgType("text");
        }
        out.setMsgType("text");
        return out;
    }


}
