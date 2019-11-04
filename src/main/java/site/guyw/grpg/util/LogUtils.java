/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package site.guyw.grpg.util;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;


/**
 * @author gyw33188
 * @version $Id: LogUtils.java, v0.1 2016/5/9 gyw33188 Exp $$
 */
public abstract class LogUtils {
    private static Logger logger      = Logger.getLogger("PROJECT");
    private static final String MODULENAME  = "ModuleName";
    private static final String TEXTFILTER1 = "TextFilter1";
    private static final String TEXTFILTER2 = "TextFilter2";
    private static final String LOGFORMAT   = "[%s][%s][%s][%s][%s]%s";


    /**
     * @param errorMessage
     */
    public static void debug(String errorMessage) {
        logger.debug(String.format(LOGFORMAT, MDC.get(MODULENAME), Thread.currentThread().getStackTrace()[2].getFileName().split("\\.")[0],
                Thread.currentThread().getStackTrace()[2].getMethodName(), MDC.get(TEXTFILTER1), MDC.get(TEXTFILTER2), errorMessage));
    }

    /**
     * @param textFilter1
     * @param errorMessage
     */
    public static void debug(String textFilter1 ,String errorMessage) {
        logger.debug(String.format(LOGFORMAT, MDC.get(MODULENAME), Thread.currentThread().getStackTrace()[2].getFileName().split("\\.")[0],
                Thread.currentThread().getStackTrace()[2].getMethodName(), textFilter1, MDC.get(TEXTFILTER2), errorMessage));
    }
    /**
     * @param errorMessage
     */
    public static void info(String errorMessage) {
        logger.info(String.format(LOGFORMAT, MDC.get(MODULENAME), Thread.currentThread().getStackTrace()[2].getFileName().split("\\.")[0],
                Thread.currentThread().getStackTrace()[2].getMethodName(), MDC.get(TEXTFILTER1), MDC.get(TEXTFILTER2), errorMessage));
    }

    /**
     * @param textFilter1
     * @param errorMessage
     */
    public static void error(String textFilter1 ,String errorMessage) {
        logger.error(String.format(LOGFORMAT, MDC.get(MODULENAME), Thread.currentThread().getStackTrace()[2].getFileName().split("\\.")[0],
                Thread.currentThread().getStackTrace()[2].getMethodName(), textFilter1, MDC.get(TEXTFILTER2), errorMessage));
    }
    /**
     * @param errorMessage
     */
    public static void error(String errorMessage) {
        logger.error(String.format(LOGFORMAT, MDC.get(MODULENAME), Thread.currentThread().getStackTrace()[2].getFileName().split("\\.")[0],
                Thread.currentThread().getStackTrace()[2].getMethodName(), MDC.get(TEXTFILTER1), MDC.get(TEXTFILTER2), errorMessage));
    }

    /**
     * @param textFilter1
     * @param errorMessage
     */
    public static void info(String textFilter1 ,String errorMessage) {
        logger.info(String.format(LOGFORMAT, MDC.get(MODULENAME), Thread.currentThread().getStackTrace()[2].getFileName().split("\\.")[0],
                Thread.currentThread().getStackTrace()[2].getMethodName(), textFilter1, MDC.get(TEXTFILTER2), errorMessage));
    }
    /**
     * @param moduleName
     */
    public static void setModuleName(String moduleName) {
        removeModuleName();
        if (!StringUtils.isEmpty(moduleName)) {
            MDC.put(MODULENAME, moduleName);
        }
        //TEXTFILTER2赋值UUID，作为一次调用的流程跟踪id
        MDC.put(TEXTFILTER2, UUID.randomUUID().toString());
    }

    /**
     * @param textFilter1
     */
    public static void setTextFilter1(String textFilter1) {
        removeTextFilter1();
        if (!StringUtils.isEmpty(textFilter1)) {
            MDC.put(TEXTFILTER1, textFilter1);
        }
    }

    /**
     * clearAll
     */
    public static void clearAll() {
        MDC.clear();
    }

    /**
     * removeModuleName
     */
    public static void removeModuleName() {
        MDC.remove(MODULENAME);
        MDC.remove(TEXTFILTER2);
    }

    /**
     * removeTextFilter1
     */
    public static void removeTextFilter1() {
        MDC.remove(TEXTFILTER1);
    }

    /**
     * removeModuleAndFilter
     */
    public static void removeModuleAndFilter() {
        removeModuleName();
        removeTextFilter1();
    }

    /**
     * @return
     */
    public static String getTextFilter1() {
        return MDC.get(TEXTFILTER1);
    }

    /**
     * @return
     */
    public static String getTextFilter2() {
        return MDC.get(TEXTFILTER2);
    }

    /**
     * @return
     */
    public static String getModuleName() {
        return MDC.get(MODULENAME);
    }

    /**
     * @return
     */
    public static Map<String, String> getMDCContext(){
        return MDC.getCopyOfContextMap();
    }

    /**
     * @param mdcContext
     */
    public static void  setMDCContext(Map<String, String> mdcContext){
        removeModuleAndFilter();
        if (mdcContext != null){
            MDC.setContextMap(mdcContext);
        }
    }

    private LogUtils() {
    }
}
