package egovframework.example.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exception")
public class ExceptionController {
	@RequestMapping("/403.do") public String forbidden() { return "cmmn/403"; }
}
