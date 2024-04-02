package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * A Chunk is a subpart of a Document，
 * that is treated as an independent unit for the purposes of vector representation and storage.
 * A Corpus can have a maximum of 1 million Chunks.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chunk {
    String name;
    //chunkData
    ChunkData data;
    List<CustomMetadata> customMetadata;
    Date createTime;
    Date updateTime;
    //inner enum
    String state;

    public void setChunkData(String data){
        this.data= new ChunkData(data);
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChunkData{
        String stringValue;
    }

    public static enum State{
        STATE_UNSPECIFIED (0),
        STATE_PENDING_PROCESSING(1),
        STATE_ACTIVE (2),
        STATE_FAILED (10);

            private int value;

            State(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }



    }


}
