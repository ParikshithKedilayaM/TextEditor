package editor.TextEditor;

import java.io.File;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;


public class FileManager extends Observable {
	public void loadFile(String filePath) {
		Scanner scanner = null;
		try {
			String text = "";
			File file = new File(filePath);
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				text += scanner.nextLine();
				text += '\n';
			}
			
			Model.getInstance().setFileName(filePath);
			Model.getInstance().setCurrentTabName(file.getName());
			setChanged();
			notifyObservers("setTabName");
			Model.getInstance().setText(text);
		} catch (Exception i) {
			i.printStackTrace();
		} finally {
			if (scanner != null) {
				try {
					scanner.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void saveAsFile(String selectedFile) {
		if (saveFile(selectedFile)) {
			setMessage("Save Success");
		}
	}

	public boolean saveFile(String fileName) {
		PrintWriter printWriter = null;
		try {
			if (fileName != null) {
				String text = Model.getInstance().getText();
				printWriter = new PrintWriter(fileName);
				printWriter.println(text);
				printWriter.flush();
				printWriter.close();
				Model.getInstance().setFileName(fileName);
				return true;
			}
		} catch (Exception i) {
			setMessage("Failed to save!");
			i.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
		return false;
	}
	
	private void setMessage(String message) {
		Model.getInstance().setDisplayMessage(message);
		setChanged();
		notifyObservers("display");
	}
}
