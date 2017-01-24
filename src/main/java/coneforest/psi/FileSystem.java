package coneforest.psi;
import coneforest.psi.core.*;

/**
*	An utility class providing filesystem-related methods.
*/
public class FileSystem
{
	private FileSystem()
	{
	}

	public static java.nio.file.Path getPath(final String fileName)
	{
		return new java.io.File(fileName).toPath();
	}

	public static java.nio.file.Path getPath(final PsiStringy oFileName)
	{
		return getPath(oFileName.stringValue());
	}

	/**
	*	Creates a new directory with the given name.
	*
	*	@param oFileName a Ψ-{@code stringy} representing the name of the
	*	directory being created.
	*
	*	@throws PsiFileExistsException when the directory already exists.
	*	@throws PsiFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsiSecurityErrorException when security policy is violated.
	*	@throws PsiIOErrorException when an input/output error occurs.
	*/
	public static void psiCreateDirectory(final PsiStringy oFileName)
		throws
			PsiFileExistsException,
			PsiFileAccessDeniedException,
			PsiSecurityErrorException,
			PsiIOErrorException
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

	/**
	*	Deletes a file or nonempty directory with a given name.
	*
	*	@param oFileName a Ψ-{@code stringy} representing the name of a file or
	*	directory.
	*
	*	@throws PsiDirectoryNotEmptyException when the directory is not empty.
	*	@throws PsiFileNotFoundException when the file or directory does not exist.
	*	@throws PsiFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsiSecurityErrorException when security policy is violated.
	*	@throws PsiIOErrorException when an input/output error occurs.
	*/
	public static void psiDeleteFile(final PsiStringy oFileName)
		throws
			PsiFileNotFoundException,
			PsiFileAccessDeniedException,
			PsiDirectoryNotEmptyException,
			PsiSecurityErrorException,
			PsiIOErrorException
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiName psiReadLink(final PsiStringy oFileName)
		throws PsiException
	{
		try
		{
			return new PsiName(java.nio.file.Files.readSymbolicLink(getPath(oFileName))
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
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
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	/**
	*	Returns the size (in bytes) for the file or directory with the given name.
	*
	*	@param oFileName a Ψ-{@code stringy} representing the file name.
	*	@return a Ψ-{@code integer} representing the size (in bytes) of the
	*	file or directory.
	*	@throws PsiFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsiFileNotFoundException when the file or directory does not exist.
	*	@throws PsiSecurityErrorException when security policy is violated.
	*	@throws PsiIOErrorException when an input/output error occurs.
	*/
	public static PsiInteger psiFileSize(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiInteger.valueOf(java.nio.file.Files.size(getPath(oFileName)));
		}
		catch(java.nio.file.NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
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
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
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
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
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
		catch(java.lang.SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(java.nio.file.AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
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

	/**
	*	Returns a Ψ-{@code name} representing the name of the current
	*	directory.
	*
	*	@return a Ψ-{@code name} representing the name of the current
	*	directory.
	*/
	public static PsiName psiCurrentDirectory()
	//	throws PsiException
	{
		return new PsiName(java.nio.file.Paths.get("").toAbsolutePath().toString());
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
