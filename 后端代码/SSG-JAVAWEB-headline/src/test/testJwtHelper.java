package test;

import org.testng.annotations.Test;
import utils.JwtHelper;


public class testJwtHelper {
    @Test
    void testAll(){
        String token = JwtHelper.createToken(1L);
        System.out.println(token);

        Long uid = JwtHelper.getUserId(token);
        System.out.println(uid);
        System.out.println(JwtHelper.isExpiration(token));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(JwtHelper.isExpiration(token));
    }
}
