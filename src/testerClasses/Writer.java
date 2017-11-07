package testerClasses;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	String file_path;
	boolean append_value;

	// constructors
	public Writer(String file_path) {
		this.file_path = file_path;
	}

	public Writer(String file_path, boolean append) {
		this.file_path = file_path;
		this.append_value = append;
	}

	public void writeToFile(String toWrite) {
		FileWriter write;
		try {
			write = new FileWriter(this.file_path, this.append_value);
			PrintWriter print = new PrintWriter(write);
			print.printf("%s" + "%n", toWrite);
			print.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
