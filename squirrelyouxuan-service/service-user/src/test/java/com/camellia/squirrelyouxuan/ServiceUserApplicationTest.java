package com.camellia.squirrelyouxuan;

import com.camellia.squirrelyouxuan.enums.UserType;
import com.camellia.squirrelyouxuan.model.user.User;
import com.camellia.squirrelyouxuan.user.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author fuyunjia
 * @Date 2023-11-14 8:39
 */
@SpringBootTest
public class ServiceUserApplicationTest {


    @Autowired
    private StringRedisTemplate stringredisTemplate;

    @Autowired
    private IUserService userService;

    @Test
    public void testRedis(){
        // 写入一条String数据
        stringredisTemplate.opsForValue().set("伍世影", "hahahahhahhghdsagods");
        System.out.println(stringredisTemplate.opsForValue().get("伍世影"));

        System.out.println(stringredisTemplate);
    }

    @Test
    public void testRedis2(){
        // 添加一个用户
        User user = new User();
        user.setNickName("伍世影");
        user.setPhotoUrl("rrrrrrrrrrr");
        user.setOpenId("fgfdgfdhgjfgjgjgjgjgjgjgjgjshfh");
        user.setPhone("150205445444");
        user.setUserType(UserType.USER);
        userService.save(user);


    }
}
