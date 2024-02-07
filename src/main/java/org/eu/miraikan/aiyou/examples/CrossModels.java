package org.eu.miraikan.aiyou.examples;

public class CrossModels {
    public static void main(String[] args) throws Exception {
        String prompt = "please generate prompt of a beautiful picture";
        String textResponse = new TextGeneration().geminiGenerateContent(prompt);
        new TextToImage().stableDiffusion(textResponse);

        System.out.println(prompt);
    }

}
