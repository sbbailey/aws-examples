package io.sbailey.aws.examples.comprehend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.google.gson.Gson;
import com.satalytics.common.aws.comprehend.SentimentRequestResult;
import com.satalytics.common.dto.services.google.GoogleSentimentAnalysisRequestDTO;
import com.satalytics.common.enums.SatalyticsLanguage;
import com.satalytics.common.enums.naturallanguage.AWSSentiment;
import com.satalytics.common.enums.naturallanguage.SatalyticsSentimentScore;


@Component
public class ComprehendExample {

	@Autowired
	private Environment env;
	
	private AmazonComprehend comprehendClient;
	
	private static final Logger logger = LoggerFactory.getLogger(ComprehendExample.class);
	
	private Gson gson = new Gson();
	
	
	ArrayList<TweetDTO> tweetDTOs = new ArrayList<TweetDTO>();
	
	
	public void runExample(){
		
		HashMap<String, String> sqsQueues = new HashMap<String, String>();
		
		AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
		comprehendClient = AmazonComprehendClientBuilder.standard().withCredentials(awsCreds).withRegion(Regions.EU_WEST_1).build();
		
		logger.info("running the aws comprehend example");
		
		String filename = env.getProperty("samples.source");
		
		logger.info("filename: " + filename);
		
		try {		
			FileReader fileReader = new FileReader(filename);
			
			this.readFile(fileReader);
		} catch (IOException e) {
			logger.error("failed to load file: " + filename, e);
		}
		
	}
	
	
	
	
	public void readFile(FileReader fileReader){
		int linesCount = 0;
		
		try {
			
			BufferedReader reader = new BufferedReader(fileReader);
		    String line = null;
		    while ((line = reader.readLine()) != null) {
//		        logger.info("line: " + line);
		        processSample(line, linesCount++);
		   
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		// flush the tweet list
		this.writeResults();
		
		
		logger.info("Totals lines: " + linesCount);
	}
	
	
	
	public void processSample(String line, int count){
		
		List<String> items = Arrays.asList(line.split(","));
		
		
		if(items.size() == 7){
		
		
			String tweet = items.get(1);
			logger.info("Tweet: " + tweet);
			
			
			TweetDTO dto = new TweetDTO();
			dto.setSource(items.get(0));
			dto.setText(items.get(1));
			dto.setCreated(items.get(2));
			dto.setReTweetCount(items.get(3));
			dto.setFavCount(items.get(4));
			dto.setIsRetweet(items.get(5));
			dto.setIdStr(items.get(6));
			
			this.ProcessTweet(dto);
			logger.info("");
			logger.info("Processed lines: " + count);
			logger.info("");
		}else{
			logger.info("BAD FILE AT LINE: " + count);
			logger.info("line csv size is: " + items.size());
 			logger.info(line);
			System.exit(0);
			
		}
	}
	
	
	
	public void writeResults(){
		logger.info("");
		logger.info("writing next batch of results: " + tweetDTOs.size());
		logger.info("");
		File file = new File("/home/baileys/space-cadet/git/aws-examples/aws-comprehend/target/tweet_analysis-part.csv");
		
		for(TweetDTO tweetDTO : tweetDTOs){			
		    try {
				FileUtils.writeStringToFile(file, tweetDTO.toString() + "\n", StandardCharsets.UTF_8, true);
				
			} catch (IOException e) {
				logger.error("failed to write to file", e);
			}
		}
		
		
		
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		PrintWriter out = null;
//		
//		try{
//			fw = new FileWriter("/home/baileys/space-cadet/git/aws-examples/aws-comprehend/target/tweet_analysis.csv", true);
//		    bw = new BufferedWriter(fw);
//		    out = new PrintWriter(bw);
//			for(TweetDTO tweetDTO : tweetDTOs){
//				logger.info("RESULT: " + tweetDTO.toString());
//				out.println(tweetDTO.toString());
//			}
//			    
//		} catch (Exception e) {
//		    logger.error("failed to write to file", e);
//		} finally {
//			try {
//				fw.close();
//				bw.close();
//				out.close();
//			} catch (Exception e) {
//				
//			}
//		}
//		
		
		tweetDTOs.clear();
	}
	
	
	
	public void ProcessTweet(TweetDTO tweetDTO){
		// do the google analysis
//		GoogleSentimentAnalysisRequestDTO dto = this.doGoogleSentiment(tweetDTO.getText());
//		logger.info("polarity: " + dto.getPolarityScore());
//		logger.info("magnitude: " + dto.getMagnitude());
//		tweetDTO.setGoogleMagnitude(dto.getMagnitude().toString());
//		tweetDTO.setGooglePolarity(dto.getPolarityScore().toString());
		// do the aws analysis
		AWSSentiment awsSentiment = this.doSentimentAnalysis(tweetDTO.getText(), SatalyticsLanguage.English);
		logger.info("comprehend: " + awsSentiment.getAwsValue());
		tweetDTO.setAwsSentiment(awsSentiment.getAwsValue());
		// do the satalytics score
		SatalyticsSentimentScore satalyticsSentimentScore = this.calculateSatalyticsSentimentRating(dto.getMagnitude(), dto.getPolarityScore(), awsSentiment);
		tweetDTO.setSatalyticsSentiment(satalyticsSentimentScore.getLabel());
		logger.info("sentiment: " + satalyticsSentimentScore.getLabel());
		// store results - if results = N - append to file
		tweetDTOs.add(tweetDTO);
		if(tweetDTOs.size() > 50){
			this.writeResults();
		}
		
	}
	
	
	
	public GoogleSentimentAnalysisRequestDTO doGoogleSentiment(String tweet){
		GoogleSentimentAnalysisRequestDTO dto = new GoogleSentimentAnalysisRequestDTO();
		dto.setRequestText(tweet);
		try {
			
			String address = "http://internal-stg-services-lb-51127282.eu-west-1.elb.amazonaws.com/google/dosentimentanalysis";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(address);
			
			String requestJson = gson.toJson(dto);
			
			StringEntity entity = new StringEntity(requestJson);
			
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		
			CloseableHttpResponse response = httpclient.execute(httpPost);
			
			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);
			
			logger.info("Response: " + apiOutput);
			
			dto = gson.fromJson(apiOutput, GoogleSentimentAnalysisRequestDTO.class);
			
		} catch (Exception e) {
			
		} 
		
		return dto;
	}
	
	
	
	
	
	public AWSSentiment doSentimentAnalysis(String text, SatalyticsLanguage satalyticsLanguage){
		AWSSentiment awsSentiment = null;
		
		SentimentRequestResult result = this.detectSentimentRequest(text, satalyticsLanguage.getLanguageCode());
		if(result != null){			
			awsSentiment = AWSSentiment.getAWSSentiment(result.getSentiment());
		}
		
		return awsSentiment;
	}
	
	
	public SentimentRequestResult detectSentimentRequest(String text, String langCode) {
		SentimentRequestResult sentimentRequestResult = null;
		
		try {
			DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(text)
			        .withLanguageCode(langCode);		
			DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
			
			sentimentRequestResult = new SentimentRequestResult();
			sentimentRequestResult.setSentiment(detectSentimentResult.getSentiment());
		} catch (Exception e) {
	
		}
		return sentimentRequestResult;
	}
	
	
	
	
	/**
	 * calculates the satalytics sentiment rating 
	 * 
	 * @param magnitude
	 * @param polarityScore
	 * @return
	 */
	public SatalyticsSentimentScore calculateSatalyticsSentimentRating(double magnitude, double polarityScore, AWSSentiment awsSentiment){
		SatalyticsSentimentScore sentimentScore = SatalyticsSentimentScore.NotScored;
		
		
		// assess the google score first
		SatalyticsSentimentScore googleScore = SatalyticsSentimentScore.NotScored;
		// check for neutral and mixed 
		if(polarityScore == 0.0d){
			googleScore = SatalyticsSentimentScore.NeutralMixed;
		}
		// check for negative 
		if(polarityScore < 0.0d){
			// see if this is strong magnitude or not 
			if(magnitude >= 1.0){
				googleScore = SatalyticsSentimentScore.VeryNegative;
			}else{
				googleScore = SatalyticsSentimentScore.Negative;
			}
		}
		// check for positive
		if(polarityScore > 0.0d){
			// see if this is strong magnitude or not 
			if(magnitude >= 1.0){
				googleScore = SatalyticsSentimentScore.VeryPositive;
			}else{
				googleScore = SatalyticsSentimentScore.Positive;
			}
		}
		
		if(awsSentiment != null){
			// now factor in the aws score to the final sentiment score
			switch (googleScore) {
				case Negative:				
					if(awsSentiment.getId() == AWSSentiment.POSITIVE.getId() || awsSentiment.getId() == AWSSentiment.NEUTRAL.getId()){
						sentimentScore = SatalyticsSentimentScore.NeutralMixed;
					}else{
						sentimentScore = googleScore;
					}
					break;
				case VeryNegative:
					if(awsSentiment.getId() == AWSSentiment.POSITIVE.getId() || awsSentiment.getId() == AWSSentiment.NEUTRAL.getId() || awsSentiment.getId() == AWSSentiment.MIXED.getId()){
						sentimentScore = SatalyticsSentimentScore.Negative;
					}else {
						sentimentScore = googleScore;
					}
					break;
				case Positive:
					if(awsSentiment.getId() == AWSSentiment.NEGATIVE.getId() || awsSentiment.getId() == AWSSentiment.NEUTRAL.getId()){
						sentimentScore = SatalyticsSentimentScore.NeutralMixed;
					}else{
						sentimentScore = googleScore;
					}
					break;
				case VeryPositive:
					if(awsSentiment.getId() == AWSSentiment.NEGATIVE.getId() || awsSentiment.getId() == AWSSentiment.NEUTRAL.getId() || awsSentiment.getId() == AWSSentiment.MIXED.getId()){
						sentimentScore = SatalyticsSentimentScore.Positive;
					}else {
						sentimentScore = googleScore;
					}
					break;
				case NeutralMixed:				
					if(awsSentiment.getId() == AWSSentiment.POSITIVE.getId()){
						sentimentScore = SatalyticsSentimentScore.Positive;
					}else if(awsSentiment.getId() == AWSSentiment.NEGATIVE.getId()){
						sentimentScore = SatalyticsSentimentScore.Negative;
					}else{
						sentimentScore = googleScore;
					}
					break;
				case NotScored:
					// should never be called - do nothing
					break;
			}
		}else{
			sentimentScore = googleScore;
		}
		
		
		return sentimentScore;
	}

}
