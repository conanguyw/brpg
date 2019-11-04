package site.guyw.grpg.service.dealMsg;

import org.jetbrains.annotations.NotNull;
import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;
import site.guyw.grpg.common.WeChatMsgTypeEnum;
import site.guyw.grpg.manager.annotation.MsgGateway;

import java.util.Date;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:31
 * @description 文本消息处理
 */
@MsgGateway(method = WeChatMsgTypeEnum.TEXT)
public class TextMessageImpl extends MessageAbstractService implements DealWeChatMessageService{
    @Override
    public OutMsgEntity invoke(InMsgEntity msg) {
        OutMsgEntity out = buildOutMsgEntity(msg);
        //用户发送的内容
        String inContent = msg.getContent();
        //公众号回复的内容
        String outContent = null;
        //关键字判断
        if(inContent.contains("开始")){
            outContent = "";
        }else if(inContent.contains("地址")){
            outContent = "";
        }else{
            //用户发什么就回复什么
            outContent = inContent;
        }
        //设置消息的响应类型
        out.setMsgType("text");
        out.setContent(outContent);
        return out;
    }


}
