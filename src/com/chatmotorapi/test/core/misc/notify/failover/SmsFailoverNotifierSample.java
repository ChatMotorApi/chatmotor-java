package com.chatmotorapi.test.core.misc.notify.failover;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.notify.failover.SmsFailoverNotifier;

    public class SmsFailoverNotifierSample {
        @SuppressWarnings("unused")
        public static void main(String[] args) throws Exception {
            Logger logger = LoggerFactory.getLogger(SmsFailoverNotifierSample.class);
            
            String accountSid = "[Twilio Account SID]"; // Replace with your Twilio Account SID
            String authToken = "[Twilio Auth Token]"; // Replace with your Twilio Auth Token
            String recipientPhoneNumber = "+88888888888"; // The phone number to notify
            String fromPhoneNumber = "+99999999999"; // The Twilio phone number used to send the message
            
            List<String> phoneNumbers = Arrays.asList(recipientPhoneNumber);
            String messageText = "The main ChatMotor API Key is down. Switching to the failover ChatMotor API Key....";
            
            // Create a new SmsFailoverNotifier instance using the builder pattern
            SmsFailoverNotifier smsFailoverNotifier = SmsFailoverNotifier.builder()
                .logger(logger)
                .accountSid(accountSid)
                .authToken(authToken)
                .phoneNumbers(phoneNumbers)
                .messageText(messageText)
                .fromPhoneNumber(fromPhoneNumber)
                .build();
            
            String failoverApiKey = "[Failover OpenAI API Key]"; // Replace with your failover API key
            ChatMotor chatMotor = ChatMotor.builder()
                .failoverApiKey(failoverApiKey)
                .failoverNotifier(smsFailoverNotifier) // Set the notifier to the ChatMotor instance
                .build();
            
            // The ChatMotor instance is now ready to use, handling failover with SMS notifications
        }
   

}
