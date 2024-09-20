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
import com.chatmotorapi.api.ChunkSize;
import com.chatmotorapi.api.MotorLargeResponse;
import com.chatmotorapi.api.OpenAiError;
import com.chatmotorapi.api.OpenAiErrorType;
import com.chatmotorapi.api.transcription.MotorTranscriptFormatterRequest;


public class MotorTranscripFormatterRequestSample {

    public static void main(String[] args) throws Exception {
	String transcriptionFilePath = SamplesParms.SAMPLE_FILES_DIR + File.separator + "sunalsorises_01_hemingway_64kb.mp3.txt";
	String transcriptionFormattedFilePath = transcriptionFilePath + ".formmatted.txt";
	
	// We assume that the env var MOTOR_API_KEY is set.
	ChatMotor chatMotor = ChatMotor.builder().build();

	MotorTranscriptFormatterRequest formatterRequest = MotorTranscriptFormatterRequest.builder()
		.chatMotor(chatMotor)
		.filePath(transcriptionFilePath)
		.inputChunkSize(ChunkSize.ofKilobytes(10))
		.build();

	MotorLargeResponse largeResponse = formatterRequest.execute();

	if (largeResponse.isResponseOk()) {
	    try (InputStream in = largeResponse.getInputStream()) {
		Files.copy(in, Paths.get(transcriptionFormattedFilePath), StandardCopyOption.REPLACE_EXISTING);
	    }
	} else {
	    OpenAiError openAiError = largeResponse.getOpenAiError();
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

	    }
	}
	
	 System.out.println(new Date() + " End.");

    }

}
