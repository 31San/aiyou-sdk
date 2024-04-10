# Aiyou

Aiyou is an  Java SDK dedicated to Serverless AI services.   
It primarily supports Google's Gemini, and is also compatible with OpenAI, as well as other open-source models



## Supported Model

### Google Gemini

1. gemini-pro  
   - text generation
   - streaming
   - functionCalling
2. gemini-pro-vision
3. embedding
4. tuning
5. aqa (semantic retrieval)

### OpenAI

1. chatCompletion
   - text generation
   - streaming
2. text-to-speech

### CloudFlare WorkersAI (Open Source Models)

1. StableDiffusionXL-1.0

## Import

### Maven

````xml
<dependency>
<groupId>org.eu.miraikan</groupId>
<artifactId>aiyou-sdk</artifactId>
<version>0.23</version>
</dependency>
````

### Gradle
````
implementation group: 'org.eu.miraikan', name: 'aiyou-sdk', version: '0.23'
````
# Usage

Oauth is required for Gemini Model Tuning and Semantic Retrieval.

## Tuning

### Gemini Model Tuning

Tuning is available for the gemini-1.0-pro-001.

After setup OAuth, first step is to prepare dateset, then start a tuning job.

Create a tunedModel instance 
```java
TunedModel tunedModel = TunedModel.builder()
                .displayName("next-number")
                .description("return next number")
                .baseModel("models/gemini-1.0-pro-001")
                .build();
```

Build a tuning job

```
        HyperParameters hyperParameters = HyperParameters.builder()
                .learningRate(0.001f)
                .epochCount(5)
                .batchSize(4).build();

        TrainingData<TuningExampleDict> trainingData = new TrainingData<>();
        TrainingData.Examples<TuningExampleDict> examples = new TrainingData.Examples<>();
        examples.setExamples(List.of(
                new TuningExampleDict("2", "3"),
                new TuningExampleDict("-3", "-2"),
                new TuningExampleDict("twenty two", "twenty three"),
                new TuningExampleDict("two hundred", "two hundred one"),
                new TuningExampleDict("ninety nine", "one hundred"),
                new TuningExampleDict("1,000", "1,001"),
                new TuningExampleDict("one", "two"),
                new TuningExampleDict("three", "four"),
                new TuningExampleDict("seven", "eight"),
                new TuningExampleDict("234", "235"),
                new TuningExampleDict("454", "455"),
                new TuningExampleDict("-1", "0"),
                new TuningExampleDict("0", "1"),
                new TuningExampleDict("一", "二"),
                new TuningExampleDict("三", "四"),
                new TuningExampleDict("九", "十"),
                new TuningExampleDict("一百", "一百零一"),
                new TuningExampleDict("一千", "一千零一"),
                new TuningExampleDict("999", "1000")
        ));
        trainingData.setExamples(examples);

        TuningTask tuningTask = TuningTask.builder()
                .trainingData(trainingData)
                .hyperParameters(hyperParameters)
                .build();
        tunedModel.setTuningTask(tuningTask);
```

After Task was submit and completed, this model can be used for text generation.

## Semantic Retrieval

### Gemini Semantic Retrieval

Semantic Retrieval is kind of RAG, which augments the prompt with relevant data retrieved from an external knowledge.

The knowledge base can be own corpora of documents.

Create curpos

```java
Corpus corpus = retriever.createCorpus(Optional.of("testing1"),Optional.of("Google for Developers Blog"));
```

Define document
```java
String docName= "Introducing Project IDX, An Experiment to Improve Full-stack, Multiplatform App Development";
String docMetadata="https://developers.googleblog.com/2023/08/introducing-project-idx-experiment-to-improve-full-stack-multiplatform-app-development.html";
Document document = retriever.createDocument(corpus.getName(),Optional.empty(),Optional.of(docName),
        Optional.of(List.of(new CustomMetadata("url",docMetadata))));
```

Create chunks
```
Chunk chunk1 = new Chunk();
chunk1.setChunkData("Chunks support user specified metadata.");
chunk1.setCustomMetadata(List.of(new CustomMetadata("section","Custom metadata filters")));

Chunk chunk2 = new Chunk();
chunk2.setChunkData("The maximum number of metadata supported is 20");
chunk1.setCustomMetadata(List.of(new CustomMetadata("num_keys",20)));

List<Chunk> chunks = retriever.batchCreateChunks(document.getName(),List.of(chunk1,chunk2));
```

#### Attributed Question-Answering

Model that performs Attributed Question Answering. 

It is trained to return answers to questions that are grounded in provided sources.

Alternatively, you can use the AQA endpoint directly, 
without using the Semantic Retriever API by passing inline_passages.
In this case API key auth is sufficient.

Using semantic retriever with existing corpus source
```java
AQAModel aqaModel = new AQAModel(client);
SemanticRetrieverConfig semanticRetrieverConfig =  SemanticRetrieverConfig.builder()
        .source("corpora/testing1")
        .query(new Content(null,new Text("What is the purpose of Project IDX?")))
        .build();
List<Content> query= List.of(new Content(null,new Text("What is the maximum number of chunk")));
GenerateAnswerRequest generateAnswerRequest = new GenerateAnswerRequest(query,
        GenerateAnswerRequest.AnswerStyle.VERBOSE,semanticRetrieverConfig);
GenerateAnswerResponse generateAnswerResponse = aqaModel.generateAnswer(generateAnswerRequest);
```

## Example
Check example code for detail. Here are some snippet.

### Config restClient


Create restClient instance. Config URL and API_KEY via setClientConfig

```java
Map<String,String> clientConfig;
RestChatClient client = new RestChatClient();
```

### Create GenerativeModel

Use model name to specify.  
```java
GeminiPro model = new GeminiPro(client, Models.GEMINI_1_0_PRO_LATEST);
```
### Build request
```
GeminiRequest generativeRequest = new GeminiRequest();
generativeRequest
    .setContent(new Content(ROLE_USER, new Text(input)))
    .setGenerationConfig(ModelConfigurationHelper.createGeminiModelConfig());
```
### Handling response
```
Iterator<GeminiResponse> iterator = model.generateStreamContent(generativeRequest);

while (iterator.hasNext()){
    Text text1 = (Text) iterator.next().getCandidates().get(0).getContent().getParts().get(0);
    System.out.print(text1.getData());  
}
```
### FunctionCalling
Define function argument


```java
public static  class MovieAndTheater{
    @JsonPropertyDescription("Any movie title")
    public String movie;
    @JsonPropertyDescription("The city and state, e.g. San Francisco, CA or a zip code e.g. 95616")
    @JsonProperty(required = true)
    public String location;
}
```
Declare Open Api function with helper
```java
    FunctionDeclaration functionDeclaration = functionCallingHelper
        .addFunction("find_theaters",MovieAndTheater.class, (MovieAndTheater movieAndTheater) -> fakeResponse)
        .setDescription("find movie titles currently playing in theaters based on any description, genre, title words, etc."); 
```
Async Execute functionCall from response 
```java    
CompletableFuture<Object> message = functionCallingHelper.executeAsync(functionCall.getName(), functionCall.getArgs());
```


### Customize

To add a model, create its own request and response template, that may consist of existing supported types     
Each model has own modelAdapter, which handle most input and output logic.


## Key abstraction

- GenerativeClient  

    Wrapper for clients supporting different protocols, mostly RestChatClient.
     
    

- GenerativeModel

    Represent a LLM.


- ModelAdapter

    Handle model's input and output formation.  



- StreamIterator   

    Provides convenient synchronized streaming support, each call return text or binary chunk. 

## Architecture       
![architecture](./uml.png)

## Dependencies

Json Schema lib introduce SLF4J-API, so a logging implementation is required.



## Route Map

Websocket client, grpc support, maybe Bidirectional stream for audio

Support more open source models, and platform like Azure. 

Looking forward to Gemini 1.5 api official release.

