package com.yupi.project.Utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@SpringBootApplication
public class WebhookHandler {

    // 创建日志记录器
    private static final Logger logger = LoggerFactory.getLogger(WebhookHandler.class);

    public static void main(String[] args) {
        SpringApplication.run(WebhookHandler.class, args);
    }

    /**
     * 处理 Webhook POST 请求
     * @param webhookData Webhook 请求中的数据
     * @return 返回 Webhook 接收成功的响应
     */
    @PostMapping("/webhook")
    public String handleWebhook(@RequestBody String webhookData) {
        // 记录接收到的 Webhook 数据
        logger.info("Received Webhook Data: " + webhookData);

        // 在这里可以根据 webhookData 的内容进行不同的处理
        // 比如判断 GitHub Webhook 或 GitLab Webhook
        if (webhookData.contains("GitHub")) {
            logger.info("This is a GitHub Webhook event.");
        } else {
            logger.info("This is a GitLab or other Webhook event.");
        }

        // 返回成功的响应
        return "Webhook received successfully";
    }
}
