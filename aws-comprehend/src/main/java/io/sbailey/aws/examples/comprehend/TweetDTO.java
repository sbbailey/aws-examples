package io.sbailey.aws.examples.comprehend;

public class TweetDTO {
	
	private String source;
	private String text;
	private String created;
	private String reTweetCount;
	private String favCount;
	private String isRetweet;
	private String idStr;
	
	private String googleMagnitude;
	private String googlePolarity;
	private String awsSentiment;
	private String satalyticsSentiment;
	
	
	
	
	
	@Override
	public String toString() {
		return source + "," + text + "," + created + "," + reTweetCount + "," + favCount + "," + isRetweet + "," + idStr + "," + googleMagnitude + "," + googlePolarity + "," + awsSentiment + "," + satalyticsSentiment;
	}
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getReTweetCount() {
		return reTweetCount;
	}
	public void setReTweetCount(String reTweetCount) {
		this.reTweetCount = reTweetCount;
	}
	public String getFavCount() {
		return favCount;
	}
	public void setFavCount(String favCount) {
		this.favCount = favCount;
	}
	public String getIsRetweet() {
		return isRetweet;
	}
	public void setIsRetweet(String isRetweet) {
		this.isRetweet = isRetweet;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public String getGoogleMagnitude() {
		return googleMagnitude;
	}
	public void setGoogleMagnitude(String googleMagnitude) {
		this.googleMagnitude = googleMagnitude;
	}
	public String getGooglePolarity() {
		return googlePolarity;
	}
	public void setGooglePolarity(String googlePolarity) {
		this.googlePolarity = googlePolarity;
	}
	public String getAwsSentiment() {
		return awsSentiment;
	}
	public void setAwsSentiment(String awsSentiment) {
		this.awsSentiment = awsSentiment;
	}
	public String getSatalyticsSentiment() {
		return satalyticsSentiment;
	}
	public void setSatalyticsSentiment(String satalyticsSentiment) {
		this.satalyticsSentiment = satalyticsSentiment;
	}
	
	
	

}
