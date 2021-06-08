package com.covidgame.api;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources"},
        tags ={"@RegressionTests"},
        strict=true,
        plugin={"html:target/cucumber-report", "pretty"},
        glue={"com.covidgame.api.StepDefinitions"})
public class AcceptanceTest {
}
