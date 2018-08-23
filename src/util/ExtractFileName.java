package util;

import javax.servlet.http.Part;

public class ExtractFileName {
	public String extractFileName(Part part) {
		String[] splitedHeader = part.getHeader("Content-Disposition").split(";");

		String fileName = null;
		for (String item : splitedHeader) {
			if (item.trim().startsWith("filename")) {
				fileName = item.substring(item.indexOf('"')).replaceAll("\"", "");
			}
		}
		return fileName;
	}
}
