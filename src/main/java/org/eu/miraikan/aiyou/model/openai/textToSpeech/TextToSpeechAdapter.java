package org.eu.miraikan.aiyou.model.openai.textToSpeech;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.model.openai.textToSpeech.template.TextToSpeechRequest;
import org.eu.miraikan.aiyou.model.openai.OpenAIAdapter;
import org.eu.miraikan.aiyou.types.Blob;
import org.eu.miraikan.aiyou.types.Candidate;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextToSpeechAdapter extends OpenAIAdapter<byte[]> {
    ObjectMapper objectMapper;
    //default value
    int chunkSize=100*1024;



    public TextToSpeechAdapter(Map<String, String> config) {
        OPENAI_API_KEY = config.get("OPENAI_API_KEY");
        BASE_URL = config.get("BASE_URL")==null?BASE_URL:config.get("BASE_URL");
        objectMapper = new ObjectMapper();
    }

    public HttpRequest createHttpRequest(TextToSpeechRequest textToSpeechRequest) throws Exception {







        String json = objectMapper.writeValueAsString(textToSpeechRequest);



        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1/audio/speech"))
                .header("Content-Type", "application/json")
                .header("Authorization"," Bearer "+OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;
    }







    public Blob handleHttpResponse(HttpResponse<byte[]> response) throws Exception{

        return new Blob("mp3",response.body());

    }

    //httpClient has deal with chunk encoding
    @Override
    public byte[] handleStream(InputStream is)  {



        byte[] chunk = new byte[chunkSize];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead=0;
        int totalBytesRead = 0;

        try {
            while (totalBytesRead < chunkSize && (bytesRead = is.read(chunk)) != -1) {
                buffer.write(chunk, 0, bytesRead);
                totalBytesRead += bytesRead;
            }

            if (totalBytesRead == 0) {
                is.close();
                return null;
            }

        } catch (IOException e) {

            e.printStackTrace();
        }




        return buffer.toByteArray();



    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

}
