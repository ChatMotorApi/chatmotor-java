package com.chatmotorapi.test.core.misc.notify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.notify.ExecutionMode;
import com.chatmotorapi.api.notify.MotorNotifyResponse;
import com.chatmotorapi.api.notify.TwilioNotificationChannel;
import com.chatmotorapi.test.core.misc.notify.failover.SmsFailoverNotifierSampleTest;

public class TwilioNotificationChannelTest {
    

    public static void main(String[] args) throws Exception {
	
	System.out.println(new Date() + ": Running TwilioNotificationChannelTest...");
	
        // Initialize logger
        Logger logger = LoggerFactory.getLogger(TwilioNotificationChannelTest.class);

        // SMS details
        String messageText = "This is a test message from TwilioNotificationChannel.";

	String accountSid = SmsFailoverNotifierSampleTest.getFileContent("twilio_account_sid.txt");
	String authToken = SmsFailoverNotifierSampleTest.getFileContent("twilio_auth_token.txt");
	String recipientPhoneNumbers = SmsFailoverNotifierSampleTest.getFileContent("twilio_recipient.txt");
	String fromPhoneNumber = SmsFailoverNotifierSampleTest.getFileContent("twilio_sender.txt");
	
        List<String> phoneNumbers = Arrays.asList(recipientPhoneNumbers); // Replace with recipient phone numbers
        
	// Build a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();
	
        // Create an instance using the Builder pattern
        TwilioNotificationChannel twilioNotifier = new TwilioNotificationChannel.Builder()
        	.chatMotor(chatMotor)
                .logger(logger)
                .accountSid(accountSid)
                .authToken(authToken)
                .fromPhoneNumber(fromPhoneNumber)
                .phoneNumbers(phoneNumbers)
                .messageText(messageText)
                .build();

        // Sample call to notifyResponseAvailable in SYNC mode
        String filePath = "/dummy/path/to/file.txt"; // This is a placeholder for the file path
        ExecutionMode executionMode = ExecutionMode.SYNC; // Change to ASYNC if needed

        MotorNotifyResponse motorNotifyResponse = twilioNotifier.notifyDocumentAvailable(filePath, executionMode);
        System.out.println("MotorNotifyResponse: " + motorNotifyResponse);
        
        motorNotifyResponse = twilioNotifier.dispatchDocument(filePath, executionMode);
        System.out.println("MotorNotifyResponse: " + motorNotifyResponse);
        
        System.out.println(new Date() + ": TwilioNotificationChannelTest completed.");
    }
}
