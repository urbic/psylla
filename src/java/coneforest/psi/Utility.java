package coneforest.psi;

public class Utility
{
	public static String fileNameToNative(final String fileName)
	{
		return java.io.File.separatorChar=='/'? fileName: fileName.replace('/', java.io.File.separatorChar);
	}

	public static String fileNameFromNative(final String fileNameNative)
	{
		return java.io.File.separatorChar=='/'? fileNameNative: fileNameNative.replace(java.io.File.separatorChar, '/');
	}

	public static void psiSleep(final PsiNumeric numeric)
		throws PsiException
	{
		try
		{
			java.util.concurrent.TimeUnit.NANOSECONDS.sleep((long)(1E9*numeric.doubleValue()));
		}
		catch(InterruptedException e)
		{
			throw new PsiException("interrupt");
		}
	}
}
