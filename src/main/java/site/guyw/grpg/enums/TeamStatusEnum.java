package site.guyw.grpg.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 11:32
 * @description 人物状态枚举
 */
public enum TeamStatusEnum {
    NONE(-1,"未知"),
    INIT(0,"初始化"),//
    START(1,"已开局"),//人满开始
    ;

    private int code;
    private String name;

    TeamStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    private static class SingletonMap {
        private static Map<Integer, TeamStatusEnum> typeMap = new HashMap();
        static {
            TeamStatusEnum[] types = TeamStatusEnum.values();
            for (TeamStatusEnum type : types) {
                typeMap.put(type.code, type);
            }
        }
    }

    public static TeamStatusEnum getEnumByCode(int code){

        return TeamStatusEnum.SingletonMap.typeMap.get(code) == null? NONE: TeamStatusEnum.SingletonMap.typeMap.get(code);
    }

}
