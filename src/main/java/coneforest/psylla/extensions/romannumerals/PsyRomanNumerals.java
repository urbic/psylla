package coneforest.psylla.extensions.romannumerals;
import coneforest.psylla.*;
import coneforest.psylla.core.*;

public class PsyRomanNumerals
	extends PsyModule
{
	public PsyRomanNumerals()
		throws PsyException
	{
		//super("text.roman");

		registerOperators
			(
				new PsyOperator.Arity11<PsyInteger>
					("toroman", PsyRomanNumerals::psyToRoman),
				new PsyOperator.Arity11<PsyStringy>
					("fromroman", PsyRomanNumerals::psyFromRoman)
			);
	}

	private static PsyName psyToRoman(final PsyInteger oInteger)
		throws PsyException
	{
		long n=oInteger.longValue();
		if(n<0 || n>=4000)
			throw new PsyRangeCheckException();
		final StringBuilder sb=new StringBuilder();
		for(int d=0; n>0; n/=10, d++)
			sb.insert(0, conversionTable[d][(int)n % 10]);
		return new PsyName(sb);
	}

	private static PsyInteger psyFromRoman(final PsyStringy oStringy)
		throws PsyException
	{
		final java.util.regex.Matcher romanMatcher
			=romanPattern.matcher(oStringy.stringValue());
		if(!romanMatcher.matches())
			throw new PsyUndefinedResultException();
		int result=0;
		for(int d=3; d>=0; d--)
			for(int n=0; n<conversionTable[d].length; n++)
				if(romanMatcher.group(4-d).equals(conversionTable[d][n]))
				{
					result=10*result+n;
					break;
				}
		return PsyInteger.valueOf(result);
	}

	private static final java.util.regex.Pattern romanPattern
		=java.util.regex.Pattern.compile("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$");

	private static final String[][] conversionTable=new String[][]
		{
			{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
			{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
			{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
			{"", "M", "MM", "MMM"}
		};
}
