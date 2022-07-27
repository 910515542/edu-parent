package com.xiaoxuan.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * JWT常用操作：生成JWTToken、验证token、得到token里存储的信息
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;//过期时间常量
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";//秘钥

    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                //JWT头
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //JWT分类
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //token主体部分，存储用户信息
                .claim("id", id)
                .claim("nickname", nickname)
                //签名
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            //从请求头中获取到token的值
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取存的信息，比如会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {

        //获取cookie列表并找到记录token的cookie
        System.out.println("1进入了getMemberIdByJwtToken方法");

//        Cookie[] cookies = request.getCookies();
//        if(cookies == null) return "";
//        System.out.println("2获取了cookie列表");
//        for (Cookie cookie : cookies) {
//            if("guli_token".equals(cookie.getName())){
//                jwtToken = cookie.getValue();
//                break;
//            }
//        }
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();//获得JWT负载部分
        System.out.println("得到Body");
        return (String)claims.get("id");//获得负载部分存的用户信息
    }
}
