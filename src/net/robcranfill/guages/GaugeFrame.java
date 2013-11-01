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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

// From http://harmoniccode.blogspot.com/ - see GaugeGenerator docs for more info.
//
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.tools.ColorDef;


/**
 * @author robcranfill@robcranfill.net
 */
@SuppressWarnings("serial")
public class GaugeFrame extends JFrame
{
private JPanel 		contentPane_;
private Rectangle	frameBounds_;


/**
 * A JFrame containing some guages (which you have to add using addBarGraph() or other TBD methods.
 */
public GaugeFrame()
	{
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// We will attempt to automagically set the bounds of the window frame
	frameBounds_ = new Rectangle(0,0,0,0);

	contentPane_ = new JPanel();
	contentPane_.setBackground(Color.GRAY);
//	contentPane_.setBorder(new EmptyBorder(5, 5, 5, 5));	// NOTE!

	// No layout manager
	contentPane_.setLayout(null);
	
	this.setContentPane(contentPane_);
	}


/**
 * 
 * @return The LinearBargraph in case the caller wants to much with it more.
 */
public LinearBargraph addBarGraph(String title, Rectangle bounds, double min, double max, long updateMillis, double maxDelta)
	{
	LinearBargraph lbg = new LinearBargraph();
	lbg.setTitle(title);
	lbg.setPreferredSize(new Dimension(bounds.width, bounds.height));
	lbg.setBounds(bounds);
	
	lbg.setValueColor(ColorDef.YELLOW);
	lbg.setUserLedVisible(true);
	lbg.setUserLedOn(true);
	lbg.setCustomLedColor(Color.GREEN);
	
	
	contentPane_.add(lbg);

	// Attempt to automagically set the bounds of the window frame
	//
	frameBounds_.add(bounds);
	contentPane_.setBounds(frameBounds_);
	
//	
//	frameBounds_.height += 38;	// for the titlebar XXX ???
//
//	frameBounds_.width  += 6;	// for the window frame XXX ???
////	frameBounds_.width  += contentPane_.getBorder().getBorderInsets(this).left 
////						 + contentPane_.getBorder().getBorderInsets(this).right;



	this.setBounds(frameBounds_);

	// some things i'd like to default the other way:
	lbg.getModel().setUnitVisible(false);
	
	new RandomWalkPumper(lbg, min, max, updateMillis, maxDelta);
	
	return lbg;
	}
	

/**
 * Dumb text-driven version; soon to parse XML?
 * 
 * String is 
 * 		"Title, x, y, w, h, min, max, update, walk"
 * like
 *   	"Squakitude, 0, 0, 320, 117, 0, 100, 500, 5"
 * 
 */
public LinearBargraph addBarGraphFromString(String configString)
	{
	String[] tokens = configString.split("[,]");
	if (tokens.length != 10)
		{
		System.err.printf("Bad config line: %s \n", configString);
		return null;
		}

	int i=0;
	
	String type = tokens[i++].trim();
	String title = tokens[i++].trim();
	
	int x = Integer.parseInt(tokens[i++].trim());
	int y = Integer.parseInt(tokens[i++].trim());
	int w = Integer.parseInt(tokens[i++].trim());
	int h = Integer.parseInt(tokens[i++].trim());
	Rectangle bounds = new Rectangle(x, y, w, h);
	
	double min = Double.parseDouble(tokens[i++].trim());
	double max = Double.parseDouble(tokens[i++].trim());
	long updateMillis = Long.parseLong(tokens[i++].trim());
	double maxDelta = Double.parseDouble(tokens[i++].trim());
	
	if (type.equals("LinearBargraph"))
		{
		System.out.printf("t='%s', b=%s, min=%f, max=%f, up=%d, md=%f \n", title, bounds, min, max, updateMillis, maxDelta);
		return addBarGraph(title, bounds, min, max, updateMillis, maxDelta);
		}
	
	System.err.printf("Unknown gauge type: '%s' \n", type);
	return null;
	}


/**
 * You gotta do this once, after adding all the stuff. XXX
 */
public void fixup()
	{
	
	frameBounds_.height += 38;	// for the titlebar XXX ???

	frameBounds_.width  += 16;	// for the window frame XXX ???

//	frameBounds_.width  += contentPane_.getBorder().getBorderInsets(this).left 
//						 + contentPane_.getBorder().getBorderInsets(this).right;

	this.setBounds(frameBounds_);
	}
	
 
}
