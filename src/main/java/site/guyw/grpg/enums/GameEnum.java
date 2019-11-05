package site.guyw.grpg.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 13:37
 * @description 游戏类型枚举
 */
public enum GameEnum {
    NONE(-1,"未知"),
    WEREWOLF(0,"狼人杀"),
    UNDERCOVER(1,"谁是卧底");

    private int code;
    private String name;


    GameEnum(int code,String name) {
        this.name = name;
        this.code = code;
    }
    private static class SingletonMap {
        private static Map<Integer, GameEnum> typeMap = new HashMap();
        static {
            GameEnum[] types = GameEnum.values();
            for (GameEnum type : types) {
                typeMap.put(type.code, type);
            }
        }
    }

    public static GameEnum getEnumByCode(int code){

        return GameEnum.SingletonMap.typeMap.get(code) == null? NONE: GameEnum.SingletonMap.typeMap.get(code);
    }

}
