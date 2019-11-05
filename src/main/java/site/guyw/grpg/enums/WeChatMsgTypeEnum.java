package site.guyw.grpg.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 15:43
 * @description 微信推送类型枚举
 */
public enum WeChatMsgTypeEnum {
    NONE("none","未定义"),
    TEXT("text","文本消息"),
    IMAGE("image","图片消息"),
    VOICE("voice","语音消息"),
    VIDEO("video","视频消息"),
    MUSIC("music","音乐消息"),
    EVENT("event","事件推送"),
    ;


    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     * event 事件推送
     */
    private String code;
    private String name;

    WeChatMsgTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    private static class SingletonMap {
        private static Map<String, WeChatMsgTypeEnum> typeMap = new HashMap();
        static {
            WeChatMsgTypeEnum[] types = WeChatMsgTypeEnum.values();
            for (WeChatMsgTypeEnum type : types) {
                typeMap.put(type.code, type);
            }
        }
    }

    public static WeChatMsgTypeEnum getEnumByCode(String code){

        return SingletonMap.typeMap.get(code) == null? NONE:SingletonMap.typeMap.get(code);
    }


}
