package software.developer.bhushan.utils;

import javax.swing.*;
import javax.swing.text.*;

public class FieldUtils {

    public static void makeFieldPinField(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new PINDocumentFilter(4));
    }

    // Custom DocumentFilter for 4-digit numeric input
    static class PINDocumentFilter extends DocumentFilter {
        private int maxLength;

        public PINDocumentFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;

            if (newText.matches("\\d{0," + maxLength + "}")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

            if (newText.matches("\\d{0," + maxLength + "}")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public static void makeFieldFloatField(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isValidFloat(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isValidFloat(fb.getDocument().getText(0, fb.getDocument().getLength()) + text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidFloat(String text) {
                return text.matches("^\\d*\\.?\\d*$");
            }
        });
    }


}
