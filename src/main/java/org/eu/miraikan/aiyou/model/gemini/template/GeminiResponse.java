package org.eu.miraikan.aiyou.model.gemini.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.types.Candidate;
import org.eu.miraikan.aiyou.types.Part;

import java.util.List;
import java.util.Optional;

/**
 * Not support promptFeedback yet
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {
    List<Candidate> candidates;

    /**
     * Convenient function that will return a Text or FunctionCall.
     * Could lead to nullPointException, in cause of error response.
     * Other detail can be retrieved from candidate.
     * @return need to be cast to Text or FunctionCall.
     */
    public Optional<Part> getResponseMessage(){
        try{
         return Optional.ofNullable(candidates.get(0).getContent().getParts().get(0));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
