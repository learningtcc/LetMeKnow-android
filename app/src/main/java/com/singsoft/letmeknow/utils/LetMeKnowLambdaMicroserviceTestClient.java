package com.singsoft.letmeknow.utils;

/**
 * Created by meidan.zemer on 1/21/2017.
 */

@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = "https://8rd0wztxo5.execute-api.eu-west-1.amazonaws.com/Dev")
public interface LetMeKnowLambdaMicroserviceTestClient {


    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);
}


