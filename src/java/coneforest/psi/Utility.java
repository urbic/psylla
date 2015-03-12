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

	public static void psiDeleteFile(final PsiStringlike stringlike)
		throws PsiException
	{
		String fileName=fileNameToNative(stringlike.getString());
		try
		{
			java.nio.file.Files.delete(new java.io.File(fileName).toPath());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("undefinedfilename");
		}
		catch(java.nio.file.DirectoryNotEmptyException e)
		{
			throw new PsiException("directorynotempty");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	public static PsiString psiReadLink(final PsiStringlike stringlike)
		throws PsiException
	{
		String linkName=fileNameToNative(stringlike.getString());
		try
		{
			return new PsiString(java.nio.file.Files.readSymbolicLink(
					new java.io.File(linkName).toPath()).toString());
		}
		catch(java.nio.file.NotLinkException e)
		{
			throw new PsiException("notlink");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}
}
