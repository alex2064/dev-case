package info.devcase.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	public String sendMail(String sender, ArrayList<String> receivers, String subject, String text) throws Exception{
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception{
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(new InternetAddress(sender));
				
				InternetAddress[] address = new InternetAddress[receivers.size()];
				for(int i=0; i<receivers.size(); i++){
					address[i] = new InternetAddress(receivers.get(i)); 
				}
				helper.setTo(address);
				helper.setSubject(subject);
				helper.setText(text);
			}
		};
		
		mailSender.send(preparator);
		
		return "success";
	}
	
	public String sendMailFile(String sender, ArrayList<String> receivers, String subject, String text, ArrayList<HashMap<String, String>> attachList ) throws Exception{
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception{
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(new InternetAddress(sender));
				
				InternetAddress[] address = new InternetAddress[receivers.size()];
				for(int i=0; i<receivers.size(); i++){
					address[i] = new InternetAddress(receivers.get(i)); 
				}
				helper.setTo(address);
				helper.setSubject(subject);
				helper.setText(text);
				
				FileSystemResource file = null;
				for(HashMap<String, String> hashMap : attachList) {
					file = new FileSystemResource(new File(hashMap.get("filePath") + "/" + hashMap.get("fileSeq")));
					helper.addAttachment(hashMap.get("fileName").toString(), file);
				}
			}
		};
		
		mailSender.send(preparator);
		
		return "success";
	}
}
