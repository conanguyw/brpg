package site.guyw.grpg.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.guyw.grpg.common.InMsgEntity;
import site.guyw.grpg.common.OutMsgEntity;
import site.guyw.grpg.common.WeChatMsgTypeEnum;
import site.guyw.grpg.manager.gateway.DefaultGatewayServiceProvider;
import site.guyw.grpg.service.dealMsg.DealWeChatMessageService;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author conangu(顾永威)
 * @createTime 2019-10-11 15:22
 * @description 微信token验证
 */
@Controller
@EnableAutoConfiguration
@EnableCaching
public class WeChatController {
    private static String                 token     = "siteguywtoken";
    private static String                 appId     = "wx322a6bd51d1db0f5";
    private static String                 appSecret = "f900a01969566f1e219d01445a3913cc";

    /** 服务网关提供商 */
    @Resource
    private DefaultGatewayServiceProvider gatewayServiceProvider;

    //设置访问的url
    @RequestMapping(value = "/brpg", method = RequestMethod.GET)
    //表示返回JSON格式的结果，如果前面使用的是@RestController可以不用写
    @ResponseBody
    public String tokenVerify(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
                              @RequestParam("echostr") String echostr) {

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }

        return "hello";
    }

    /**
     * 微信消息处理
     */
    @RequestMapping(value = "/brpg", method = RequestMethod.POST)
    @ResponseBody
    public OutMsgEntity handleMessage(@RequestBody InMsgEntity msg) {

        //获取接收的消息类型

        DealWeChatMessageService messageService = gatewayServiceProvider.getService(WeChatMsgTypeEnum.getEnumByCode(msg.getMsgType()));
        return messageService.invoke(msg);

    }

    public static void main(String[] args) throws Exception {
        //通过SpringApplication的run()方法启动应用，无需额外的配置其他的文件
        SpringApplication.run(WeChatController.class, args);
    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

}
