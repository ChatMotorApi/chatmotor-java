package com.chatmotorapi.test.core.misc.notify;


import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.notify.ExecutionMode;
import com.chatmotorapi.api.notify.MotorNotifyResponse;
import com.chatmotorapi.api.notify.SlackNotificationChannel;

public class SlackNotificationChannelSample {

    public static void main(String[] args) {

        String token = "[Slak Token]"; 
        String channel = "[Slak Channel ID]"; 
        
        String filePath = "/path/to/your/ai-treated-file.txt";

	// Build a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();
	
        // Create an instance of SlackNotificationChannel 
        SlackNotificationChannel treatment 
            = new SlackNotificationChannel.Builder()
        	.chatMotor(chatMotor)
                .token(token)
                .channel(channel)
                .build();

        // Define the execution mode for the messages or files
        ExecutionMode executionMode = ExecutionMode.SYNC;
        
        // Send a notification message Slack channel
        String message = "Your AI processed document is ready!";
        MotorNotifyResponse motorNotifyResponse 
        	= treatment.notifyDocumentAvailable(message, 
        		executionMode);
        
        // And/Or send a file to Slack channel
        motorNotifyResponse 
            = treatment.dispatchDocument(filePath, executionMode);
        
        // Handle the send file response
        if (motorNotifyResponse.isResponseOk()) {
            System.out.println("File has been sent successfully.");
        }
        else {
            System.out.println("Failed to send file: " 
        	    + "Error: " + motorNotifyResponse.getThrowable());
        }
  
    }
}
