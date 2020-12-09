package com.example.consumer1;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "testProvider")
public class Consumer1ContractTest {
  @Pact(provider = "testProvider", consumer = "testConsumer")
  public RequestResponsePact createPact(PactDslWithProvider builder) {
    return builder
        .given("test state")
        .uponReceiving("example test interaction")
          .path("/articles")
          .method("GET")
        .willRespondWith()
          .status(200)
          .body("{\"responsetest\": true}")
        .toPact();
  }

  @Test
  void test(MockServer mockServer) throws IOException {
    HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/articles").execute().returnResponse();
    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
  }
}
