package coneforest.psi.extensions.romannumerals;
import coneforest.psi.*;
import coneforest.psi.core.*;

public class PsiRomanNumerals
	extends PsiModule
{
	public PsiRomanNumerals()
		throws PsiException
	{
		registerOperators
			(
				new PsiOperator.Arity11<PsiInteger>
					("toroman", PsiRomanNumerals::psiToRoman),
				new PsiOperator.Arity11<PsiStringy>
					("fromroman", PsiRomanNumerals::psiFromRoman)
			);
	}

	private static PsiName psiToRoman(final PsiInteger oInteger)
		throws PsiException
	{
		long n=oInteger.longValue();
		if(n<0 || n>=4000)
			throw new PsiRangeCheckException();
		final StringBuilder sb=new StringBuilder();
		for(int d=0; n>0; n/=10, d++)
			sb.insert(0, conversionTable[d][(int)n % 10]);
		return new PsiName(sb);
	}

	private static PsiInteger psiFromRoman(final PsiStringy oStringy)
		throws PsiException
	{
		final java.util.regex.Matcher romanMatcher
			=romanPattern.matcher(oStringy.stringValue());
		if(!romanMatcher.matches())
			throw new PsiUndefinedResultException();
		int result=0;
		for(int d=3; d>=0; d--)
			for(int n=0; n<conversionTable[d].length; n++)
				if(romanMatcher.group(4-d).equals(conversionTable[d][n]))
				{
					result=10*result+n;
					break;
				}
		return PsiInteger.valueOf(result);
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