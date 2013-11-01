/*
	Copyright (C) 2013  robcranfill@robcranfill.net
	
	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package net.robcranfill.guages;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * Threaded object that takes a screen shot every N milliseconds.
 * Outputs JPEG. 
 * 
 * TODO: Support other image formats.
 * TODO: Catch format errors in 'basename' string.
 * 
 * @author robcranfill@robcranfill.net
 */
public class SnapShooter implements Runnable
{
private Component	component_;
private long		delayMillis_;
private String		basename_;


/**
 * @param component		The component to take a pic of; can be a JFrame or anything else.
 * @param delayMillis	How many milliseconds to delay betwixt shots.
 * @param basename		Should be String.format-compatible with a "%d" in it, like "my_screen_shot-%d.jpeg"
 */
public SnapShooter(Component component, long delayMillis, String basename)
	{
	component_ = component;
	delayMillis_ = delayMillis;
	basename_ = basename;
	
	new Thread(this).start();
	}


@Override
public void run()
	{
	int snapNumber = 1;
	
	while (true)
		{
		try
			{
			Thread.sleep(delayMillis_);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		
    	BufferedImage img = ComponentImageCapture.getScreenShot(component_);
		try
			{
			ImageIO.write(img, "jpeg", new File(String.format(basename_, snapNumber++)));
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
			
		}
	}

}
