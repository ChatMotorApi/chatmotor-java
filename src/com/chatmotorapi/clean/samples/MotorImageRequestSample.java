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
import java.util.List;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.MotorDefaultsModels;
import com.chatmotorapi.api.OpenAiError;
import com.chatmotorapi.api.OpenAiErrorType;
import com.chatmotorapi.api.image.MotorImageContainer;
import com.chatmotorapi.api.image.MotorImageRequest;
import com.chatmotorapi.api.image.MotorImageResponse;
import com.chatmotorapi.api.image.MotorImageSize;
import com.chatmotorapi.api.image.ResponseImageFormat;

public class MotorImageRequestSample {


    public static void main(String[] args)  throws Exception  {
	chatMotorApi();
    }
    
    /**
     * ChatMotor usage
     */
    
    public static void chatMotorApi() {
	
	System.out.println(new Date() + " Begin...");
	
	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder()
		.build();
	
	String prompt = "A modern, sleek illustration of a Java program connecting to ChatGPT. "
		+ "The image features a laptop screen with a clean, dark-themed IDE open, "
		+ "displaying Java code that shows a Main class with methods to connect to the ChatGPT API. "
		+ "The overall aesthetic is professional and polished, suitable for a LinkedIn audience.";

	prompt = "Realistic image, a beautifull young woman smilingly reading a book on her laptop, with the text 'Java Program connecting to ChatGPT'";
	
	// Make a request to the ChatMotor.
	MotorImageRequest motorImageRequest = MotorImageRequest.builder()
		.chatMotor(chatMotor)
		.aiModel(MotorDefaultsModels.MOTOR_IMAGE_MODEL)
		.n(1)
		.prompt(prompt)
		.motorImageSize(MotorImageSize.X1024)
		.responseImageFormat(ResponseImageFormat.URL)
		.build();

	// Execute the image request.
	MotorImageResponse motorImageResponse = motorImageRequest.execute();

	if (motorImageResponse.isResponseOk()) {
	    List<MotorImageContainer> motorImageContainers = motorImageResponse.getImageContainer();
	    for (MotorImageContainer motorImageContainer : motorImageContainers) {
		System.out.println(motorImageContainer.getUrl());
	    }
	    System.out.println();
	} else {
	    OpenAiError openAiError = motorImageResponse.getOpenAiError();
	    OpenAiErrorType errorType = openAiError.getType();

	    if (errorType != OpenAiErrorType.NO_OPENAI_ERROR) {
		System.out.println("OpenAI has returned an error : " + openAiError);

		// Take specific action depending on the error code:
		if (errorType.equals(OpenAiErrorType.SERVER_ERROR)) {
		    // ...
		} else if (errorType.equals(OpenAiErrorType.TOKENS)) {
		    // ...
		} else {
		    // Etc.
		}

	    } else {
		System.out.println("throwable: " + motorImageResponse.getThrowable());
	    }
	}

	 System.out.println();
	 System.out.println(new Date() + " End.");
    }
    

}
