package com.example.demo;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("Example API")
@PactBroker
class PactDemoApplicationTests {

  @Autowired
  private AlligatorRestInterface alligatorRestInterface;

  @TestTemplate
  @ExtendWith(PactVerificationSpringProvider.class)
  void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @State("there is an alligator named Mary")
  void present() {
  }

  @State("there is not an alligator named Mary")
  void absent() {
    AlligatorService mock = mock(AlligatorService.class);
    doReturn(Optional.empty()).when(mock).process(anyString());
    alligatorRestInterface.setAlligatorService(mock);
  }

  @State("an error occurs retrieving an alligator")
  void error() {
    AlligatorService mock = mock(AlligatorService.class);
    doThrow(new RuntimeException()).when(mock).process(anyString());
    alligatorRestInterface.setAlligatorService(mock);
  }
}
