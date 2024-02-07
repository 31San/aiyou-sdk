package org.eu.miraikan.aiyou.model.openai.completions.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//not include optional null
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletionRequest {



    String model;

    List<Message> messages;



    //optional
    Integer n;
    @JsonProperty("max_tokens")
    Integer maxTokens;

    //Penalizes tokens based on their frequency, reducing repetition.
    String frequency_penalty;
    //Modifies likelihood of specified tokens with bias values.
    String logit_bias;
    //Returns log probabilities of output tokens if true.
    String logprobs;
    //Specifies the number of most likely tokens to return at each position.
    String top_logprobs;
    //Penalizes new tokens based on their presence in the text.
    String presence_penalty;
    //Specifies the output format, e.g., JSON mode.
    String response_format;
    //Ensures deterministic sampling with a specified seed.
    String seed;
    //Specifies up to 4 sequences where the API should stop generating tokens.
    String stop;
    //Sends partial message deltas as tokens become available.
    Boolean stream;
    //Sets the sampling temperature between 0 and 2.
    String temperature;
    //Uses nucleus sampling; considers tokens with top_p probability mass.
    String top_p;
    //Lists functions the model may call.
    String tools;
    //Controls the model's function calls (none/auto/function).
    String tool_choice;
    //Unique identifier for end-user monitoring and abuse detection.
    String user;

    public String getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(String frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public String getLogit_bias() {
        return logit_bias;
    }

    public void setLogit_bias(String logit_bias) {
        this.logit_bias = logit_bias;
    }

    public String getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(String logprobs) {
        this.logprobs = logprobs;
    }

    public String getTop_logprobs() {
        return top_logprobs;
    }

    public void setTop_logprobs(String top_logprobs) {
        this.top_logprobs = top_logprobs;
    }

    public String getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(String presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public String getResponse_format() {
        return response_format;
    }

    public void setResponse_format(String response_format) {
        this.response_format = response_format;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTop_p() {
        return top_p;
    }

    public void setTop_p(String top_p) {
        this.top_p = top_p;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getTool_choice() {
        return tool_choice;
    }

    public void setTool_choice(String tool_choice) {
        this.tool_choice = tool_choice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }
}
