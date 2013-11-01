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

// From http://harmoniccode.blogspot.com/ - see GaugeGenerator docs for more info.
// 
import eu.hansolo.steelseries.gauges.AbstractGauge;


/**
 * A superclass for a threaded object that can feed data to a gauge.
 * @author robcranfill@robcranfill.net
 */
abstract public class Pumper implements Runnable
{
protected AbstractGauge	guage_;
protected double		min_;
protected double		max_;
protected long			updateMillis_;

public Pumper(AbstractGauge guage, double min, double max, long updateMillis)
	{
	guage_ = guage;
	min_ = min;
	max_ = max;
	updateMillis_ = updateMillis;
	if (guage_ != null)
		{
		guage_.setMaxValue(max);	// FUNNY: they won't let you set these in the other order!
		guage_.setMinValue(min);
		}
	}
	
}
