package site.guyw.grpg.common;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 15:14
 * @description 微信消息返回
 */
@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity {
    // 开发者微信号
    protected String FromUserName;
    // 发送方帐号（一个OpenID）
    protected String ToUserName;
    // 消息创建时间
    protected Long CreateTime;
    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     * event 事件推送
     */
    protected String MsgType;
    // 消息id
    protected Long MsgId;
    // 文本内容
    private String Content;
    // 图片链接（由系统生成）
    private String PicUrl;

    /**
     * 事件类型
     * subscribe(订阅)
     * unsubscribe(取消订阅)
     * LOCATION(上报地理位置)
     * CLICK(点击普通的菜单)
     * VIEW(点击跳转链接的菜单)
     */
    private String Event;
}
