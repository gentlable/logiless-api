package logiless.web.com.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class CsvExportService {

	/**
	 * オブジェクトをoutputストリームに
	 * @param file
	 * @param obj
	 * @throws IOException
	 */
	public void exportFromObject(File file, Object obj) throws IOException {
		FileOutputStream ostream = null;

		try {
			ostream = new FileOutputStream(file);
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			p.writeObject(obj);
			p.flush();
			ostream.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FILE NOT FOUND.\n" + e.getMessage(), e);
		} catch (IOException e) {
			e.fillInStackTrace();
			throw e;
		} finally {
			if (ostream != null) {
				try {
					ostream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @param file
	 * @param psText
	 * @param encode
	 * @throws IOException
	 */
	public void saveAs(File file, String psText, String encode) throws IOException {

		OutputStreamWriter os = null;
		BufferedWriter out = null;

		try {
			os = new OutputStreamWriter(new FileOutputStream(file), encode);
			out = new BufferedWriter(os);
			out.write(psText);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
			if (os != null) {
				os.close();
			}
			os = null;
		}
	}
}
