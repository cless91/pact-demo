package com.example.demo;

import io.vavr.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AlligatorRestInterface {
  private final String produces = "application/json;charset=utf-8";
  private AlligatorService alligatorService;

  public AlligatorRestInterface(AlligatorService alligatorService) {
    this.alligatorService = alligatorService;
  }

  @GetMapping(path = "/alligators/{name}",
      produces = produces)
  public AlligatorResponse mary(@PathVariable String name) {
    System.out.println(name);
    return Try.of(() -> alligatorService.process(name))
//        .getOrElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Argh!!!"))
        .getOrElseThrow(AlligatorServiceException::new)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler({AlligatorServiceException.class})
  public ResponseEntity<AlligatorServiceException> name(HandlerMethod handlerMethod){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.valueOf(produces))
        .body(new AlligatorServiceException());
  }

  public void setAlligatorService(AlligatorService alligatorService) {
    this.alligatorService = alligatorService;
  }
}
