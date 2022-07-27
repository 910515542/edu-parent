package com.xiaoxuan.msm.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiaoxuan.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImp implements MsmService {
    @Override
    public boolean send(Map<String, Object> map, String number) {
        if(StringUtils.isEmpty(number)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5tBndgVdi88PrAznKSq6", "7pf2MMPUQJBXmNrPrPEkxDLdcSaXN8");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", number);//设置手机号
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");//签名的名称
        request.putQueryParameter("TemplateCode", "");//模板code
        //发送的随机数字，阿里云需要一个JSON类型的数据，所有前面封装成map，现在更方便
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        try {
            //最终发送给阿里云
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
