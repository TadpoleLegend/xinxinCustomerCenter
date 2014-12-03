package com.ls.mail;

public class MailSenderFactory {
	 /**
     * 服务邮箱
     */
    private static SimpleMailSender serviceSms = null;
 
    /**
     * 获取邮箱
     * 
     * @param type 邮箱类型
     * @return 符合类型的邮箱
     */
    public static SimpleMailSender getSender(String mailType) {
    if (MailSenderType.SMTP.equals(mailType)) {
        if (serviceSms == null) {
        serviceSms = new SimpleMailSender("xinxin_cms@163.com",
            "xinxin123");
        }
        return serviceSms;
    }
    return null;
    }
 
}
