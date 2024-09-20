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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.MotorAiOptions;
import com.chatmotorapi.api.MotorLargeRequestLines;
import com.chatmotorapi.api.MotorLargeResponse;
import com.chatmotorapi.api.MotorSystemMessage;
import com.chatmotorapi.api.OpenAiError;
import com.chatmotorapi.api.OpenAiErrorType;

public class MotorLargeRequestLinesSample {


    
    public static String CR_LF = System.getProperty("line.separator");

    public static void main(String[] args) throws Exception {
	System.out.println(new Date() + " Begin...");

	String filePath = SamplesParms.SAMPLE_FILES_DIR + File.separator + "temp_fahrenheit.txt";
	String responsePath = SamplesParms.SAMPLE_FILES_DIR + File.separator  + "temp_celsius.csv";
	
	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();

	// Optionally build a MotorAiOptions instance.
	MotorAiOptions options = MotorAiOptions.builder()
		.temperature(0.0) // Controls the randomness of the AI responses
		.maxTokens(4096) // Defines the maximum number of tokens in each response
		.build();

	MotorSystemMessage motorSystemMessage = new MotorSystemMessage(
		    "I will provide you with temperature measurements in Fahrenheit in a text file. "
		    + "Convert the temperatures to degrees Celsius and add ';true' at the end of the line "
		    + "if the temperature is above 20 degrees Celsius. "
		    + "Do not add any comments and maintain the exact input format "
		    + "without adding any new CR/LF characters."
		);
	// We make a request to the ChatMotor.
	MotorLargeRequestLines request = MotorLargeRequestLines.builder()
		.chatMotor(chatMotor)
		.motorAiOptions(options)
		.systemMessage(motorSystemMessage)
		.filePath(filePath)
		.build();

	// We execute the request.
	MotorLargeResponse largeResponse = request.execute();

	if (largeResponse.isResponseOk()) {
	     try (InputStream in = largeResponse.getInputStream();) {
		 Files.copy(in, Paths.get(responsePath), StandardCopyOption.REPLACE_EXISTING);
	     }
	} else {
	    
	    OpenAiError openAiError = largeResponse.getOpenAiError();
	    OpenAiErrorType errorType = openAiError.getType();

	    if (errorType != OpenAiErrorType.NO_OPENAI_ERROR) {
		System.out.println("OpenAI has returned an error : " 
			+ openAiError);

		// Take specific action depending on the error code:
		if (errorType.equals(OpenAiErrorType.SERVER_ERROR)) {
		    // ...
		} else if (errorType.equals(OpenAiErrorType.TOKENS)) {
		    // ..
		} else {
		    // Etc.
		}

	    } else {
		System.out.println("throwable   : " 
			+ largeResponse.getThrowable());
	    }
	}

	System.out.println();
	System.out.println(new Date() + " End.");
    }
  

}
