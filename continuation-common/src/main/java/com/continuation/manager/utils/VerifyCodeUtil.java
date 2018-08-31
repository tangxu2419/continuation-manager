package com.continuation.manager.utils;

import com.continuation.manager.domain.dto.VerifyCode;
import com.continuation.manager.exception.InitVerifyCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * 生成验证码工具
 * @author tangxu
 * @date 2018年3月26日
 */
@Slf4j
public class VerifyCodeUtil {

    private static Random random = new Random();

    private static final String FIXEDLY = "Fixedsys";
    /**
     * 随机生成字符串的取值范围
     */
    private static final String RAND_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static int width = 80;
    private static int height = 26;

    /**
     * 获取随机字符,并返回字符的String格式
     * @param index (指定位置)
     */
    private static String getRandomChar(int index) {
        //获取指定位置index的字符，并转换成字符串表示形式
        return String.valueOf(RAND_STRING.charAt(index));
    }

    /**
     * 获得字体
     */
    private static Font getFont() {
        return new Font(FIXEDLY, Font.BOLD, 18);
    }

    /**
     * 获得颜色
     */
    private static Color getRandColor(int frontColor,int backColor) {
        int maxColor = 255;
        if(frontColor > maxColor) {
            frontColor = maxColor;
        }
        if(backColor > maxColor) {
            backColor = maxColor;
        }
        int red = frontColor + random.nextInt(backColor - frontColor - 16);
        int green = frontColor + random.nextInt(backColor - frontColor -14);
        int blue = frontColor + random.nextInt(backColor - frontColor -18);
        return new Color(red, green, blue);
    }

    /**
     * 绘制字符串,返回绘制的字符串
     */
    private static String drawString(Graphics g, String randomString, int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        String randChar = String.valueOf(getRandomChar(random.nextInt(RAND_STRING.length())));
        randomString += randChar;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(randChar, 13*i, 16);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private static void drawLine(Graphics g) {
        //起点(x,y)  偏移量x1、y1
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 生成随机图片
     */
    public static VerifyCode getRandomCode() throws InitVerifyCodeException {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.setColor(getRandColor(110, 133));
        int lineSize = 40;
        for(int i = 0; i <= lineSize; i++) {
            drawLine(g);
        }
        String randomString = "";
        int stringNum = 4;
        for(int i = 1; i <= stringNum; i++) {
            randomString = drawString(g, randomString, i);
        }
        log.info("生成的验证码为：{}" , randomString);
        g.dispose();
        try {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            ImageIO.write(image, "png", tmp);
            tmp.close();
            return new VerifyCode(randomString, Base64.encodeBase64String(tmp.toByteArray()).replace("\n", "").replace("\r", ""));
        } catch (Exception e) {
            log.error("生成验证码异常：", e);
            throw new InitVerifyCodeException("生成验证码异常");
        }
    }


}


