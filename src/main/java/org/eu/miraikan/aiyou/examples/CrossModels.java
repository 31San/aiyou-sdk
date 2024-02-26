package org.eu.miraikan.aiyou.examples;

import java.io.IOException;

public class CrossModels {
    public static void main(String[] args) throws IOException, InterruptedException {
        String prompt = "please generate prompt of a beautiful picture";
        String textResponse = new TextGeneration().geminiGenerateContent(prompt);
        new TextToImage().stableDiffusion(textResponse);

        System.out.println(prompt);
    }

}
