package com.virtualspace.twodimcode.web;

import java.awt.Color;
import net.glxn.qrgen.image.ImageType;

public class TwoDimCodeOutConfig 
{
	private int imgSize = 120;
	private int margin = 0;
	private ImageType imageType = ImageType.PNG;
	private Color backgroundColor = Color.WHITE;
	private Color color = Color.BLACK;
	
	public TwoDimCodeOutConfig(){};
	public TwoDimCodeOutConfig(int size)
	{
		this.setImgSize(size);
	}
	public ImageType getImageType() {
		return imageType;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	public int getImgSize() {
		return imgSize;
	}
	public void setImgSize(int imgSize) {
		this.imgSize = imgSize;
	}
	public int getMargin() {
		return margin;
	}
	public void setMargin(int margin) {
		this.margin = margin;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
