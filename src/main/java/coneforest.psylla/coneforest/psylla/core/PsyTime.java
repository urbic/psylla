package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.GregorianCalendar;

/**
*	An utility class providing time-related methods.
*/
public class PsyTime
{
	private PsyTime()
	{
	}

	/**
	*	Returns an {@code integer} object representing the current time (in
	*	milliseconds since 1970.01.01 00:00:00 GMT).
	*
	*	@return an {@code integer} representing the current time (in
	*	milliseconds since 1970.01.01 00:00:00 GMT).
	*/
	public static PsyInteger psyTime()
	{
		return PsyInteger.of(System.currentTimeMillis());
	}

	public static PsyDict psyCalendar(final PsyInteger oTime)
	{
		final var calendar=new GregorianCalendar();
		calendar.setTimeInMillis(oTime.longValue());

		final var oCalendar=new PsyDict();
		for(int i=0; i<fieldNames.length; i++)
			oCalendar.put(fieldNames[i], PsyInteger.of(calendar.get(i)));

		// oCalendar.put("leapyear", PsyBoolean.of(calendar.isLeapYear(calendar.get(Calendar.YEAR))));

		return oCalendar;
	}

	public static PsyInteger psyCalendarTime(final PsyFormalDict oCalendar)
		throws PsyErrorException
	{
		final var calendar=new GregorianCalendar();
		for(int i=0; i<fieldNames.length; i++)
			if(oCalendar.known(fieldNames[i]))
				calendar.set(i, ((PsyInteger)oCalendar.get(fieldNames[i])).intValue());

		return PsyInteger.of(calendar.getTimeInMillis());
	}

	private static final String[] fieldNames
		={
			"era",
			"year",
			"month",
			"weekofyear",
			"weekofmonth",
			"dayofmonth",
			"dayofyear",
			"dayofweek",
			"dayofweekinmonth",
			"ampm",
			"hour",
			"hourofday",
			"minute",
			"second",
			"millisecond",
			"zoneoffset",
			"dstoffset"
		};

	/**
	*	Context action of the {@code time} operator.
	*/
	@OperatorType("time")
	public static final ContextAction PSY_TIME
		=ContextAction.ofSupplier(PsyTime::psyTime);
}
