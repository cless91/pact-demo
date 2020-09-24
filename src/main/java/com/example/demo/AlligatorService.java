package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AlligatorService {
  private Set<String> alligators = Set.of("Mary");

  public Optional<AlligatorResponse> process(String alligatorName) {
    return Optional.of(new AlligatorResponse(alligatorName))
        .filter(alligatorResponse -> alligators.contains(alligatorName));
  }
}
