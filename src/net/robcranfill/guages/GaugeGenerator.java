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

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.util.Scanner;

// From http://harmoniccode.blogspot.com/ - see GaugeGenerator docs for more info.
//
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.tools.ColorDef;


/**
 * Reads a simple config file.
 * 
 * @author robcranfill@robcranfill.net
 */
public class GaugeGenerator
{

/**
 * @param args
 *		<ul>
 *		<li>args[0] (optional)	Name of config file; default is "config.text".
 *		<li>args[1] (optional)	Save pattern, like "images/Pandigital-%d.jpg" (default); <b>must</b> contain one "%d" for replacement.
 *		</ul>
 */
public static void main(String[] args)
	{
	String configName = "config.text";
	if (args.length > 0)
		{
		configName = args[0];
		}

	String savePattern = "images/Pandigital-%d.jpg";
	if (args.length > 1)
		{
		savePattern = args[1];
		}

	GaugeGenerator.go(configName, savePattern);
	}


/**
 * 
 * @param configName
 * @param savePattern
 */
private static void go(String configName, String savePattern) 
	{
	final String cfgName = configName;
	final String sPattern = savePattern;

	EventQueue.invokeLater(new Runnable()
		{
    	public void run()
    		{
    		try
    			{
    			GaugeFrame gf = new GaugeFrame();
    
    			Scanner scanner = new Scanner(new FileInputStream(cfgName));
    			
    			// TODO: first line: base name?
    
    			int i = 0; // for 'sperimentin'
    
    			try
    				{
    				while (scanner.hasNextLine())
    					{
    					String line = scanner.nextLine();
    					if (line != null && !line.isEmpty() && !line.startsWith("#"))
    						{
    						LinearBargraph lbg = gf.addBarGraphFromString(line);
    						lbg.setLcdVisible(false);
    						i++;
    						if (i == 2)
    							{
    							lbg.setBarGraphColor(ColorDef.GREEN);
    							}
    						else
    						if (i == 3)
    							{
    							lbg.setBarGraphColor(ColorDef.BLUE);
    							}
    							
    						}
    					}
    				}
    			finally
    				{
    				scanner.close();
    				}
    
    			gf.fixup();
    
    			gf.setLocation(400, 300);
    			gf.setVisible(true);
    			
    			new SnapShooter(gf.getContentPane(), 500, sPattern);
    			}
    		catch (Exception e)
    			{
    			System.err.printf("Error: %s \n", e.getMessage());
//    			e.printStackTrace();
    			}
    		}
		});
	}
	
}
