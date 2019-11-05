package site.guyw.grpg.service.personAct;

import org.springframework.stereotype.Service;

import site.guyw.grpg.common.PersonActRequest;
import site.guyw.grpg.common.PersonActResponse;
import site.guyw.grpg.enums.PersonStatusEnum;
import site.guyw.grpg.manager.annotation.PersonGateway;

/**
 * @author conangu(顾永威)
 * @createTime 2019-11-05 11:26
 * @description 已初始化人物回复
 */
@Service
@PersonGateway(method = PersonStatusEnum.INITED)
public class InitedPersonActImpl implements PersonActService{
    @Override
    public PersonActResponse invoke(PersonActRequest request) {
        PersonActResponse response = new PersonActResponse();
        response.setContent("请输入游戏人数");
        return response;
    }
}
