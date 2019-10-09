package com.cloud.provider.utils;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 发送邮件，简直邮件、复杂邮件
 */
public class SendEmailUtil {

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com, 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    private static String myEmailSMTPHost = "smtp.163.com" ;
    // 发件人的 邮箱
    private static String sendMail = "XXXXXXX@163.com" ;
    // 发件人的密码
    private static String sendMailPassword = "123456" ;
    // 收件人的昵称
    private static String sendUserNickname  = "XX大王" ;

    public static void sendMail(String receiveMail,String receiveNickname,String subject,String content) throws Exception{

        //用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        Properties props = new Properties() ;
        //使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", "smtp") ;
        // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.host", myEmailSMTPHost) ;
        // 需要请求认证
        props.setProperty("mail.smtp.auth", "true") ;
        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误, 打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

        //创建一个会话对象（为了发送邮件准备的）
        Session session = Session.getDefaultInstance(props) ;
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

        //创建邮件对象（根据需要调用简单邮件发送还是复杂邮件发送）
        MimeMessage message = createMimeMessage(session, sendMail, sendUserNickname, receiveMail, receiveNickname, subject, content) ;

        //根据session获取邮件传输对象
        Transport transport = session.getTransport() ;
        //使用 邮箱账号 和 密码 连接邮件服务器,如果邮箱有独立密码，则邮箱密码就是独立密码
        transport.connect(sendMail, sendMailPassword);
        //发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        //关闭链接
        transport.close();

    }

    /**
     * 创建一个只包含文本的简单邮件
     * @param session 会话
     * @param sendMail 发件人邮箱
     * @param sendUserNickname 发件人昵称
     * @param receiveMail 收件人邮箱
     * @param receiveNickname 收件人昵称
     * @param subject 邮件主题
     * @param content 邮件正文
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session,String sendMail,String sendUserNickname,String receiveMail,String receiveNickname,String subject,String content) throws Exception{
        //1.创建一封邮件MimeMessage
        MimeMessage message = new MimeMessage(session) ;
        //2.From：发件人
        message.setFrom(new InternetAddress(sendMail,sendUserNickname,"utf-8"));
        //3.To：收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail,receiveNickname,"utf-8"));
        //4.Subject：邮件主题
        message.setSubject(subject,"UTF-8");
        //5.content： 邮件正文，可以使用html标签，如果内容有广告嫌疑，发送邮件可能失败
        message.setContent(content,"text/html;charset=UTF-8");
        //6.设置发邮件时间
        message.setSentDate(new Date());
        //7.保存设置
        message.saveChanges();

        return message ;

    }

    /**
     * 创建一个复杂邮件，包含图片，文本，附近
     * @param session 会话
     * @param sendMail 发件人邮箱
     * @param sendUserNickname 发件人昵称
     * @param receiveMail 收件人邮箱
     * @param receiveNickname 收件人昵称
     * @param subject 邮件主题
     * @param content 像图片地址，附件地址可以传一个json，地址为集合，使用的时候判断一下
     * @return
     * @throws Exception
     */
    public static MimeMessage createComplexMessage(Session session,String sendMail,String sendUserNickname,String receiveMail,String receiveNickname,String subject,String content) throws Exception{
        //1.创建一封邮件
        MimeMessage message = new MimeMessage(session) ;
        //2.From：发件人
        message.setFrom(new InternetAddress(sendMail,sendUserNickname,"utf-8"));
        //3.To：收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail,receiveNickname,"utf-8"));
        //4.Subject：邮件主题
        message.setSubject(subject,"UTF-8");

        /*********创建图片节点**************/
        MimeBodyPart image = new MimeBodyPart() ;
        DataHandler dh = new DataHandler(new FileDataSource("E://1.png")) ;//读取本地文件
        image.setDataHandler(dh);//将图片添加到节点
        image.setContentID("image-test");// 为该节点设置一个唯一编号（在文本“节点”将引用该ID）
        image.setFileName(MimeUtility.encodeText(dh.getName()));

        /********创建图片和文本节点*********/
        MimeBodyPart imgAndTxt = new MimeBodyPart() ;
        imgAndTxt.setContent("这是一张图片<br/><img src='cid:image-test'/>", "text/html;charset=UTF-8");

        /********创建附件节点**************/
        MimeBodyPart attachment = new MimeBodyPart() ;
        DataHandler att = new DataHandler(new FileDataSource("E://1.txt")) ;//读取本地附近
        attachment.setDataHandler(att);
        attachment.setFileName(MimeUtility.encodeText(att.getName()));

        /********设置各节点之间的关系********/
        MimeMultipart mm = new MimeMultipart() ;
        mm.addBodyPart(image);
        mm.addBodyPart(imgAndTxt);
        mm.addBodyPart(attachment);
        mm.setSubType("mixed");

        //5.content： 邮件正文，将MimeMultipart添加到内容中
        message.setContent(mm,"text/html;charset=UTF-8");
        //6.设置发邮件时间
        message.setSentDate(new Date());
        //7.保存设置
        message.saveChanges();

        return message ;

    }

    public static void main(String[] args) throws Exception {
        sendMail("XXXXX@qq.com", "张先生", "测试", "这是测试用的");
    }

    //说明
    /*
    一、JavaMail API简介
    JavaMail API是读取、撰写、发送电子信息的可选包。常用协议：SMTP（简单邮件传输协议），POP（邮局协议），IMAP，MIME

    所需jar：javax.mail.jar

    主要类：

            1.Properties：连接邮件服务器的参数配置

            2.Session：基本的邮件会话，我们进行收发邮件的工作都是基于这个会话的。Session对象利用了java.util.Properties对象获得了邮件服务器、用户名、密码信息和整个应用程序都要使用到的共享信息。

            3.Message：建立了Session对象后，就通过Message构造信息体，Message是抽象类，通过MimeMessage创建

            4.Address：用来设置发件人收件人，是个抽象类，实现类用InternetAddress

            5.Transport：在发送信息时，Transport类将被用到。这个类实现了发送信息的协议（通称为SMTP），此类是一个抽象类，我们可以使用这个类的静态方法send()和sendMessage()来发送消息　　

            　　send()：每次调用时进行与邮件服务器的连接的，然后在发送，发送多个邮件效率低。
            　　sendMessage()：效率比send()高，先链接在发送

    步骤如下：

            1.创建一封邮件MimeMessage

            2.From：发件人

            3.To：收件人

            4.Subject：邮件主题

            5.content： 邮件正文，可以使用html标签，如果内容有广告嫌疑，发送邮件可能失败

            6.设置发邮件时间

            7.保存设置

            8.根据session获取邮件传输对象

            9.使用 邮箱账号 和 密码 连接邮件服务器,如果邮箱有独立密码，则邮箱密码就是独立密码

            10.发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人

            11.关闭链接

     */
}