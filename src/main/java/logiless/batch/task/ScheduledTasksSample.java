package logiless.batch.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * スケジューラサンプル
 * 
 * @author nsh14789
 *
 */
@Component
@Slf4j
public class ScheduledTasksSample {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//	@Scheduled(cron = "${scheduler.cron2}")
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}
}
