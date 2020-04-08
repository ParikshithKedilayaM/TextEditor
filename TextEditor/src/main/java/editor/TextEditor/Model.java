package editor.TextEditor;

import javax.swing.JTextArea;

public class Model {
	
	private static Model instance = null;
	
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	private String fileName;
	private int latestTab = 0;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public void setText(String text) {
		this.textArea.setText(text);
	}
	
	public String getText() {
		return this.textArea.getText();
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	public String getCurrentTabName() {
		return currentTabName;
	}

	public void setCurrentTabName(String currentTabName) {
		this.currentTabName = currentTabName;
	}

	public int getLatestTab() {
		return latestTab;
	}

	public void setLatestTab(int latestTab) {
		this.latestTab = latestTab;
	}

	private JTextArea textArea;
	
	private String displayMessage;
	
	private String currentTabName;
}
