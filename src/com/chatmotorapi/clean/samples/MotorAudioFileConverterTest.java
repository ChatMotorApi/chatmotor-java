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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import com.chatmotorapi.api.ChatMotor;
import com.chatmotorapi.api.MotorLargeResponse;
import com.chatmotorapi.api.transcription.MotorAudioFileConverter;
import com.chatmotorapi.api.transcription.MotorTranscriptionDuration;
import com.chatmotorapi.gpt.api.AndroidUtil;

public class MotorAudioFileConverterTest {

    public static void main(String[] args) throws IOException {

	System.out.println(new Date() + " Begin...");

	if (AndroidUtil.isAndroid()) {
	    throw new IllegalStateException("This test does not run on Android.");
	}
	
	// We assume that the env var MOTOR_API_KEY is set
	// Create a ChatMotor instance.
	ChatMotor chatMotor = ChatMotor.builder().build();
	
	String audioFilePath = SamplesParms.SAMPLE_FILES_DIR +  File.separator + "sunalsorises_01_hemingway_64kb.mp3";
	
	// Build the transcription request.
	MotorAudioFileConverter  motorAudioFileConverter  = MotorAudioFileConverter.builder()
		.chatMotor(chatMotor)
		.inputFilePath(audioFilePath)
		.conversionFormat("mp4")
		.build();

	MotorLargeResponse largeResponse = motorAudioFileConverter.execute();


	if (largeResponse.isResponseOk()) {
	    String convertedAudioFilePath = audioFilePath + ".mp4";
	    try (InputStream in = largeResponse.getInputStream()) {
		Files.copy(in, Paths.get(convertedAudioFilePath), StandardCopyOption.REPLACE_EXISTING);
	    }

	    System.out.println("convertedAudioFilePath:");
	    System.out.println(convertedAudioFilePath);

	    if (!AndroidUtil.isAndroid()) {
		int duration = MotorTranscriptionDuration.getDuration(chatMotor, new File(audioFilePath));
		System.out.println("Duration in seconds: " + duration);
	    }

	} else {
	    
	}
	
	System.out.println(new Date() + " End.");

    }

}
