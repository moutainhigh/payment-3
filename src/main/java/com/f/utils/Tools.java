package com.f.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-23 下午7:13
 **/
public class Tools {
    public static final String ORDERNO_PREFEX_NORMAL = "draw";
    public static String Creatordernum(int money) {
        // 将字符串转为为BigDecimal格式
        String moneyStr = Integer.toString(money, 36);
        long date = System.currentTimeMillis() / 100;
        String dateStr = Long.toString(date, 36);
        String dNum = moneyStr + dateStr;
        int length = dNum.length();
        if (length < 16) {
            int i = 16 - length;
            String randNum = RandomStringUtils.random(i, false, true);
            dNum += randNum;
        }
        return ORDERNO_PREFEX_NORMAL + dNum;
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        return StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
    }

    private static <T> T randomGetByWeight(List<T> pool, Function<T, Integer> weightFunction) {
        int totalWeight = pool.stream().mapToInt(weightFunction::apply).sum();
        int random = new Random().nextInt(totalWeight) + 1;
        for (T t : pool) {
            random -= weightFunction.apply(t);
            if (random <= 0) {
                return t;
            }
        }
        return null;
    }

    public static String dataFormat(long timestamp) {
        return DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:mm:ss");
    }
}
