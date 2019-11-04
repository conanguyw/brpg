package site.guyw.grpg.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import site.guyw.grpg.util.HttpUtils;
import site.guyw.grpg.common.AccessTokenResp;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-11 20:30
 * @description accessToken
 */
@Service
public class AccessTokenService {
    private String accessToken;
    private static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx322a6bd51d1db0f5&secret=f900a01969566f1e219d01445a3913cc";

    private long time = 0;
    public String getAccessToken(){
        if (StringUtils.isBlank(accessToken) || time <= System.currentTimeMillis()){
            AccessTokenResp accessTokenResp;
            String res = HttpUtils.get(accessTokenUrl);
            System.out.println(res);
            accessTokenResp  = JSON.parseObject(res,AccessTokenResp.class);
            accessToken = accessTokenResp.getAccess_token();
            if (StringUtils.isNotBlank(accessToken)){
                time = System.currentTimeMillis() + accessTokenResp.getExpires_in()*1000;
            }
        }
        return accessToken;
    }

}
