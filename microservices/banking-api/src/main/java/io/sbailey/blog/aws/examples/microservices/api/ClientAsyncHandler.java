package io.sbailey.blog.aws.examples.microservices.api;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;


public class ClientAsyncHandler extends AsyncCompletionHandler<Response> {

	
	@Override
	public Response onCompleted(Response response) throws Exception {
		return response;
	}
	
}
