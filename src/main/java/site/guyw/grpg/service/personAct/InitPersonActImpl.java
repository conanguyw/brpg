package site.guyw.grpg.service.personAct;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import site.guyw.grpg.cache.SimpleCache;
import site.guyw.grpg.cache.Team;
import site.guyw.grpg.common.PersonActRequest;
import site.guyw.grpg.common.PersonActResponse;
import site.guyw.grpg.enums.PersonStatusEnum;
import site.guyw.grpg.manager.annotation.PersonGateway;

/**
 * @author conangu(顾永威)
 * @createTime 2019-11-05 11:26
 * @description 初始化人物回复
 */
@Service
@PersonGateway(method = PersonStatusEnum.INIT)
public class InitPersonActImpl implements PersonActService{
    @Override
    public PersonActResponse invoke(PersonActRequest request) {
        PersonActResponse response = new PersonActResponse();
        response.setContent("请选择游戏,或输入正确的房间号");
        if (NumberUtils.isCreatable(request.getContent())){
            Team team = SimpleCache.getCache(request.getContent());
            if (team == null){
                response.setContent("房间号"+request.getContent()+"不存在，请重新输入");
            }


        }

        return response;
    }
}
