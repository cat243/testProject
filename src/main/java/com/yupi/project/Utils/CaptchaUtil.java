package com.yupi.project.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * 图片验证码工具类
 */
public class CaptchaUtil {
    // 验证码字符集
    private static final char[] CODE_CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    
    // 默认配置
    private static final int DEFAULT_WIDTH = 120;      // 默认宽度
    private static final int DEFAULT_HEIGHT = 40;      // 默认高度
    private static final int DEFAULT_CODE_COUNT = 4;   // 默认验证码位数
    private static final int DEFAULT_LINE_COUNT = 20;  // 默认干扰线数量
    private static final int DEFAULT_FONT_SIZE = 25;   // 默认字体大小
    
    private final Random random = new Random();
    
    // 验证码配置
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int codeCount = DEFAULT_CODE_COUNT;
    private int lineCount = DEFAULT_LINE_COUNT;
    private int fontSize = DEFAULT_FONT_SIZE;
    
    // 生成的验证码
    private String code;
    
    /**
     * 使用默认配置
     */
    public CaptchaUtil() {}
    
    /**
     * 自定义配置
     * @param width 图片宽度
     * @param height 图片高度
     * @param codeCount 验证码位数
     * @param lineCount 干扰线数量
     * @param fontSize 字体大小
     */
    public CaptchaUtil(int width, int height, int codeCount, int lineCount, int fontSize) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.fontSize = fontSize;
    }
    
    /**
     * 生成验证码图片
     * @return 生成的图片
     */
    public BufferedImage generateImage() {
        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g = image.createGraphics();
        // 设置背景色
        g.setColor(getRandomColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设置字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
        // 绘制干扰线
        for (int i = 0; i < lineCount; i++) {
            drawRandomLine(g);
        }
        // 绘制验证码
        code = drawRandomCode(g);
        // 释放图形上下文
        g.dispose();
        return image;
    }
    
    /**
     * 将验证码图片写入输出流
     * @param os 输出流
     * @param formatName 图片格式名称 (如 "JPEG", "PNG")
     * @throws IOException 如果写入失败
     */
    public void writeTo(OutputStream os, String formatName) throws IOException {
        BufferedImage image = generateImage();
        ImageIO.write(image, formatName, os);
    }
    
    /**
     * 获取验证码图片的字节数组
     * @param formatName 图片格式名称 (如 "JPEG", "PNG")
     * @return 图片字节数组
     * @throws IOException 如果生成失败
     */
    public byte[] getImageBytes(String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeTo(baos, formatName);
        return baos.toByteArray();
    }
    
    /**
     * 获取生成的验证码
     * @return 验证码字符串
     */
    public String getCode() {
        return code;
    }
    
    // 绘制随机验证码
    private String drawRandomCode(Graphics2D g) {
        StringBuilder sb = new StringBuilder();
        // 计算字符间距
        int codeX = (width - 10) / codeCount;
        
        for (int i = 0; i < codeCount; i++) {
            // 随机字符
            char c = CODE_CHARS[random.nextInt(CODE_CHARS.length)];
            sb.append(c);
            
            // 随机颜色
            g.setColor(getRandomColor(20, 130));
            
            // 随机旋转角度 (-30到30度)
            AffineTransform affine = new AffineTransform();
            affine.rotate(Math.toRadians(random.nextInt(60) - 30), 0, 0);
            g.setTransform(affine);
            
            // 绘制字符
            g.drawString(String.valueOf(c), 15 + i * codeX, height - 15);
            
            // 重置旋转
            affine.rotate(Math.toRadians(0), 0, 0);
            g.setTransform(affine);
        }
        return sb.toString();
    }
    
    // 绘制干扰线
    private void drawRandomLine(Graphics2D g) {
        int x1 = random.nextInt(width);
        int y1 = random.nextInt(height);
        int x2 = random.nextInt(width);
        int y2 = random.nextInt(height);
        
        g.setColor(getRandomColor(100, 200));
        g.drawLine(x1, y1, x2, y2);
    }
    
    // 生成随机颜色
    private Color getRandomColor(int fc, int bc) {
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        
        return new Color(r, g, b);
    }
    
    // 测试
    public static void main(String[] args) throws IOException {
        // 使用默认配置
        CaptchaUtil captcha = new CaptchaUtil();
        // 生成验证码图片
        BufferedImage image = captcha.generateImage();
        // 获取验证码
        String code = captcha.getCode();
        System.out.println("生成的验证码: " + code);
        
        // 保存图片到文件
        ImageIO.write(image, "JPEG", new java.io.File("captcha.jpg"));
        System.out.println("验证码图片已保存到 captcha.jpg");
        
        // 获取图片字节数组
        byte[] imageBytes = captcha.getImageBytes("PNG");
        System.out.println("PNG格式图片字节数: " + imageBytes.length);
    }
}