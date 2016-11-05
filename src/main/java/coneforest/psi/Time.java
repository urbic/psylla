package coneforest.psi;
import coneforest.psi.core.*;

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
	public static PsiInteger psiTime()
	{
		return new PsiInteger(System.currentTimeMillis());
	}

	public static PsiDict psiCalendar(final PsiInteger oTime)
	{
		final java.util.GregorianCalendar calendar=new java.util.GregorianCalendar();
		calendar.setTimeInMillis(oTime.longValue());

		final PsiDict oCalendar=new PsiDict();
		for(int i=0; i<fieldNames.length; i++)
			oCalendar.put(fieldNames[i], PsiInteger.valueOf(calendar.get(i)));

		// oCalendar.put("leapyear", PsiBoolean.valueOf(calendar.isLeapYear(calendar.get(java.util.Calendar.YEAR))));

		return oCalendar;
	}

	public static PsiInteger psiCalendarTime(final PsiDictlike oCalendar)
		throws PsiException
	{
		final java.util.GregorianCalendar calendar=new java.util.GregorianCalendar();
		for(int i=0; i<fieldNames.length; i++)
			if(oCalendar.known(fieldNames[i]))
				calendar.set(i, ((PsiInteger)oCalendar.get(fieldNames[i])).intValue());

		return PsiInteger.valueOf(calendar.getTimeInMillis());
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
