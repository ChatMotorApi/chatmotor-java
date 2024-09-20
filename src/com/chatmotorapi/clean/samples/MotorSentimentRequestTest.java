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

package com.chatmotorapi.clean.samples;

import java.io.File;
import java.util.Date;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.functional.MotorSentiment;
import com.chatmotorapi.api.functional.MotorSentimentRequest;
import com.chatmotorapi.api.functional.MotorSentimentResponse;
import com.chatmotorapi.api.functional.SentimentType;

public class MotorSentimentRequestTest {


    public static void main(String[] args)  throws Exception  {
	System.out.println(new Date() + " Begin...");
	
	String filePath = SamplesParms.SAMPLE_FILES_DIR +  File.separator+ "sentiment.txt";
	
	//filePath = "/path/to/sentiments-sourcetxt";

	// Build a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder()
		    .build();
	
	 // Make a Sentiment Analysis request to the ChatMotor.
	 MotorSentimentRequest request = MotorSentimentRequest.builder()
		.chatMotor(chatMotor)
		.filePath(filePath)
		.build();
	 
         // Execute the request.
	 MotorSentimentResponse response = request.execute();
	 
	 if (response.isResponseOk()) {
	     System.out.println("response: ");
	     MotorSentiment motorSentiment = response.getMotorSentiment();
	     SentimentType sentiment = motorSentiment.sentiment();
	     
	     // POSITIVE, NEGATIVE, NEUTRAL
	     System.out.println("sentiment : " + sentiment); 
	     
	     // Confidence scores for each sentiment type.
	     // Ex: ConfidenceScores [positive=85, negative=10, neutral=5]
	     System.out.println("scores    : " + motorSentiment.getConfidenceScores());
	 } else {
	     System.out.println("throwable   : " + response.getThrowable());
	 }

	 System.out.println();
	 System.out.println(new Date() + " End.");
    }
    
}
