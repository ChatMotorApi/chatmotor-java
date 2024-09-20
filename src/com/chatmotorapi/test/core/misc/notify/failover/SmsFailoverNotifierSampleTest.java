/*
 * ChatMotor API Samples
 *
 * MIT License
 * 
 * Copyright (c) 2024 ChatMotorApi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.chatmotorapi.test.core.misc.notify.failover;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatmotorapi.api.notify.failover.SmsFailoverNotifier;

public class SmsFailoverNotifierSampleTest {

    /**
     * @param args
     */

    public static void main(String[] args) throws Exception {

	System.out.println(new Date() + ": Running SmsFailoverNotifierSample...");
	
        Logger logger = LoggerFactory.getLogger(SmsFailoverNotifierSampleTest.class);
	// Get from user.home confidential information
	String accountSid = getFileContent("twilio_account_sid.txt");
	String authToken = getFileContent("twilio_auth_token.txt");
	String recipientPhoneNumber = getFileContent("twilio_recipient.txt");
	String fromPhoneNumber = getFileContent("twilio_sender.txt");
	
	List<String> phoneNumbers = Arrays.asList(recipientPhoneNumber);
	String messageText = "The main ChatMotor API Key is down. Swithing to the failover ChatMotor API Key....";

	// Create a new SmsFailoverNotifier instance
        SmsFailoverNotifier smsFailoverNotifier = SmsFailoverNotifier.builder()
                .logger(logger)
                .accountSid(accountSid)
                .authToken(authToken)
                .phoneNumbers(phoneNumbers)
                .messageText(messageText)
                .fromPhoneNumber(fromPhoneNumber)
                .build();
        
        // Start the failover notification process	
        smsFailoverNotifier.notifyFailover();
	
	System.out.println(new Date() + ": Done");

    }


    /**
     * Gets the content of a file name located in user.home as a string.
     * @param filename the file name
     * @return the content of the file as a string.
     * @throws IOException
     */
    public static String getFileContent(String filename) throws IOException {
	File file = new File( SystemUtils.USER_HOME + File.separator + filename);
	if (!file.exists() ) {
            throw new IllegalArgumentException("File not found at: " + file.getAbsolutePath());
        }
	String accountSid = FileUtils.readFileToString( file, "UTF-8" );
	return accountSid;
    }

}
