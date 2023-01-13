package logiless.common.model.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

@Service
public class FileOutputService {
	
	private static final String OUTPUT_DIR = "output";
	
	public void output(String filename, String input) {
		
		FileOutputStream fo = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		
		try {
			
			fo = new FileOutputStream(OUTPUT_DIR + "/" + filename + ".csv");
			osw = new OutputStreamWriter(fo, "SHIFT_JIS");
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