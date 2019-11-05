/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package site.guyw.grpg.manager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import site.guyw.grpg.enums.GameEnum;
import site.guyw.grpg.enums.PersonStatusEnum;

/**
 * 服务网关标注
 * @author
 * @version $Id : Gateway.java, v 0.1 2018/10/20 下午10:29  Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface GameGateway {

    /**
     * 网关方法
     * @return
     */
    GameEnum method();


}
