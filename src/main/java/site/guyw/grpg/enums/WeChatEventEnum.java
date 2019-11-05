package site.guyw.grpg.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 13:37
 * @description 游戏类型枚举
 */
public enum WeChatEventEnum {
    /**
     * 事件类型
     * subscribe(订阅)
     * unsubscribe(取消订阅)
     * LOCATION(上报地理位置)
     * CLICK(点击普通的菜单)
     * VIEW(点击跳转链接的菜单)
     */
    NONE("","未知"),
    SUBSCRIBE("SUBSCRIBE","订阅"),
    UNSUBSCRIBE("UNSUBSCRIBE","取消订阅"),
    LOCATION("LOCATION","上报地理位置"),
    CLICK("CLICK","点击普通的菜单"),
    VIEW("VIEW","点击跳转链接的菜单"),
    ;

    private String code;
    private String name;


    WeChatEventEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }
    private static class SingletonMap {
        private static Map<String, WeChatEventEnum> typeMap = new HashMap();
        static {
            WeChatEventEnum[] types = WeChatEventEnum.values();
            for (WeChatEventEnum type : types) {
                typeMap.put(type.code, type);
            }
        }
    }

    public static WeChatEventEnum getEnumByCode(String code){

        return WeChatEventEnum.SingletonMap.typeMap.get(code) == null? NONE: WeChatEventEnum.SingletonMap.typeMap.get(code);
    }

}
