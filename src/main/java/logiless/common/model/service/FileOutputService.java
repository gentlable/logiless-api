package logiless.common.model.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import logiless.config.properties.FileOutputProperties;

/**
 * ファイル出力サービス
 * 
 * @author nsh14789
 *
 */
@Service
public class FileOutputService {

	private final FileOutputProperties fileOutputProperties;

	@Autowired
	FileOutputService(FileOutputProperties fileOutputProperties) {
		this.fileOutputProperties = fileOutputProperties;
	}

	/**
	 * MS932(SHIFT-JIS)形式でファイルをディレクトリに出力する
	 * 
	 * @param filename
	 * @param input
	 */
	public void output(String filename, String input) {

		FileOutputStream fo = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		String outputDir = fileOutputProperties.getDirectory();

		try {

			fo = new FileOutputStream(outputDir + filename, true);
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