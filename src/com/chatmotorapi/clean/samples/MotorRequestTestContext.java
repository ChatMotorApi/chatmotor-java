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
import com.chatmotorapi.api.MotorMessage;
import com.chatmotorapi.api.MotorRequest;
import com.chatmotorapi.api.MotorStreamStatus;
import com.chatmotorapi.api.MotorSystemMessage;
import com.chatmotorapi.api.MotorUserMessage;
import com.chatmotorapi.api.listener.ConsoleResponseListener;
import com.chatmotorapi.api.listener.MotorResponseListener;

public class MotorRequestTestContext {

    public static void main(String[] args) throws Exception {
	contextTest();
    }

    /**
     * Builds a Java class with a divide(int a, int b) method.
     */
    public static void contextTest() {
	System.out.println(new Date() + " Begin...");

	// Build a ChatMotor instance.
	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();

	// Make context aware requests to ChatGPT with ChatMotor.
	// This simulates a "browser session" with https://chatgpt.com/
	// Context is kept between calls.
	
	List<MotorMessage> motorMessages = new ArrayList<MotorMessage>();
	motorMessages.add(new MotorSystemMessage(
		"You are an expert in Java programming, world class."));

	motorMessages.add(new MotorUserMessage(
		"Write a simple class with an int divide(int a, int b) method. "
		+ "Do not include a main method. " 
		+ "Do not comment your work."));
	buildAndexecuteTheRequest(chatMotor, motorMessages);

	motorMessages.add(new MotorUserMessage(
		"Now add to the class nice and complete Javadoc."));
	buildAndexecuteTheRequest(chatMotor, motorMessages);

	motorMessages.add(new MotorUserMessage(
		"Now catch the divide by zero Exception and "
		+ "print a clean error message if it occurs."));
	buildAndexecuteTheRequest(chatMotor, motorMessages);

	System.out.println(new Date() + " End.");
    }

    /**
     * Build and execute a request, keeping the context.
     * 
     * @param chatMotor     the ChatMotor instance
     * @param motorMessages the messages
     */
    private static void buildAndexecuteTheRequest(ChatMotor chatMotor, List<MotorMessage> motorMessages) {

	MotorRequest motorRequest = MotorRequest.builder().chatMotor(chatMotor).messages(motorMessages).build();

	// Execute the request.
	MotorResponseListener consoleResponseListener = new ConsoleResponseListener();
	MotorStreamStatus motorStreamStatus = motorRequest.executeAsStream(consoleResponseListener);

	if (motorStreamStatus.isResponseOk()) {
	    // Nothing to do here. The response is available in the consoleResponseListener.
	} else {
	    Throwable throwable = motorStreamStatus.getThrowable();
	    System.out.println(throwable);
	}
    }

}
