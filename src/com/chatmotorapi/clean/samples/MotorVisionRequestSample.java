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

import java.util.Date;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.MotorAiOptions;
import com.chatmotorapi.api.MotorResponse;
import com.chatmotorapi.api.OpenAiError;
import com.chatmotorapi.api.OpenAiErrorType;
import com.chatmotorapi.api.vision.MotorContentPartImage;
import com.chatmotorapi.api.vision.MotorContentPartText;
import com.chatmotorapi.api.vision.MotorVisionRequest;

public class MotorVisionRequestSample {


    public static void main(String[] args)  throws Exception  {
	System.out.println(new Date() + " Begin...");
	
	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();
	
	 // Optionally build a MotorAiOptions instance.
	 MotorAiOptions options = MotorAiOptions.builder()
		    .temperature(0.7) 
		    .maxTokens(1000)    
		    .build();
	 	 
	String text = "Describe what you see in this image.";
	String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d1/" 
		 + "Mount_Everest_as_seen_from_Drukair2_PLW_edit.jpg";
	
	MotorContentPartText motorContentPartText = new MotorContentPartText(text);
	MotorContentPartImage motorContentPartImage = new MotorContentPartImage(
		 imageUrl);

	 // Build a Vision request 
	 MotorVisionRequest visionRequest = MotorVisionRequest.builder()
		.chatMotor(chatMotor)
		.motorAiOptions(options)
		.contentPartText(motorContentPartText)
		.contentPartImage(motorContentPartImage)
		.build();
	 
         // Execute the request.
	 MotorResponse motorResponse = visionRequest.execute();
	 
	 if (motorResponse.isResponseOk()) {
	     String response = motorResponse.getResponse();
             System.out.println(response);
	 }
	 else {
	     OpenAiError openAiError = motorResponse.getOpenAiError();
	     OpenAiErrorType errorType = openAiError.getType();

	     if (errorType != OpenAiErrorType.NO_OPENAI_ERROR) {
		 System.out.println("OpenAI has returned an error : " + openAiError);

		 // Take specific action depending on the error code:
		 if (errorType.equals(OpenAiErrorType.SERVER_ERROR)) {
		     // ...
		 } else if (errorType.equals(OpenAiErrorType.TOKENS)) {
		     // ..
		 } else {
		     // Etc.
		 }

	     } else {
		 System.out.println("throwable   : " + motorResponse.getThrowable());
	     }
	 }
	 
	 System.out.println();
	 System.out.println(new Date() + " End.");
    }
    
    
 

}
