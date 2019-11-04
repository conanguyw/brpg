package site.guyw.grpg.util;


import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 18:32
 * @description aopUtil
 */

public class AopTargetUtils {
    /**
     * 获取目标对象
     *
     * @param proxy 代理
     * @return 代理对象
     */
    public static Object getTarget(Object proxy) {

        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            return getCglibProxyTargetObject(proxy);
        }

    }

    /**
     * 获取cglib代理对象
     *
     * @param proxy 代理
     * @return 代理对象
     */
    private static Object getCglibProxyTargetObject(Object proxy) {
        try {
            Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
            h.setAccessible(true);
            Object _proxy = h.get(proxy);

            Field advised = _proxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);

            return ((AdvisedSupport) advised.get(_proxy)).getTargetSource().getTarget();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取JDK动态代理对象
     *
     * @param proxy 代理
     * @return 代理对象
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) {
        try {
            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            Object _proxy = h.get(proxy);

            Field advised = _proxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);

            return ((AdvisedSupport) advised.get(_proxy)).getTargetSource().getTarget();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
