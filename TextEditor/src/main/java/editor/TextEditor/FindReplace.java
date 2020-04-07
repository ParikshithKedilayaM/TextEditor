package editor.TextEditor;

import javax.swing.text.Highlighter.HighlightPainter;

public class FindReplace {
	public void searchAll(String string, HighlightPainter painter) {
		int offset = Model.getInstance().getText().indexOf(string);
		int length = string.length();

		while (offset != -1) {
			search(string, painter, offset, length);
			offset = Model.getInstance().getText().indexOf(string, offset + 1);
		}
	}

	public void searchOne(String string, HighlightPainter painter) {
		int offset = Model.getInstance().getText().indexOf(string);
		int length = string.length();
		search(string, painter, offset, length);
	}

	private void search(String string, HighlightPainter painter, int offset, int length) {
		try {
			Model.getInstance().getTextArea().getHighlighter().addHighlight(offset, offset + length, painter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
