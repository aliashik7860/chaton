package com.appdevsolutions.chat.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CaptchaGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaGenerator.class);
	
	private static final int WIDTH=160;   //image width
    private static final int HEIGHT=40;   //image height
    private static final String FONT_NAME="Lucida Handwriting";
    private static final int FONT_SIZE=20;
    private String strCaptcha;
    private final int locY=25;
    private final Random random;
    private final Color clrBackGround;
    private final Font fntCaptchaString;
    private final BufferedImage imgCaptcha;
    private final Graphics graphicsCaptcha;
    private ByteArrayOutputStream byteArrayOutputStream;
    public CaptchaGenerator(){
    	random=new Random();
    	imgCaptcha= new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    	graphicsCaptcha=imgCaptcha.createGraphics();
    	clrBackGround=new Color(204,204,204);
    	fntCaptchaString=new Font(FONT_NAME,Font.BOLD,FONT_SIZE);
    }
    
    public byte[] getCaptchaImageArray(){
    	byteArrayOutputStream=new ByteArrayOutputStream();
    	strCaptcha=(Integer.toHexString(random.nextInt())).substring(0,5);
    	graphicsCaptcha.setColor(clrBackGround);  //setting background color
        graphicsCaptcha.fillRect(0,0,WIDTH,HEIGHT);  //setting width and height
        graphicsCaptcha.setFont(fntCaptchaString);  // setting font
        int locX=40;
        for(int i=0;i<strCaptcha.length();i++){
			final int R=Byte.toUnsignedInt((byte)random.nextInt(255));
			final int G=Byte.toUnsignedInt((byte)random.nextInt(255));
			final int B=Byte.toUnsignedInt((byte)random.nextInt(255));
			final Color clrForeGround = new Color(R,G,B);  //setting foreground color
			graphicsCaptcha.setColor(clrForeGround);
			graphicsCaptcha.drawString(String.valueOf(strCaptcha.charAt(i)),locX,locY);
			locX=locX+18;
		}
        graphicsCaptcha.setFont(fntCaptchaString);  // setting font
        graphicsCaptcha.setColor(clrBackGround);  //setting background color
        try{
        	ImageIO.write(imgCaptcha, "jpeg", byteArrayOutputStream);
        }catch(IOException ioException){
        	logger.error("could not write buffered image to byte array", ioException);
        }
        imgCaptcha.flush();
        logger.info("return image string : "+strCaptcha);
        
        final byte[] array=byteArrayOutputStream.toByteArray();
    	return array;
    }
    public String getCaptchaString() {
    	return strCaptcha;
    }
}
