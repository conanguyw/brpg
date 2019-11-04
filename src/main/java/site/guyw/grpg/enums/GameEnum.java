package site.guyw.grpg.enums;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 13:37
 * @description 游戏类型枚举
 */
public enum GameEnum {
    Werewolf(0,"狼人杀");


    private int code;
    private String name;


    GameEnum(int code,String name) {
        this.name = name;
        this.code = code;
    }


}
