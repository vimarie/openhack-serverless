package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;

/**
 * Azure Functions with HTTP Trigger.
 */
public class CreateRating {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    /*
     * {
        "id": "79c2779e-dd2e-43e8-803d-ecbebed8972c",
        "userId": "cc20a6fb-a91f-4192-874d-132493685376",
        "productId": "4c25613a-a3c2-4ef3-8e02-9c335eb23204",
        "timestamp": "2018-05-21 21:27:47Z",
        "locationName": "Sample ice cream shop",
        "rating": 5,
        "userNotes": "I love the subtle notes of orange in this ice cream!"
        }
     */
    @FunctionName("CreateRating")
    @CosmosDBOutput(name = "database",
      databaseName = "hackathon",
      collectionName = "ratings",
      createIfNotExists = true,
      connectionStringSetting = "cosmosdb")
    public String run(
            @HttpTrigger(name = "req",
              methods = {HttpMethod.POST},
              authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Document to be saved: " + request.getBody());
        JSONObject jo = new JSONObject(request.getBody().get());

        // Generate random ID
        final String id = UUID.randomUUID().toString();

        // Generate document
        /*final String jsonDocument = "{\"id\":\"" + id + "\", " +
                                    "\"description\": \"" + name + "\"}";*/

        String jsonDocument = """
            {
                "id": "%s",
                "userId": "%s",
                "productId": "%s",
                "timestamp": "%s",
                "locationName": "%s",
                "rating": %d,
                "userNotes": "%s"
            }
                """.formatted(id, jo.getString("userId"), jo.getString("productId"), LocalDateTime.now().toString(), jo.getString("locationName"), jo.getInt("rating"), jo.getString("userNotes"));

        
        context.getLogger().info("Document to be saved: " + jsonDocument);

        return jsonDocument;
    }
}
