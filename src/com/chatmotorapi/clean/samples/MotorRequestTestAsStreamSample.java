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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.MotorAiOptions;
import com.chatmotorapi.api.MotorMessage;
import com.chatmotorapi.api.MotorRequest;
import com.chatmotorapi.api.MotorStreamStatus;
import com.chatmotorapi.api.MotorSystemMessage;
import com.chatmotorapi.api.MotorUserMessage;
import com.chatmotorapi.api.OpenAiError;
import com.chatmotorapi.api.OpenAiErrorType;
import com.chatmotorapi.api.listener.ConsoleResponseListener;
import com.chatmotorapi.api.listener.MotorResponseListener;

public class MotorRequestTestAsStreamSample {

    public static void main(String[] args)  throws Exception  {
	System.out.println(new Date() + " ChatMotorApiInChunks Begin...");
	 
	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();
	
	 // We optionally build a MotorAiOptions instance.
	 MotorAiOptions options = MotorAiOptions.builder()
		    .temperature(0.0)    // Controls the randomness of the AI responses
		    .maxTokens(1500)      // Defines the maximum number of tokens in each response
		    .build();
	 
	 List<MotorMessage> motorMessages = new ArrayList<MotorMessage>();
	 motorMessages.add(new MotorSystemMessage("You are an expert in AI."));
	 motorMessages.add(new MotorUserMessage("Write a technical article about ChatGPT, no more than 1500 words."));

	 // We make a request to the ChatMotor.
	 MotorRequest motorRequest = MotorRequest.builder()
		.chatMotor(chatMotor)
		//.aiModel("gpt-4")
		.motorAiOptions(options)
		.messages(motorMessages)
		.build();
	 
         // We execute the request.
	 MotorResponseListener consoleResponseListener = new ConsoleResponseListener();
	 
	 // Get error details if any.
	 MotorStreamStatus motorStreamStatus = motorRequest.executeAsStream(consoleResponseListener);
	 
	 // Check if the response is ok.
	 if (motorStreamStatus.isResponseOk()) {
	     // Nothing to do here. Display is done by the listener.
	 } else {
	     // If the response is not ok, we get the error message and the throwable.
	     OpenAiError openAiError = motorStreamStatus.getOpenAiError();
	     OpenAiErrorType errorType = openAiError.getType();

	     if (errorType != OpenAiErrorType.NO_OPENAI_ERROR) {
		 System.out.println("OpenAI has returned an error : " + openAiError);
	     } else {
		 System.out.println("throwable   : " + motorStreamStatus.getThrowable());
	     }
	 }
	 
	 System.out.println();
	 System.out.println(new Date() + " ChatMotorApiInChunks End.");
    }


    
    
  
 

}
