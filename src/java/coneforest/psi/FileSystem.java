package coneforest.psi;

public class FileSystem
{
	public static java.nio.file.Path getPath(final PsiStringy stringy)
	{
		return new java.io.File(stringy.getString()).toPath();
	}

	public static void psiCreateDirectory(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createDirectory(getPath(stringy));
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

	public static void psiDeleteFile(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.delete(getPath(stringy));
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

	public static PsiString psiReadLink(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return new PsiString(java.nio.file.Files.readSymbolicLink(getPath(stringy))
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

	public static void psiSymLink(final PsiStringy stringy1,
			final PsiStringy stringy2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createSymbolicLink(getPath(stringy2),
				getPath(stringy1));
		}
		catch(UnsupportedOperationException e)
		{
			throw new PsiException("unsupported");
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

	public static void psiHardLink(final PsiStringy stringy1,
			final PsiStringy stringy2)
		throws PsiException
	{
		try
		{
			java.nio.file.Files.createLink(getPath(stringy2),
				getPath(stringy1));
		}
		catch(UnsupportedOperationException e)
		{
			throw new PsiException("unsupported");
		}
		catch(java.nio.file.FileAlreadyExistsException e)
		{
			throw new PsiException("fileexists");
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

	public static void psiRenameFile(final PsiStringy stringy1,
			final PsiStringy stringy2)
		throws PsiException
	{
		String fileName1=stringy1.getString();
		String fileName2=stringy2.getString();
		try
		{
			java.nio.file.Files.move(getPath(stringy1),
					getPath(stringy2));
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

	public static PsiBoolean psiFileExists(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.exists(getPath(stringy)));
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiException("securityerror");
		}
	}

	public static PsiBoolean psiIsFile(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getPath(stringy),
					java.nio.file.attribute.BasicFileAttributes.class,
					java.nio.file.LinkOption.NOFOLLOW_LINKS).isRegularFile());
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

	public static PsiBoolean psiIsDirectory(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getPath(stringy),
					java.nio.file.attribute.BasicFileAttributes.class,
					java.nio.file.LinkOption.NOFOLLOW_LINKS).isDirectory());
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

	public static PsiBoolean psiIsSameFile(final PsiStringy name1, final PsiStringy name2)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.isSameFile(getPath(name1), getPath(name2)));
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

	public static PsiBoolean psiIsSymLink(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(java.nio.file.Files.readAttributes(getPath(stringy),
					java.nio.file.attribute.BasicFileAttributes.class,
					java.nio.file.LinkOption.NOFOLLOW_LINKS).isSymbolicLink());
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

	public static PsiInteger psiFileSize(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.size(getPath(stringy)));
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

	public static PsiInteger psiFileAccessTime(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getPath(stringy),
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

	public static PsiInteger psiFileCreationTime(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getPath(stringy),
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

	public static PsiInteger psiFileModifiedTime(final PsiStringy stringy)
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.readAttributes(getPath(stringy),
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

	public static PsiArray psiListDirectory(final PsiStringy stringy)
		throws PsiException
	{
		PsiArray array=new PsiArray();
		try
		{
			java.nio.file.DirectoryStream<java.nio.file.Path> dirStream
				=java.nio.file.Files.newDirectoryStream(getPath(stringy));
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

	public static PsiString psiCurrentDirectory()
		throws PsiException
	{
		return new PsiString(java.nio.file.Paths.get("").toAbsolutePath().toString());
	}

	public static PsiIterable<PsiName> psiFiles(final PsiStringy stringy)
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
											//throw new PsiException("ioerror");
										}
								}

								@Override
								public PsiName next()
								{
									return new PsiName(directoryIterator.next().toString());
								}
							};
					}
								
					private java.nio.file.DirectoryStream directoryStream
						=java.nio.file.Files.newDirectoryStream(getPath(stringy));
					private java.util.Iterator<java.nio.file.Path> directoryIterator
						=directoryStream.iterator();
				};
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiException("filenotfound");
		}
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
