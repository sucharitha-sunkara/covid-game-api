package com.covidgame.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class StepDefinitions {

    static final Logger log = Logger.getLogger(StepDefinitions.class);
    URL url ;
    HttpURLConnection conn;

    @Given("^I have an API Service (.+)$")
    public void setupAPIConnection(String serviceName) throws Exception{
        url = new URL("https://supervillain.herokuapp.com/v1/" + serviceName);
        log.info("Request URL: " + url.toString());
        conn =  (HttpURLConnection) url.openConnection();
    }


    @When("^I retrieve user api$")
    public void retrieveUsers() throws Exception {
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();
    }

    @And("^all the users registered in the game are returned$")
    public void checkAllUsersReturned() throws Exception {
        String response = new String();
        Scanner sc = new Scanner(url.openStream());
        while(sc.hasNext()) {
            response+=sc.nextLine();
        }
        log.info("\n\n JSON API Response:\n " + response);
        sc.close();
    }


    @When("^I add a new user with name (.*)$")
    public void addNewUser(String username) throws Exception {
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "*/*");
        conn.setDoOutput(true);
        conn.connect();
        JSONObject requestBody = new JSONObject();
        int randomNo = (int)(Math.random()*9000) +1000;
        requestBody.put("username", username.replace("<RandomNumber>", String.valueOf(randomNo)));
        requestBody.put("score",0);
        log.info("Request Body: " + requestBody.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(requestBody.toString());
        bw.close();
    }

    @And("^new user is added$")
    public void checkNewUserAdded() throws Exception {
            String response = new String();
            Scanner sc = new Scanner(conn.getInputStream());
            while (sc.hasNext()) {
                response += sc.nextLine();
            }
            log.info("\n\n JSON API Response:\n " + response);
            sc.close();
            Assert.assertTrue(response.contains("User added."));
    }

    @When("^I update an existing user (.*) with score (\\d*)$")
    public void updateUser(String username, int score) throws Exception {
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "*/*");
        conn.setDoOutput(true);
        conn.connect();
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("score",score);
        log.info("Request Body: " + requestBody.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(requestBody.toString());
        bw.close();
    }

    @And("^user is updated$")
    public void checkUserUpdated() throws Exception {
        int responseCode = conn.getResponseCode();
        Assert.assertTrue("responseCode " + responseCode + " is not equal to 204",responseCode==204);
        String response = new String();
        Scanner sc = new Scanner(url.openStream());
        while(sc.hasNext()) {
            response+=sc.nextLine();
        }
        log.info("\n\n JSON API Response:\n " + response);
        sc.close();
    }

    @When("^I update an existing user (.*) without score$")
    public void updateUserWithoutScore(String username) throws Exception {
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "*/*");
        conn.setDoOutput(true);
        conn.connect();
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        log.info("Request Body: " + requestBody.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(requestBody.toString());
        bw.close();
    }

    @When("^add a new user with name(.*) without score$")
    public void addNewUserWithoutScore(String username) throws Exception {
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "*/*");
        conn.setDoOutput(true);
        conn.connect();
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        log.info("Request Body: " + requestBody.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(requestBody.toString());
        bw.close();
    }


    @Then("^(\\d+) response code is returned$")
    public void checkResponseCode(int responseCode) throws Exception{
        Assert.assertTrue("Expected -  " + responseCode + "  Actual - " +conn.getResponseCode(),responseCode==conn.getResponseCode());
    }


}
