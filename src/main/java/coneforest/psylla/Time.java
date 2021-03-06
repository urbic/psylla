package coneforest.psylla;
import coneforest.psylla.core.*;

/**
*	An utility class providing time-related methods.
*/
public class Time
{
	private Time()
	{
	}

	/**
	*	Returns a Ψ-{@code integer} representing the current time (in
	*	milliseconds since 1970.01.01 00:00:00 GMT).
	*
	*	@return a Ψ-{@code integer} representing the current time (in
	*	milliseconds since 1970.01.01 00:00:00 GMT).
	*/
	public static PsyInteger psyTime()
	{
		return new PsyInteger(System.currentTimeMillis());
	}

	public static PsyDict psyCalendar(final PsyInteger oTime)
	{
		final java.util.GregorianCalendar calendar=new java.util.GregorianCalendar();
		calendar.setTimeInMillis(oTime.longValue());

		final PsyDict oCalendar=new PsyDict();
		for(int i=0; i<fieldNames.length; i++)
			oCalendar.put(fieldNames[i], PsyInteger.valueOf(calendar.get(i)));

		// oCalendar.put("leapyear", PsyBoolean.valueOf(calendar.isLeapYear(calendar.get(java.util.Calendar.YEAR))));

		return oCalendar;
	}

	public static PsyInteger psyCalendarTime(final PsyDictlike oCalendar)
		throws PsyException
	{
		final java.util.GregorianCalendar calendar=new java.util.GregorianCalendar();
		for(int i=0; i<fieldNames.length; i++)
			if(oCalendar.known(fieldNames[i]))
				calendar.set(i, ((PsyInteger)oCalendar.get(fieldNames[i])).intValue());

		return PsyInteger.valueOf(calendar.getTimeInMillis());
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
}
