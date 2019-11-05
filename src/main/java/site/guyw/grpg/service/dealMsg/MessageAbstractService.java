package site.guyw.grpg.service.dealMsg;

import org.jetbrains.annotations.NotNull;
import site.guyw.grpg.cache.Person;
import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;
import site.guyw.grpg.enums.PersonStatusEnum;

import java.util.Date;

import static site.guyw.grpg.cache.SimpleCache.getCache;
import static site.guyw.grpg.cache.SimpleCache.setCache;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:50
 * @description 通用message处理类
 */
public abstract class MessageAbstractService {

    public OutMsgEntity buildOutMsgEntity(InMsgEntity msg) {
        //创建消息响应对象
        OutMsgEntity out = new OutMsgEntity();
        //把原来的发送方设置为接收方
        out.setToUserName(msg.getFromUserName());
        //把原来的接收方设置为发送方
        out.setFromUserName(msg.getToUserName());
        //设置消息创建时间
        out.setCreateTime(new Date().getTime());
        return out;
    }

    public Person getPerson(InMsgEntity msg) {
        Person person = getCache(msg.getFromUserName());
        if (person == null) {
            person.setOpenId(msg.getFromUserName());
            person.setStatus(PersonStatusEnum.INIT);
            setCache(msg.getFromUserName(), person);
        }
        return person;
    }
}
