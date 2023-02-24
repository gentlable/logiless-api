package logiless.web.controller.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 例外管理
 * 
 * @author nsh14789
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("error", ex.getMessage());
		return modelAndView;
	}

}
