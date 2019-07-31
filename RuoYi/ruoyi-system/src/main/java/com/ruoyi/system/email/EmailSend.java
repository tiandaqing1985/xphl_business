package com.ruoyi.system.email;



import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSend {
		
		 private static String Sender = "wugaofang@perfect-cn.cn";
		 private static String password = "123456";
		 private static String host = "smtp.mxhichina.com";
		 
		 private static JavaMailSenderImpl createMailSender() {
		        JavaMailSenderImpl sender = new JavaMailSenderImpl();
		        sender.setHost(host);
		        sender.setPort(25);
		        sender.setUsername(Sender);
		        sender.setPassword(password);
		        sender.setDefaultEncoding("Utf-8");
		        Properties p = new Properties();
		        p.setProperty("mail.smtp.timeout", "25000");
		        p.setProperty("mail.smtp.auth", "true");
		        sender.setJavaMailProperties(p);
		        return sender;
		 }
		 
		 public static void main(String[] args) {
			 EmailSend es = new EmailSend();
			 
			 try {
				es.sendMail("daienxian@perfect-cn.cn", "标题：测试标题", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		  //发送邮件
		  public void sendMail(String to,String subject,String text1) throws Exception {
		   JavaMailSenderImpl mailSender1 = createMailSender();
		   MimeMessage message = createMixedMail(null, subject, text1, Sender, to, null, null);
		   mailSender1.send(message);
		  }
	
		  
		  public static MimeMessage createMixedMail(Session session, String title, String content, String Sender,String recipients,String copyto,
				    String[] filePath) throws Exception {//String recipient,String copyto
				   // 创建邮件
				   MimeMessage message = new MimeMessage(session);
				   // 设置邮件的基本信息 //指明邮件的发件人
				   message.setFrom(new InternetAddress(Sender));
				   // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
				   message.setRecipients(Message.RecipientType.TO, Address(recipients));
				//   message.setRecipients(Message.RecipientType.TO, new InternetAddress(recipient));
				   // 设置抄送人
				   if(null != copyto && !"".equals(copyto)){  
//				             @SuppressWarnings("static-access")  
//				             InternetAddress[] internetAddressCC = new InternetAddress().parse(ccUser);  
				             message.setRecipients(Message.RecipientType.CC, Address(copyto));  
				            }  
				//   
				   // 邮件的标题
				   message.setSubject(title);
				   // 正文
				   MimeBodyPart text = new MimeBodyPart();
				   text.setContent(content, "text/html;charset=UTF-8");
				   MimeMultipart mp1 = new MimeMultipart();
				   mp1.addBodyPart(text);

				//   String[] filePath={"G://xk_user.zip"};

				   if (filePath != null) {
				    BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
				    mdp.setContent(text, "text/html;charset=UTF-8");// 给BodyPart对象设置内容和格式/编码方式
				    //Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对象
				    //mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
				    // 把mm作为消息对象的内容
				    MimeBodyPart filePart;
				    FileDataSource filedatasource;
				    // 逐个加入附件
				    for (int j = 0; j < filePath.length; j++) {
				     filePart = new MimeBodyPart();
				     filedatasource = new FileDataSource(filePath[j]);
				     filePart.setDataHandler(new DataHandler(filedatasource));
				     try {
				      filePart.setFileName(MimeUtility.encodeText(filedatasource.getName()));
				     } catch (Exception e) {
				      e.printStackTrace();
				     }
				     mp1.addBodyPart(filePart);
				    }
//				    message.setContent(mp1);
				   }
				   message.setContent(mp1);
				   message.saveChanges();
				   
				   return message;
		  }
		  
			public static   InternetAddress[]  Address(String recipient){
				//多个接收账号
				         String str=recipient;
				         InternetAddress[] address=null;
				         Pattern pattern = Pattern.compile("<([^<>]*)>");
				         try {
				             List list = new ArrayList();//不能使用string类型的类型，这样只能发送一个收件人
				             String[] median=str.split(",");//对输入的多个邮件进行逗号分割
				             for(int i=0;i<median.length;i++){
				            	 if(median[i].contains("<")){
				            	        Matcher matcher = pattern.matcher(median[i]);
				            	        while(matcher.find())
				            	        	list.add(new InternetAddress(matcher.group()));
				            	 }else{
				            		 list.add(new InternetAddress(median[i]));
				            	 }
				             }
				             address =(InternetAddress[])list.toArray(new InternetAddress[list.size()]);
				            
				        } catch (AddressException e) {
				            e.printStackTrace();
				        }
				         return address;
		    }
		  
}
