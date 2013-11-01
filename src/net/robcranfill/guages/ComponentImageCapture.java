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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;

import java.awt.image.BufferedImage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.*;

import javax.swing.border.TitledBorder;

import javax.imageio.ImageIO;

import java.io.File;

/**
 * Create a screenshot of a component.
 * @see http://stackoverflow.com/questions/5853879/swing-obtain-image-of-jframe
 * @author Andrew Thompson
 */
class ComponentImageCapture
{

static final String HELP = "Type Ctrl-0 to get a screenshot of the current GUI.\n"
		+ "The screenshot will be saved to the current " + "directory as 'screenshot.png'.";


public static BufferedImage getScreenShot(Component component)
	{

	BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
	// call the Component's paint method, using
	// the Graphics object of the image.
	component.paint(image.getGraphics());
	return image;
	}


/**
 * Just for testing/demo.
 * @param args
 */
public static void main(String[] args)
	{
	Runnable r = new Runnable()
		{
		public void run()
			{
			final JFrame f = new JFrame("Test ComponentImageCapture");
			JMenuItem screenshot = new JMenuItem("Screenshot");
			screenshot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
			screenshot.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
						{
						BufferedImage img = getScreenShot(f.getContentPane());
						JOptionPane.showMessageDialog(
								null,
								new JLabel(new ImageIcon(img.getScaledInstance(img.getWidth(null) / 2,
										   img.getHeight(null) / 2, Image.SCALE_SMOOTH))));
						try
							{
							// write the image as a PNG
							ImageIO.write(img, "png", new File("ComponentImageCapture.png"));
							}
						catch (Exception e)
							{
							e.printStackTrace();
							}
						}
				});
			JMenu menu = new JMenu("Other");
			menu.add(screenshot);
			JMenuBar mb = new JMenuBar();
			mb.add(menu);
			f.setJMenuBar(mb);

			JPanel p = new JPanel(new BorderLayout(5, 5));
			p.setBorder(new TitledBorder("ComponentImageCapture"));
			p.add(new JScrollPane(new JTree()), BorderLayout.WEST);
			p.add(new JScrollPane(new JTextArea(HELP, 10, 30)), BorderLayout.CENTER);

			f.setContentPane(p);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			}
		};
	SwingUtilities.invokeLater(r);
	}
}
