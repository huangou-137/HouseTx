package pers.ho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.ho.utils.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author 黄欧
 * @create 2021-08-07 18:40
 */
@Controller
public class CheckCodeController {

    private static String codeName = "checkCode";

    /**
     * @return 返回随机字体
     */
    private Font getRandFont(){
        String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
        Random rand = new Random();
        int index = rand.nextInt(fontNames.length);
        String fontName = fontNames[index];
        // 生成随机的样式 {Font.PLAIN, Font.BOLD, Font.ITALIC}
        int style = rand.nextInt(4);
        int size = rand.nextInt(5) + 22;
        return new Font(fontName, style, size);
    }

    /**
     * 动态生成验证码图片，并将其输出到网页上
     */
    @RequestMapping("/checkCode/create")
    public void create(HttpServletResponse resp, HttpSession session) throws IOException {

        //服务器通知浏览器不要缓存
        resp.setHeader("pragma","no-cache");
        resp.setHeader("cache-control","no-cache");
        resp.setHeader("expires","0");

        int width = 100;        //默认图片宽度
        int height = 36;        //默认图片高度
        //创建图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        //美化前先获取画笔
        Graphics g = image.getGraphics();
        //背景：粉红色
        g.setColor(Color.PINK);
        g.fillRect(0, 0, width, height);
        //边框：蓝色、1
        g.setColor(Color.BLUE);
        g.drawRect(0, 0, width - 1, height - 1);

        //验证码的基本字符集（已去除外观相似的字符：0oO,1ilI,6b,8g,9q）
        String baseCharSet = "23457acdefhjkmnprstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        String code = WebUtils.getRandString(baseCharSet,4);
        //设置字体颜色：DarkSlateBlue深板蓝色
        g.setColor(new Color(72,61,139));
        for (int i = 1; i <= 4; i++) {
            char ch = code.charAt(i - 1);
            //根据随机字体画字符
            g.setFont(getRandFont());
            g.drawString(ch + "", width / 5 * i, (int) (height / 1.2));
        }

        //绘画5条干扰线（绿色）
        g.setColor(Color.GREEN);
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int x1 = rand.nextInt(width);
            int x2= rand.nextInt(width);
            int y1 = rand.nextInt(height);
            int y2 = rand.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }

        //把验证码放入HttpSession中
        session.setAttribute(codeName,code);

        //输出.jpg图片到浏览器
        ImageIO.write(image, "jpg", resp.getOutputStream());
    }

    /**
     * 根据请求连接中的参数验证验证码
     * @param paramName 对应参数名
     */
    public static boolean verify(HttpServletRequest req, String paramName) {
        String checkCode = (String) req.getSession().getAttribute(codeName);
        return checkCode != null && checkCode.equals(req.getParameter(paramName));
    }

    /**
     * 根据请求连接中的参数（默认为"code"参数）验证验证码
     */
    public static boolean verify(HttpServletRequest req) {
        return verify(req, "code");
    }

}