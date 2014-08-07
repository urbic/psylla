package coneforest.psi.extensions.romannumerals;
import coneforest.psi.*;

public class PsiRomanNumerals
	extends PsiModule
{
	public PsiRomanNumerals()
	{
		super();
		registerOperatorClasses(_toroman.class, _fromroman.class);
	}

	public static class _toroman
		extends PsiOperator
	{
		@Override
		public void invoke(Interpreter interpreter)
		{
			OperandStack opstack=interpreter.getOperandStack();
			if(opstack.size()<1)
			{
				interpreter.error("stackunderflow", this);
				return;
			}

			PsiObject integer=opstack.pop();
			try
			{
				opstack.push(psiToRoman((PsiInteger)integer));
			}
			catch(ClassCastException e)
			{
				opstack.push(integer);
				interpreter.error("typecheck", this);
			}
			catch(PsiException e)
			{
				opstack.push(integer);
				interpreter.error(e.kind(), this);
			}
		}

		private PsiString psiToRoman(PsiInteger integer)
			throws PsiException
		{
			long n=integer.getValue().longValue();
			if(n<0 || n>=4000)
				throw new PsiException("rangecheck");
			StringBuilder sb=new StringBuilder();
			for(int d=0; n>0; n/=10, d++)
				sb.insert(0, conversionTable[d][(int)n%10]);
			return new PsiString(sb);
		}
	}

	public static class _fromroman
		extends PsiOperator
	{
		@Override
		public void invoke(Interpreter interpreter)
		{
			OperandStack opstack=interpreter.getOperandStack();
			if(opstack.size()<1)
			{
				interpreter.error("stackunderflow", this);
				return;
			}

			PsiObject stringlike=opstack.pop();
			try
			{
				opstack.push(psiFromRoman((PsiStringlike)stringlike));
			}
			catch(ClassCastException e)
			{
				opstack.push(stringlike);
				interpreter.error("typecheck", this);
			}
			catch(PsiException e)
			{
				opstack.push(stringlike);
				interpreter.error(e.kind(), this);
			}
		}

		private PsiInteger psiFromRoman(PsiStringlike stringlike)
			throws PsiException
		{
			java.util.regex.Matcher romanMatcher=romanPattern.matcher(stringlike.getString());
			if(romanMatcher.matches())
			{
				int result=0;
				for(int d=3; d>=0; d--)
					for(int n=0; n<conversionTable[d].length; n++)
						if(romanMatcher.group(4-d).equals(conversionTable[d][n]))
						{
							result=10*result+n;
							break;
						}
				return new PsiInteger(result);
			}
			else
				throw new PsiException("undefinedresult");
		}

		private static java.util.regex.Pattern romanPattern
			=java.util.regex.Pattern.compile("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$");
	}

	private static String[][] conversionTable=new String[][]
		{
			{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
			{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
			{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
			{"", "M", "MM", "MMM"}
		};
}