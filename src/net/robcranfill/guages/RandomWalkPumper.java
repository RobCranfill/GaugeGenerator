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

import java.util.Random;

// From http://harmoniccode.blogspot.com/ - see GaugeGenerator docs for more info.
//
import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.AbstractLinear;

/**
 * A Pumper that does a random walk between min and max, jumping at most by maxDelta.
 * @author robcranfill@robcranfill.net
 */
public class RandomWalkPumper extends Pumper
{
private double maxDelta_;

/**
 * Will start somewhere random between min and max, and walk around betwixt, bouncing off the ends.
 * @param guage
 * @param min
 * @param max
 * @param updateMillis
 */
public RandomWalkPumper(AbstractGauge guage, double min, double max, long updateMillis, double maxDelta)
	{
	super(guage, min, max, updateMillis);
	
	maxDelta_ = maxDelta;

	// Start this object in a new thread.
	//
	Thread t = new Thread(this);
	t.start();
	}


@Override
public void run()
	{
	Random r = new Random();
	double val = min_ + r.nextDouble()*(max_ - min_);
	while (true)
		{
		if (guage_ == null)
			{
			System.out.printf("RandomWalkPumper: %f \n", val);
			}
		else
			{
			// If we're updating slowly, AbstractLinear has a nice animated setValue() to use.
			//
			if (updateMillis_ > 1000 && (guage_ instanceof AbstractLinear))
				{
				((AbstractLinear)guage_).setValueAnimated(val);
				}
			else
				{
				guage_.setValue(val);
				}
			}

		try
			{
			Thread.sleep(updateMillis_);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}

		val += maxDelta_ * r.nextDouble() * (r.nextBoolean()?1:-1);
		if (val < min_)
			{
			val = min_;
			}
		if (val > max_)
			{
			val = max_;
			}

		}
	}


/**
 * For testing.
 * @param args
 */
public static void main(String[] args)
	{
	new RandomWalkPumper(null, 10, 20, 5000, 2);
	}
	
}
