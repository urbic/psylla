package coneforest.psylla.extensions.romannumerals;

import coneforest.psylla.core.*;
import coneforest.psylla.runtime.*;
import java.util.regex.Pattern;

public class PsyRomanNumerals
	extends PsyModule
{
	@OperatorType("toroman")
	public static final ContextAction PSY_TOROMAN
		=ContextAction.<PsyInteger>ofFunction(PsyRomanNumerals::psyToRoman);

	@OperatorType("fromroman")
	public static final ContextAction PSY_FROMROMAN
		=ContextAction.<PsyTextual>ofFunction(PsyRomanNumerals::psyFromRoman);

	private static final Pattern ROMAN_PATTERN
		=Pattern.compile("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$");

	private static final String[][] CONVERSION_TABLE=new String[][]
		{
			{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
			{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
			{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
			{"", "M", "MM", "MMM"}
		};

	public PsyRomanNumerals()
	{
		//super("text.roman");

		importOperators(getClass());
	}

	private static PsyString psyToRoman(final PsyInteger oInteger)
		throws PsyRangeCheckException
	{
		long n=oInteger.longValue();
		if(n<0 || n>=4000)
			throw new PsyRangeCheckException();
		final var sb=new StringBuilder();
		for(int d=0; n>0; n/=10, d++)
			sb.insert(0, CONVERSION_TABLE[d][(int)n % 10]);
		return new PsyString(sb);
	}

	private static PsyInteger psyFromRoman(final PsyTextual oTextual)
		throws PsyUndefinedResultException
	{
		final var romanMatcher=ROMAN_PATTERN.matcher(oTextual.stringValue());
		if(!romanMatcher.matches())
			throw new PsyUndefinedResultException();
		int result=0;
		for(int d=3; d>=0; d--)
			for(int n=0; n<CONVERSION_TABLE[d].length; n++)
				if(romanMatcher.group(4-d).equals(CONVERSION_TABLE[d][n]))
				{
					result=10*result+n;
					break;
				}
		return PsyInteger.of(result);
	}

}
