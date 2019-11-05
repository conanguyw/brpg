/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package site.guyw.grpg.manager.gateway;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import site.guyw.grpg.enums.GameEnum;
import site.guyw.grpg.enums.PersonStatusEnum;
import site.guyw.grpg.enums.WeChatMsgTypeEnum;
import site.guyw.grpg.manager.annotation.GameGateway;
import site.guyw.grpg.manager.annotation.MsgGateway;
import site.guyw.grpg.manager.annotation.PersonGateway;
import site.guyw.grpg.service.dealMsg.DealWeChatMessageService;
import site.guyw.grpg.service.game.GameService;
import site.guyw.grpg.service.personAct.PersonActService;
import site.guyw.grpg.util.AopTargetUtils;


/**
 * 网关提供商默认实现，系统启动时，注册各类服务

 * @version $Id : DefaultMsgGatewayServiceProvider.java, v 0.1 2017年1月17日 下午4:45:48  Exp $
 */
@Component
public class DefaultGatewayServiceProvider implements CommandLineRunner {
    /** logger */
    private static final Logger logger             = LoggerFactory.getLogger(DefaultGatewayServiceProvider.class);
    /** spring上下文 */
    @Resource
    private ApplicationContext applicationContext;
       /** 服务集合 */
    private final Map<WeChatMsgTypeEnum, DealWeChatMessageService>        MsgGateway_SERVICES   = Maps.newConcurrentMap();
    private final Map<PersonStatusEnum, PersonActService>        PersonGateway_SERVICES   = Maps.newConcurrentMap();

    private final Map<GameEnum, GameService>        GameGateway_SERVICES   = Maps.newConcurrentMap();

    public DealWeChatMessageService getMsgService(WeChatMsgTypeEnum method) {
        return MsgGateway_SERVICES.get(method);
    }

    public PersonActService getPersonService(PersonStatusEnum method) {
        return PersonGateway_SERVICES.get(method);
    }
    public GameService getGameService(GameEnum method) {
        return GameGateway_SERVICES.get(method);
    }

    // ~ protected方法 ----------------------------------------------------------------------------------------------------------------

    /**
     * 系统启动时，注册系统中的网关服务
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void registerMsgGatewayServices() {
        Map<String, DealWeChatMessageService> services = applicationContext.getBeansOfType(DealWeChatMessageService.class, false, true);

        if (MapUtils.isEmpty(services)) {
            throw new IllegalStateException("Cannot register DealWeChatMessageService, please make sure the DealWeChatMessageService setup as spring bean");
        }

        for (DealWeChatMessageService service : services.values()) {
            DealWeChatMessageService serviceProxy = (DealWeChatMessageService) AopTargetUtils.getTarget(service);
            MsgGateway MsgGatewayAnno = serviceProxy.getClass().getDeclaredAnnotation(MsgGateway.class);
            if (MsgGatewayAnno == null) {
                throw new IllegalStateException(
                    "Cannot register DealWeChatMessageService, please make sure place annotation @MsgGateway on service, serivce: " + service.getClass().getName());

            }
            WeChatMsgTypeEnum serviceName = MsgGatewayAnno.method();
            DealWeChatMessageService sps = MsgGateway_SERVICES.get(serviceName);
            if (sps != null) {
                throw new IllegalStateException(
                    "Cannot register DealWeChatMessageService, the service name has been register by other MsgGateway service, service: " + service.getClass().getName());
            }
            MsgGateway_SERVICES.put(serviceName, service);
            logger.info("Register DealWeChatMessageService: {} - {}", serviceName, service.getClass().getName());
        }
    }

    /**
     * 系统启动时，注册系统中的网关服务
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void registerPersonGatewayServices() {
        Map<String, PersonActService> services = applicationContext.getBeansOfType(PersonActService.class, false, true);

        if (MapUtils.isEmpty(services)) {
            throw new IllegalStateException("Cannot register DealWeChatMessageService, please make sure the DealWeChatMessageService setup as spring bean");
        }

        for (PersonActService service : services.values()) {
            PersonActService serviceProxy = (PersonActService) AopTargetUtils.getTarget(service);
            PersonGateway personGatewayAnno = serviceProxy.getClass().getDeclaredAnnotation(PersonGateway.class);
            if (personGatewayAnno == null) {
                throw new IllegalStateException(
                        "Cannot register PersonActService, please make sure place annotation @PersonGateway on service, serivce: " + service.getClass().getName());

            }
            PersonStatusEnum serviceName = personGatewayAnno.method();
            PersonActService sps = PersonGateway_SERVICES.get(serviceName);
            if (sps != null) {
                throw new IllegalStateException(
                        "Cannot register PersonActService, the service name has been register by other PersonGateway service, service: " + service.getClass().getName());
            }
            PersonGateway_SERVICES.put(serviceName, service);
            logger.info("Register PersonActService: {} - {}", serviceName, service.getClass().getName());
        }
    }

    /**
     * 系统启动时，注册系统中的网关服务
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void registerGameGatewayServices() {
        Map<String, GameService> services = applicationContext.getBeansOfType(GameService.class, false, true);

        if (MapUtils.isEmpty(services)) {
            throw new IllegalStateException("Cannot register GameService, please make sure the GameService setup as spring bean");
        }

        for (GameService service : services.values()) {
            GameService serviceProxy = (GameService) AopTargetUtils.getTarget(service);
            GameGateway gameGatewayAnno = serviceProxy.getClass().getDeclaredAnnotation(GameGateway.class);
            if (gameGatewayAnno == null) {
                throw new IllegalStateException(
                        "Cannot register GameService, please make sure place annotation @GameGateway on service, serivce: " + service.getClass().getName());

            }
            GameEnum serviceName = gameGatewayAnno.method();
            GameService sps = GameGateway_SERVICES.get(serviceName);
            if (sps != null) {
                throw new IllegalStateException(
                        "Cannot register GameService, the service name has been register by other GameGateway service, service: " + service.getClass().getName());
            }
            GameGateway_SERVICES.put(serviceName, service);
            logger.info("Register GameService: {} - {}", serviceName, service.getClass().getName());
        }
    }


    @Override
    public void run(String... args) throws Exception {
        // 注册网关服务
        registerMsgGatewayServices();
        registerPersonGatewayServices();
        registerGameGatewayServices();
    }
}
