package logiless.batch.task;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import logiless.common.model.dto.common.SessionComponent;
import logiless.common.model.service.LogilessApiService;
import logiless.common.model.service.OAuthService;
import lombok.extern.slf4j.Slf4j;

/**
 * APIスケジューラ<br>
 * 時間を変更できるようにするためいずれ廃止
 * 
 * @author nsh14789
 *
 */
@Component
@Slf4j
public class ScheduledTasksAPI {

	private final SessionComponent sessionComponent;
	private final MessageSource messageSource;
	private final LogilessApiService logilessApiService;
	private final OAuthService oauth2Service;

	@Autowired
	public ScheduledTasksAPI(SessionComponent sessionComponent, MessageSource messageSource,
			LogilessApiService logilessApiService, OAuthService oauth2Service) {
		this.sessionComponent = sessionComponent;
		this.messageSource = messageSource;
		this.logilessApiService = logilessApiService;
		this.oauth2Service = oauth2Service;
	}

	@Scheduled(cron = "${scheduler.cron}")
	public void getLogilessSykkaJisseki() {

		LocalDate syoriDt = LocalDate.now();

		boolean res = logilessApiService.getJuchu(syoriDt, "3901");

	}
}
