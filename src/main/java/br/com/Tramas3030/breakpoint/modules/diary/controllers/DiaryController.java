package br.com.Tramas3030.breakpoint.modules.diary.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/diary")
public class DiaryController {

  @PostMapping("/")
  public void create() {

  }

}
