package site.guyw.grpg.service.personAct;

import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.PersonActRequest;
import site.guyw.grpg.common.PersonActResponse;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:18
 * @description 微信消息处理接口
 */
public interface PersonActService {

  public PersonActResponse invoke(PersonActRequest request);
}
