package logiless.common.model.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

/**
 * ファイル出力サービス
 * 
 * @author nsh14789
 *
 */
@Service
public class FileOutputService {

	private static final String OUTPUT_DIR = "output";

	/**
	 * MS932(SHIFT-JIS)形式でファイルを出力する
	 * 
	 * @param filename
	 * @param input
	 */
	public void output(String filename, String input) {

		FileOutputStream fo = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {

			fo = new FileOutputStream(OUTPUT_DIR + "/" + filename + ".csv");
			osw = new OutputStreamWriter(fo, "MS932");
			bw = new BufferedWriter(osw);

			bw.write(input);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}