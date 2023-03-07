package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBInput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetRating {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("GetRating")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            @CosmosDBInput(name = "database",
                databaseName = "hackathon",
                collectionName = "ratings",
                id = "{Query.ratingId}",
                connectionStringSetting = "cosmosdb")
            Optional<String> item,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        if (item.isPresent()) {
            return request.createResponseBuilder(HttpStatus.OK).body(item.get()).build();            
        } else {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).build();
        }
    }
}
