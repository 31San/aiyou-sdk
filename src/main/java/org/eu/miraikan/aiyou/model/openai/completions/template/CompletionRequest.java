package org.eu.miraikan.aiyou.model.openai.completions.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletionRequest {



    String model;

    List<Message> messages;


    /**
     *  optional
     */
    Integer n;
    @JsonProperty("max_tokens")
    Integer maxTokens;

    /**
     *  Penalizes tokens based on their frequency, reducing repetition.
     */
    String frequency_penalty;
    /**
     *  Modifies likelihood of specified tokens with bias values.
     */

    String logit_bias;
    /**
     *   Returns log probabilities of output tokens if true.
     */
    String logprobs;
    /**
     * Specifies the number of most likely tokens to return at each position.
     */

    String top_logprobs;
    /**
     *  Penalizes new tokens based on their presence in the text.
     */

    String presence_penalty;
    /**
     * Specifies the output format, e.g., JSON mode.
     */
    String response_format;
    /**
     * Ensures deterministic sampling with a specified seed.
     */
    String seed;
    /**
     * Ensures deterministic sampling with a specified seed.
    */
    String stop;
    /**
     * Sends partial message deltas as tokens become available.
     */
    Boolean stream;
    /**
     *   Sets the sampling temperature between 0 and 2.
     */
    String temperature;
    /**
     * Uses nucleus sampling; considers tokens with top_p probability mass.
     */
    String top_p;
    /**
     * Lists functions the model may call.
     */
    String tools;
    /**
     * Controls the model's function calls (none/auto/function).
     */
    String tool_choice;
    /**
     * Unique identifier for end-user monitoring and abuse detection.
     */
    String user;

    /**
     * Set single message
     * @param messages
     */
    public CompletionRequest setMessage(Message messages) {
        this.messages = List.of(messages);
        return this;
    }


}
