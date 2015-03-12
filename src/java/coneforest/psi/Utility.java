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

	public static java.nio.file.Path getNativePath(final PsiStringlike stringlike)
	{
		return new java.io.File(fileNameToNative(stringlike.getString())).toPath();
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
		try
		{
			java.nio.file.Files.delete(getNativePath(stringlike));
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
		try
		{
			return new PsiString(java.nio.file.Files.readSymbolicLink(getNativePath(stringlike))
					.toString());
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

	public static void psiRenameFile(final PsiStringlike stringlike1,
			final PsiStringlike stringlike2)
		throws PsiException
	{
		String fileName1=fileNameToNative(stringlike1.getString());
		String fileName2=fileNameToNative(stringlike2.getString());
		try
		{
			java.nio.file.Files.move(getNativePath(stringlike1),
					getNativePath(stringlike2));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("undefinedfilename");
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiException("fileexists");
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

	public static PsiBoolean psiFileExists(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.exists(getNativePath(stringlike)));
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiBoolean psiIsFile(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.isRegularFile(getNativePath(stringlike)));
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiBoolean psiIsDirectory(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.isDirectory(getNativePath(stringlike)));
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiBoolean psiIsSymlink(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.isSymbolicLink(getNativePath(stringlike)));
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

}
