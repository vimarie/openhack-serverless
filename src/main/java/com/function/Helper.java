package com.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Helper {
    public static String sendGET(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
            return response.toString();
		} else {
			throw new IOException();
		}
	}

    public static String getProduct(String id) throws IOException {
        return Helper.sendGET("https://serverlessohapi.azurewebsites.net/api/GetProduct?productId=" + id);
    }

	public static String getUser(String id) throws IOException {
        return Helper.sendGET("https://serverlessohapi.azurewebsites.net/api/GetUser?userId=" + id);
    }
}
