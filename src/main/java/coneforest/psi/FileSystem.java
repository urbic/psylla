package coneforest.psi;
import coneforest.psi.core.*;

public class FileSystem
{
	public static java.nio.file.Path getPath(String fileName)
	{
		// TODO
		if(fileName.startsWith("~"))
			fileName=System.getProperty("user.home")+fileName.substring(1);
		return new java.io.File(fileName).toPath();
	}

	public static java.nio.file.Path getPath(final PsiStringy oFileName)
	{
		return getPath(oFileName.stringValue());
		/*
		String name=oFileName.stringValue();
		if(name.startsWith("~"))
			name=System.getProperty("user.home")+name.substring(1);
		return new java.io.File(name).toPath();
		*/
	}

	public static void psiCreateDirectory(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createDirectory(getPath(oFileName));
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	/*
	public static PsiName psiCreateTempFile(final PsiStringy oPrefix,
			final PsiStringy oSuffix, PsiStringy oDirectory)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createTempFile()
			//java.io.File file=java.io.File.createTempFile(oPrefix.stringValue(),
			//		oSuffix.stringValue(), new java.io.File(oDirectory.stringValue()));
			//return new PsiName(file.getPath());
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}
	*/

	public static void psiDeleteFile(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.delete(getPath(oFileName));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.nio.file.DirectoryNotEmptyException e)
		{
			throw new PsiDirectoryNotEmptyException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiCopyFile(final PsiStringy oFileName1, final PsiStringy oFileName2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.copy(getPath(oFileName1), getPath(oFileName2));
		}
		catch(UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.nio.file.DirectoryNotEmptyException e)
		{
			throw new PsiDirectoryNotEmptyException();
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiString psiReadLink(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return new PsiString(java.nio.file.Files.readSymbolicLink(getPath(oFileName))
					.toString());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.nio.file.NotLinkException e)
		{
			throw new PsiNotLinkException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiSymLink(final PsiStringy oFileName1, final PsiStringy oFileName2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createSymbolicLink(getPath(oFileName2),
				getPath(oFileName1));
		}
		catch(UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiHardLink(final PsiStringy oFileName1,
			final PsiStringy oFileName2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createLink(getPath(oFileName2),
				getPath(oFileName1));
		}
		catch(UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiRenameFile(final PsiStringy oFileName1,
			final PsiStringy oFileName2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.move(getPath(oFileName1),
					getPath(oFileName2));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(java.nio.file.DirectoryNotEmptyException e)
		{
			throw new PsiDirectoryNotEmptyException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiFileExists(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.exists(getPath(oFileName)));
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiBoolean psiIsFile(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getPath(oFileName),
					java.nio.file.attribute.BasicFileAttributes.class,
					java.nio.file.LinkOption.NOFOLLOW_LINKS).isRegularFile());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiIsDirectory(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getPath(oFileName),
					java.nio.file.attribute.BasicFileAttributes.class,
					java.nio.file.LinkOption.NOFOLLOW_LINKS).isDirectory());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiIsSameFile(final PsiStringy oFileName1, final PsiStringy oFileName2)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.isSameFile(
					getPath(oFileName1), getPath(oFileName2)));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiIsSymLink(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getPath(oFileName),
					java.nio.file.attribute.BasicFileAttributes.class,
					java.nio.file.LinkOption.NOFOLLOW_LINKS).isSymbolicLink());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiInteger psiFileSize(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.size(getPath(oFileName)));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiInteger psiFileAccessTime(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getPath(oFileName),
					java.nio.file.attribute.BasicFileAttributes.class).lastAccessTime().toMillis());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiInteger psiFileCreationTime(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getPath(oFileName),
					java.nio.file.attribute.BasicFileAttributes.class).creationTime().toMillis());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiInteger psiFileModifiedTime(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getPath(oFileName),
					java.nio.file.attribute.BasicFileAttributes.class).lastModifiedTime().toMillis());
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiArray psiListDirectory(final PsiStringy oFileName)
		throws PsiException
	{
		PsiArray array=new PsiArray();
		try
		{
			java.nio.file.DirectoryStream<java.nio.file.Path> dirStream
				=java.nio.file.Files.newDirectoryStream(getPath(oFileName));
			for(java.nio.file.Path item: dirStream)
				array.psiAppend(new PsiString(item.toString()));
			return array;
		}
		//catch(java.nio.file.DirectoryIteratorException e)
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiString psiCurrentDirectory()
		throws PsiException
	{
		return new PsiString(java.nio.file.Paths.get("").toAbsolutePath().toString());
	}

	public static PsiIterable<PsiName> psiFiles(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return new PsiIterable<PsiName>()
				{

					public java.util.Iterator<PsiName> iterator()
					{
						return new java.util.Iterator<PsiName>()
							{
								@Override
								public boolean hasNext()
								{
									if(directoryIterator.hasNext())
										return true;
									else
										try
										{
											directoryStream.close();
											return false;
										}
										catch(java.io.IOException e)
										{
											return false;
										}
								}

								@Override
								public PsiName next()
								{
									return new PsiName(directoryIterator.next().toString());
								}
							};
					}

					private final java.nio.file.DirectoryStream directoryStream
						=java.nio.file.Files.newDirectoryStream(getPath(oFileName));
					private final java.util.Iterator<java.nio.file.Path> directoryIterator
						=directoryStream.iterator();
				};
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.nio.file.NotDirectoryException e)
		{
			throw new PsiNotDirectoryException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}
}