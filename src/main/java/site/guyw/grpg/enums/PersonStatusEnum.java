package site.guyw.grpg.enums;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 11:32
 * @description 人物状态枚举
 */
public enum PersonStatusEnum {
    INIT(0,"初始化"),//需输入游戏人数获取房间号
    JOIN(1,"已入局"),//输入房间号入局
    START(2,"已开局"),//人满开始

    ;

    private int code;
    private String name;

    PersonStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }}
