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
import java.util.List;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.MotorMessage;
import com.chatmotorapi.api.MotorRequest;
import com.chatmotorapi.api.MotorResponse;
import com.chatmotorapi.api.MotorSystemMessage;
import com.chatmotorapi.api.MotorUserMessage;
import com.chatmotorapi.api.OpenAiError;
import com.chatmotorapi.api.OpenAiErrorType;

public class MotorRequestSample {

    public static void main(String[] args) throws Exception {

	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();

	String system = "You are an expert in programming.";
	String user = "Write a simple technical article about Java language";

	List<MotorMessage> motorMessages = new ArrayList<MotorMessage>();
	motorMessages.add(new MotorSystemMessage(system));
	motorMessages.add(new MotorUserMessage(user));

	// Make a request to the ChatMotor.
	MotorRequest motorRequest = MotorRequest.builder().chatMotor(chatMotor).messages(motorMessages).build();

	// Execute the request.
	MotorResponse motorResponse = motorRequest.execute();

	// We check if the response is ok.
	if (motorResponse.isResponseOk()) {
	    String response = motorResponse.getResponse();
	    System.out.println(response);
	} else {
	    OpenAiError openAiError = motorResponse.getOpenAiError();
	    OpenAiErrorType errorType = openAiError.getType();

	    if (errorType != OpenAiErrorType.NO_OPENAI_ERROR) {
		System.out.println("OpenAI has returned an error : " 
			+ openAiError);

		// Take specific action depending on the error code:
		if (errorType.equals(OpenAiErrorType.SERVER_ERROR)) {
		    // ...
		} else if (errorType.equals(OpenAiErrorType.TOKENS)) {
		    // ...
		} else {
		    // Etc.
		}

	    } else {
		System.out.println("throwable: " + motorResponse.getThrowable());
	    }
	}

    }

}
