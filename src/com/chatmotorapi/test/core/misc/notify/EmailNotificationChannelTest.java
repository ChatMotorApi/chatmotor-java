package com.chatmotorapi.test.core.misc.notify;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.notify.EmailNotificationChannel;
import com.chatmotorapi.api.notify.ExecutionMode;

public class EmailNotificationChannelTest {

    // Define CR_LF as a public static constant.
    public static final String CR_LF = System.getProperty("line.separator");
    
    public static String HOSTNAME = "smtp-relay.sendinblue.com";
    public static int PORT = 587;
    public static String USERNAME = "ndepomereu@kawansoft.com";
    public static boolean TLS = false;
    public static boolean SSL = true;
    
    public static String FILE_TO_SEND = "c:\\tmp\\NewYorker.docx.txt";
    
    public static void main(String[] args) throws Exception  {
	
	System.out.println(new Date() + "Sending a message...");
	     
	// Build a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();
	
        // Sample logger instance
        Logger logger = LoggerFactory.getLogger(EmailNotificationChannelTest.class);

        // Sample SMTP server configuration
        String smtpHost = HOSTNAME; // Replace with actual SMTP host
        int smtpPort = PORT; // Replace with actual SMTP port
        String smtpUsername = USERNAME; // Replace with actual SMTP username
        String smtpPassword = FileUtils.readFileToString(new File(SystemUtils.USER_HOME + File.separator + "email_passwd.txt"), "UTF-8");
        String fromAddress = USERNAME; // Replace with actual sender email address
        String toAddress = "npomereu@yahoo.fr"; // Replace with actual recipient email address

        // Sample email subject and message content
        String subject = new Date() + " EmailNotificationChannelTest Email Subject";
        String message = "Hello, " + CR_LF + 
        	 " this is a test email sent from the EmailNotificationChannel class." + CR_LF + 
        	 " Please find attached the test document.";

        // Create an instance of EmailNotificationChannel using the Builder
        EmailNotificationChannel emailNotificationChannel = new EmailNotificationChannel.Builder()
        	.chatMotor(chatMotor)
                .logger(logger)
                .smtpHost(smtpHost)
                .smtpPort(smtpPort)
                .smtpUsername(smtpUsername)
                .smtpPassword(smtpPassword)
                .fromAddress(fromAddress)
                .toAddress(toAddress)
                .subject(subject)
                .message(message)
                .build();

        // Sample file path for attachment
        String filePath = FILE_TO_SEND; // Replace with an actual file path

        // Notify that a document is available
        emailNotificationChannel.notifyDocumentAvailable(filePath, ExecutionMode.ASYNC);

        // Dispatch the actual document
        emailNotificationChannel.dispatchDocument(filePath, ExecutionMode.ASYNC);
        
        System.out.println(new Date() + " Pause for effective sent...");
        Thread.sleep(5000); // Wait for the message to be sent
        
        System.out.println(new Date() + " Message sent successfully!");
    }
}
