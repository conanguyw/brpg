package site.guyw.grpg.service.game;

import org.springframework.stereotype.Service;
import site.guyw.grpg.enums.GameEnum;
import site.guyw.grpg.enums.PersonStatusEnum;
import site.guyw.grpg.manager.annotation.GameGateway;
import site.guyw.grpg.manager.annotation.PersonGateway;

/**
 * @author conangu(顾永威)
 * @createTime 2019-11-05 14:58
 * @description 狼人杀
 */
@Service
@GameGateway(method = GameEnum.WEREWOLF)
public class WereWolfGameImpl implements GameService{
    @Override
    public String init(String request) {
        return null;
    }

    @Override
    public String start(String request) {
        return null;
    }

    @Override
    public String next(String request) {
        return null;
    }
}
