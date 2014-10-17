/**
 * 
 */
package com.mg.studio.alice.myframework.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

/**
 * @author Dk Thach
 * 
 */
public class SpriteAtlasParXmlUtil {

	public static SpriteInfo[] readXMLdom(Context context, String pathName,
			int nFrame, float scale) {
		SpriteInfo[] liSpriteInfo = new SpriteInfo[nFrame];
		AssetManager assetManager = context.getAssets();

		InputStream input = null;
		try {
			input = assetManager.open(pathName);
		} catch (IOException e) {
			e.printStackTrace();

		}
		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(input);
		} catch (ParserConfigurationException e) {
			Log.d("XML parse error: ", "Error: " + e.getMessage());
		} catch (SAXException e) {
			Log.d("Wrong XML file structure: ", "Error: " + e.getMessage());
		} catch (IOException e) {
			Log.d("I/O exeption: ", "Error: " + e.getMessage());
		}
		NodeList sprite = doc.getElementsByTagName("sprite");
		float left, top, right, bottom;
		for (int k = 0; k < sprite.getLength(); k++) {
			liSpriteInfo[k] = new SpriteInfo();
			Element h1 = (Element) sprite.item(k);
			liSpriteInfo[k].name = h1.getAttribute("n");
			left = Integer.parseInt(h1.getAttribute("x"));
			top = Integer.parseInt(h1.getAttribute("y"));
			right = left + Integer.parseInt(h1.getAttribute("w"));
			bottom = top + Integer.parseInt(h1.getAttribute("h"));
			liSpriteInfo[k].rectcutframe.set(left * scale, top * scale,
					right * scale, bottom * scale);

		}
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liSpriteInfo;

	}

	/**
	 * chuyển data Sprite XML qua .dat
	 */
	public static void convertXML2Dat(String pathName) {

		String externalStoragePath = Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
		DataOutputStream dos;

		InputStream input = null;
		try {
			input = new FileInputStream(externalStoragePath + pathName
					+ ".xml");
		} catch (IOException e) {
			e.printStackTrace();

		}
		Document doc = null;
		try {
			dos = new DataOutputStream(new FileOutputStream(
					externalStoragePath + pathName + ".dat"));

			DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(input);
			} catch (ParserConfigurationException e) {
				Log.d("XML parse error: ", "Error: " + e.getMessage());
			} catch (SAXException e) {
				Log.d("Wrong XML file structure: ",
						"Error: " + e.getMessage());
			} catch (IOException e) {
				Log.d("I/O exeption: ", "Error: " + e.getMessage());
			}
			NodeList sprite = doc.getElementsByTagName("sprite");
			for (int k = 0; k < sprite.getLength(); k++) {

				Element h1 = (Element) sprite.item(k);
				dos.writeInt(Integer.parseInt(h1.getAttribute("n")));
				dos.writeInt(Integer.parseInt(h1.getAttribute("x")));
				dos.writeInt(Integer.parseInt(h1.getAttribute("y")));
				dos.writeInt(Integer.parseInt(h1.getAttribute("w")));
				dos.writeInt(Integer.parseInt(h1.getAttribute("h")));
			}

			dos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
