package coneforest.psi;

public class FileSystem
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

	public static void psiCreateDirectory(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createDirectory(getNativePath(stringlike));
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiException("fileexists");
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

	public static void psiDeleteFile(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.delete(getNativePath(stringlike));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
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
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
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

	public static void psiSymLink(final PsiStringlike stringlike1,
			final PsiStringlike stringlike2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createSymbolicLink(getNativePath(stringlike2),
				getNativePath(stringlike1));
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiException("fileexists");
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
			throw new PsiException("filenotfound");
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
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getNativePath(stringlike),
					java.nio.file.attribute.BasicFileAttributes.class).isRegularFile());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
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

	public static PsiBoolean psiIsDirectory(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getNativePath(stringlike),
					java.nio.file.attribute.BasicFileAttributes.class).isDirectory());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
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

	public static PsiBoolean psiIsSymLink(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getNativePath(stringlike),
					java.nio.file.attribute.BasicFileAttributes.class).isSymbolicLink());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiInteger psiFileSize(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.size(getNativePath(stringlike)));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiInteger psiFileAccessTime(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getNativePath(stringlike),
					java.nio.file.attribute.BasicFileAttributes.class).lastAccessTime().toMillis());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiInteger psiFileCreationTime(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getNativePath(stringlike),
					java.nio.file.attribute.BasicFileAttributes.class).creationTime().toMillis());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiInteger psiFileModifiedTime(final PsiStringlike stringlike)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getNativePath(stringlike),
					java.nio.file.attribute.BasicFileAttributes.class).lastModifiedTime().toMillis());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiArray psiListDirectory(final PsiStringlike stringlike)
		throws PsiException
	{
		PsiArray array=new PsiArray();
		try
		{
			java.nio.file.DirectoryStream<java.nio.file.Path> dirStream
				=java.nio.file.Files.newDirectoryStream(getNativePath(stringlike));
			for(java.nio.file.Path item: dirStream)
				array.psiAppend(new PsiString(item.toString()));
			return array;
		}
		//catch(java.nio.file.DirectoryIteratorException e)
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		catch(SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}
}
