package classes;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.print.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class Billprint implements Printable {
	hhelper hh = new hhelper();
	private int numberOfPages = 0; // numberOfPages -- How many pages we will be printing.
	private int headerLines = 0; // headerLines -- How many lines are in the header.
	private int footerLines = 0; // footerLines -- How many lines are in the footer.
	private int bodyLines = 0; // bodyLines -- How many lines we've added to the body of the report.
	private boolean useDefaultFooter = true;	
	private int fontSize = 10; // fontSize -- The size of the font to use, in points. The default is 10pt.
	private Vector fheader = new Vector();
	private Vector header = new Vector(); // header -- The vector containing the header text.
											// Each item in the vector is a line on the header.
	private Vector body = new Vector(); // body -- The vector containing the body text.
	private Vector footer = new Vector(); // footer -- The vector containing the footer.

	public Billprint() {
	
	}

	public void printReport() {
		headerLines = header.size();
		bodyLines = body.size();
		if (useDefaultFooter == true) {
			footerLines = 1;
		} else {
			footerLines = footer.size();
		}
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setJobName("Invoice");
		PageFormat landscape = printerJob.defaultPage();
		landscape.setOrientation(PageFormat.PORTRAIT);
		printerJob.setPrintable(Billprint.this, landscape);
		printerJob.printDialog();
		try {
			printerJob.print();
		} catch (Exception PrintException) {
		}
	}

	public void addFHeaderLine(String fheaderLine) {
		fheader.addElement(fheaderLine);
	}

	public void addHeaderLine(String headerLine) {
		header.addElement(headerLine);
	}

	public void addBodyLine(String bodyLine) {
		body.addElement(bodyLine);
	}

	public void addFooterLine(String footerLine) {
		footer.addElement(footerLine);
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);	
		Font myFont = new Font("Monospaced", Font.BOLD, fontSize);
		g2.setFont(myFont);
		int fontHeight = g2.getFontMetrics().getHeight();
		int fontDescent = g2.getFontMetrics().getDescent();
		double pageHeight = pageFormat.getHeight();
		double pageWidth = pageFormat.getWidth();		
		int lineHeight = fontHeight + fontDescent;	
		int linesPerPage = (int) ((pageHeight - 72 - 72) / lineHeight);
		int bodyLinesPerPage = linesPerPage - headerLines - footerLines;
		int numberOfPages = (int) Math.ceil((double) bodyLines / bodyLinesPerPage);
		int pwidth = (int) pageWidth;
		String curDate = hh.currentDate();
		if (pageIndex >= numberOfPages) {
			return Printable.NO_SUCH_PAGE;
		}	
		
		int currentY = 72 + lineHeight;
		int currentX = 72;

		// First page fheader
		if (pageIndex == 0) {
			for (int i = 0; i < fheader.size(); i++) {
				g2.drawString((String) fheader.get(i), currentX, currentY);				
				currentY = currentY + lineHeight;
			}
		}
		// Draw the header
		g2.drawLine(currentX, currentY - 10, pwidth, currentY - 10);
		for (int i = 0; i < header.size(); i++) {
			g2.drawString((String) header.get(i), currentX, currentY);
			currentY = currentY + lineHeight;
		}
		g2.drawLine(currentX, currentY-10, pwidth, currentY-10);
		currentY = currentY + lineHeight/5;
		// Draw the body
		for (int i = bodyLinesPerPage * pageIndex; i < (bodyLinesPerPage * (pageIndex + 1)); i++) {
			if (i < body.size()) {
				g2.drawString((String) body.get(i), currentX, currentY);
				if (i == body.size() - 2) {
					g2.drawLine(currentX, currentY + 3, pwidth, currentY + 5);
				}
			}
			currentY = currentY + lineHeight;
		}

		// Draw footer string
		g2.drawString(curDate + " | page " + (pageIndex + 1) + " of " + numberOfPages, pwidth / 2 - 50, currentY);
		g2.drawLine(currentX, currentY - 12, pwidth, currentY - 12);
		return Printable.PAGE_EXISTS;
	}	

	public static void main(String[] args) {
		new Billprint();
	}
}
