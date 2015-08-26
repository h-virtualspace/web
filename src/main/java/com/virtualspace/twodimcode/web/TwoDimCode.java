package com.virtualspace.twodimcode.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class TwoDimCode 
{
	public final static String TEXT = "text";
	public final static String SIZE = "size";
	public final static String IMG_TYPE = "imgtype";
	public final static String BACKGROUND_COLOR = "bgcolor";
	public final static String COLOR = "color";
	
	private String text;
	private TwoDimCodeOutConfig config;
	
	public TwoDimCode()
	{
		this("http://www.agohere.com");
	}
	public TwoDimCode(String text)
	{
		this(text,new TwoDimCodeOutConfig());
	}
	public TwoDimCode(String text,TwoDimCodeOutConfig config)
	{
		this.text = text;
		this.config = config;
	}
	public void createQrCode(OutputStream outputStream){  
	    try{  
	        // Create the ByteMatrix for the QR-Code that encodes the given String.  
	        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<EncodeHintType, Object>();  
	        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	        hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
	        hintMap.put(EncodeHintType.MARGIN, config.getMargin());
	        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();  
	        BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, config.getImgSize(), config.getImgSize(), hintMap);  
	          
            // Make the BufferedImage that are to hold the QRCode  
            int matrixWidth = byteMatrix.getWidth();  
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);  
            image.createGraphics();  
            Graphics2D graphics = (Graphics2D) image.getGraphics();  
            graphics.setColor(config.getBackgroundColor());  
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);  
  
            // Paint and save the image using the ByteMatrix  
            graphics.setColor(config.getColor());  
            for (int i = 0; i < matrixWidth; i++){  
                for (int j = 0; j < matrixWidth; j++){  
                    if (byteMatrix.get(i, j)){  
                        graphics.fillRect(i, j, 1, 1);  
                    }  
                }  
            }  
  
            ImageIO.write(image, config.getImageType().toString().toLowerCase(), outputStream);  
        }catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    } 

}