package site.guyw.grpg.service.dealMsg;

import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:18
 * @description 微信消息处理接口
 */
public interface DealWeChatMessageService {

  public OutMsgEntity invoke(InMsgEntity msg);
}
