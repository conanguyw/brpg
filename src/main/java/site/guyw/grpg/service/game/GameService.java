package site.guyw.grpg.service.game;



/**
 * @author conangu(顾永威)
 * @createTime 2019-10-12 16:18
 * @description 微信消息处理接口
 */
public interface GameService {

  public String init(String request);
  public String start(String request);
  public String next(String request);

}
