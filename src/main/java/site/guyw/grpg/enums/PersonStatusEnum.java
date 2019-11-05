package site.guyw.grpg.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 11:32
 * @description 人物状态枚举
 */
public enum PersonStatusEnum {
    NONE(-1,"未知"),
    INIT(0,"初始化"),//需选择游戏或输入房间号
    INITED(1,"已选择，待开始"),//输入游戏人数获取房间号
    JOIN(2,"已入局"),//输入房间号入局
    START(3,"已开局"),//人满开始

    ;

    private int code;
    private String name;

    PersonStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    private static class SingletonMap {
        private static Map<Integer, PersonStatusEnum> typeMap = new HashMap();
        static {
            PersonStatusEnum[] types = PersonStatusEnum.values();
            for (PersonStatusEnum type : types) {
                typeMap.put(type.code, type);
            }
        }
    }

    public static PersonStatusEnum getEnumByCode(int code){

        return PersonStatusEnum.SingletonMap.typeMap.get(code) == null? NONE: PersonStatusEnum.SingletonMap.typeMap.get(code);
    }

}
