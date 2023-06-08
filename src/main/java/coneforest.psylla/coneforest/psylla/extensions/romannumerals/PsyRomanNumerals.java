package coneforest.psylla.extensions.romannumerals;

import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsyUndefinedResult;
import coneforest.psylla.core.types.PsyInteger;
import coneforest.psylla.core.types.PsyModule;
import coneforest.psylla.core.types.PsyName;
import coneforest.psylla.core.types.PsyOperator;
import coneforest.psylla.core.types.PsyTextual;

public class PsyRomanNumerals
	extends PsyModule
{
	public PsyRomanNumerals()
		throws PsyError
	{
		//super("text.roman");

		registerOperators
			(
				new PsyOperator.Arity11<PsyInteger>
					("toroman", PsyRomanNumerals::psyToRoman),
				new PsyOperator.Arity11<PsyTextual>
					("fromroman", PsyRomanNumerals::psyFromRoman)
			);
	}

	private static PsyName psyToRoman(final PsyInteger oInteger)
		throws PsyError
	{
		long n=oInteger.longValue();
		if(n<0 || n>=4000)
			throw new PsyRangeCheck();
		final var sb=new StringBuilder();
		for(int d=0; n>0; n/=10, d++)
			sb.insert(0, conversionTable[d][(int)n % 10]);
		return new PsyName(sb);
	}

	private static PsyInteger psyFromRoman(final PsyTextual oTextual)
		throws PsyError
	{
		final var romanMatcher=romanPattern.matcher(oTextual.stringValue());
		if(!romanMatcher.matches())
			throw new PsyUndefinedResult();
		int result=0;
		for(int d=3; d>=0; d--)
			for(int n=0; n<conversionTable[d].length; n++)
				if(romanMatcher.group(4-d).equals(conversionTable[d][n]))
				{
					result=10*result+n;
					break;
				}
		return PsyInteger.of(result);
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
