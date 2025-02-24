package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.GregorianCalendar;
import java.util.List;

/**
*	An utility class providing time-related methods.
*/
public interface PsyTime
{
	/**
	*	Context action of the {@code time} operator.
	*/
	@OperatorType("time")
	public static final ContextAction PSY_TIME
		=ContextAction.ofSupplier(PsyTime::psyTime);

	/**
	*	Context action of the {@code calendar} operator.
	*/
	@OperatorType("calendar")
	public static final ContextAction PSY_CALENDAR
		=ContextAction.ofFunction(PsyTime::psyCalendar);

	/**
	*	An array consisting of the name of the calendar fields.
	*/
	public static final List<String> CALENDAR_FIELDS=List.of(
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
			"dstoffset");

	/**
	*	{@return an {@code integer} object representing the current time (in
	*	milliseconds since 1970.01.01 00:00:00 GMT)}
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
		for(int i=0; i<CALENDAR_FIELDS.size(); i++)
			oCalendar.put(CALENDAR_FIELDS.get(i), PsyInteger.of(calendar.get(i)));

		// oCalendar.put("leapyear", PsyBoolean.of(calendar.isLeapYear(calendar.get(Calendar.YEAR))));

		return oCalendar;
	}

	public static PsyInteger psyCalendarTime(final PsyFormalDict<PsyObject> oCalendar)
		throws PsyUndefinedException
	{
		final var calendar=new GregorianCalendar();
		for(int i=0; i<CALENDAR_FIELDS.size(); i++)
			if(oCalendar.known(CALENDAR_FIELDS.get(i)))
				calendar.set(i, ((PsyInteger)oCalendar.get(CALENDAR_FIELDS.get(i))).intValue());

		return PsyInteger.of(calendar.getTimeInMillis());
	}
}
