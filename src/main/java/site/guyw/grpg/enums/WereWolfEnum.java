package site.guyw.grpg.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 13:37
 * @description 游戏类型枚举
 */
public enum WereWolfEnum {
    NONE(-1,"未知"),
    VILLAGERS(0,"平民"),
    WEREWOLVES(1,"普通狼人"),
    SEER(2,"预言家"),
    WITCH(3,"女巫"),
    HUNTER(4,"猎人"),
    ACIENT(5,"长老"),
    SAVIOR(6,"守卫"),
    ;

    private int code;
    private String name;


    WereWolfEnum(int code, String name) {
        this.name = name;
        this.code = code;
    }
    private static class SingletonMap {
        private static Map<Integer, WereWolfEnum> typeMap = new HashMap();
        static {
            WereWolfEnum[] types = WereWolfEnum.values();
            for (WereWolfEnum type : types) {
                typeMap.put(type.code, type);
            }
        }
    }

    public static WereWolfEnum getEnumByCode(int code){

        return WereWolfEnum.SingletonMap.typeMap.get(code) == null? NONE: WereWolfEnum.SingletonMap.typeMap.get(code);
    }

}
