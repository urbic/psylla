package coneforest.psi;
import coneforest.psi.core.*;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.NotLinkException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;

/**
*	An utility class providing filesystem-related methods.
*/
public class FileSystem
{
	private FileSystem()
	{
	}

	public static Path getPath(final PsiStringy oFileName)
	{
		return Paths.get(oFileName.stringValue());
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
			Files.createDirectory(getPath(oFileName));
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final IOException e)
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
			Files.createTempFile()
			//java.io.File file=java.io.File.createTempFile(oPrefix.stringValue(),
			//		oSuffix.stringValue(), new java.io.File(oDirectory.stringValue()));
			//return new PsiName(file.getPath());
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final IOException e)
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
			Files.delete(getPath(oFileName));
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final DirectoryNotEmptyException e)
		{
			throw new PsiDirectoryNotEmptyException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiCopyFile(final PsiStringy oFileName1, final PsiStringy oFileName2)
		throws
			PsiDirectoryNotEmptyException,
			PsiFileAccessDeniedException,
			PsiFileExistsException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException,
			PsiUnsupportedException
	{
		try
		{
			Files.copy(getPath(oFileName1), getPath(oFileName2));
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final DirectoryNotEmptyException e)
		{
			throw new PsiDirectoryNotEmptyException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiName psiReadLink(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiNotLinkException,
			PsiSecurityErrorException
	{
		try
		{
			return new PsiName(Files.readSymbolicLink(getPath(oFileName)).toString());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final NotLinkException e)
		{
			throw new PsiNotLinkException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiSymLink(final PsiStringy oFileName1, final PsiStringy oFileName2)
		throws
			PsiFileAccessDeniedException,
			PsiFileExistsException,
			PsiIOErrorException,
			PsiSecurityErrorException,
			PsiUnsupportedException
	{
		try
		{
			Files.createSymbolicLink(getPath(oFileName2),
				getPath(oFileName1));
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiHardLink(final PsiStringy oFileName1,
			final PsiStringy oFileName2)
		throws
			PsiFileAccessDeniedException,
			PsiFileExistsException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException,
			PsiUnsupportedException
	{
		try
		{
			Files.createLink(getPath(oFileName2), getPath(oFileName1));
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static void psiRenameFile(final PsiStringy oFileName1,
			final PsiStringy oFileName2)
		throws
			PsiDirectoryNotEmptyException,
			PsiFileAccessDeniedException,
			PsiFileExistsException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			Files.move(getPath(oFileName1), getPath(oFileName2));
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsiFileExistsException();
		}
		catch(final DirectoryNotEmptyException e)
		{
			throw new PsiDirectoryNotEmptyException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiFileExists(final PsiStringy oFileName)
		throws PsiSecurityErrorException
	{
		try
		{
			return PsiBoolean.valueOf(Files.exists(getPath(oFileName)));
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiBoolean psiIsFile(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiBoolean.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isRegularFile());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiIsDirectory(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiBoolean.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isDirectory());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiIsSameFile(final PsiStringy oFileName1, final PsiStringy oFileName2)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiBoolean.valueOf(Files.isSameFile(
					getPath(oFileName1), getPath(oFileName2)));
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiBoolean psiIsSymLink(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiBoolean.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isSymbolicLink());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	/**
	*	Returns the Ψ-{@code integer} representing the size (in bytes) for the
	*	file or directory with the given name.
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
			return PsiInteger.valueOf(Files.size(getPath(oFileName)));
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiInteger psiFileAccessTime(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiInteger.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).lastAccessTime().toMillis());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiInteger psiFileCreationTime(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiInteger.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).creationTime().toMillis());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static PsiInteger psiFileModifiedTime(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			return PsiInteger.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).lastModifiedTime().toMillis());
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	/*
	public static PsiArray psiListDirectory(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiLimitCheckException,
			PsiSecurityErrorException
	{
		PsiArray array=new PsiArray();
		try
		{
			DirectoryStream<Path> dirStream
				=Files.newDirectoryStream(getPath(oFileName));
			for(Path item: dirStream)
				array.psiAppend(new PsiString(item.toString()));
			return array;
		}
		//catch(final java.nio.file.DirectoryIteratorException e)
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}
	*/

	/**
	*	Returns a Ψ-{@code name} representing the absolute name of the current
	*	directory.
	*
	*	@return a Ψ-{@code name} representing the absolute name of the current
	*	directory.
	*/
	public static PsiName psiCurrentDirectory()
	{
		return new PsiName(Paths.get("").toAbsolutePath().toString());
	}

	public static PsiInteger psiFilePermissions(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			final java.util.Set<PosixFilePermission> permSet
				=Files.getPosixFilePermissions(getPath(oFileName));
			long permissions=0L;
			for(PosixFilePermission p: permSet)
				permissions|=(256L>>p.ordinal());

			return PsiInteger.valueOf(permissions);
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static void psiChangeFilePermissions(final PsiStringy oFileName, final PsiInteger oPermissions)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException
	{
		try
		{
			final long permissions=oPermissions.longValue();
			final java.util.HashSet<PosixFilePermission> permSet
				=new java.util.HashSet<PosixFilePermission>();
			final PosixFilePermission[] permValues
				=PosixFilePermission.values();
			for(int i=0; i<=8; i++)
				if((permissions&(256L>>i))!=0L)
					permSet.add(permValues[i]);
			Files.setPosixFilePermissions(getPath(oFileName), permSet);

		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiObject psiFileAttribute(final PsiStringy oFileName, final PsiStringy oAttribute)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException,
			PsiTypeCheckException,
			PsiUndefinedException,
			PsiUnsupportedException
	{
		try
		{
			return toPsiObject(Files.getAttribute(getPath(oFileName),
					oAttribute.stringValue(), LinkOption.NOFOLLOW_LINKS));
		}
		catch(final ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsiUndefinedException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static void psiChangeFileAttribute(final PsiStringy oFileName, final PsiStringy oAttribute, final PsiObject oValue)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiSecurityErrorException,
			PsiTypeCheckException,
			PsiUndefinedException,
			PsiUnsupportedException
	{
		try
		{
			Files.setAttribute(getPath(oFileName),
					oAttribute.stringValue(), fromPsiObject(oValue),
					LinkOption.NOFOLLOW_LINKS);
		}
		catch(final ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsiUnsupportedException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsiUndefinedException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	public static PsiIterable<PsiName> psiFiles(final PsiStringy oFileName)
		throws
			PsiFileAccessDeniedException,
			PsiFileNotFoundException,
			PsiIOErrorException,
			PsiNotDirectoryException,
			PsiSecurityErrorException
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
										catch(final IOException e)
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

					private final DirectoryStream directoryStream
						=Files.newDirectoryStream(getPath(oFileName));
					private final java.util.Iterator<Path> directoryIterator
						=directoryStream.iterator();
				};
		}
		catch(final NoSuchFileException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final NotDirectoryException e)
		{
			throw new PsiNotDirectoryException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsiFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsiIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
	}

	private static PsiObject toPsiObject(final Object obj)
	{
		//System.out.println(obj.getClass());
		if(obj instanceof String)
			return new PsiName((String)obj);
		else if(obj instanceof Integer)
			return PsiInteger.valueOf(((Integer)obj).longValue());
		else if(obj instanceof Long)
			return PsiInteger.valueOf(((Long)obj).longValue());
		else if(obj instanceof Boolean)
			return PsiBoolean.valueOf((Boolean)obj);
		else
			throw new ClassCastException();
	}

	private static Object fromPsiObject(final PsiObject o)
	{
		//System.out.println(obj.getClass());
		if(o instanceof PsiStringy)
			return ((PsiStringy)o).stringValue();
		else if(o instanceof PsiInteger)
			return ((PsiInteger)o).longValue();
		else if(o instanceof PsiBoolean)
			return ((PsiBoolean)o).booleanValue();
		else
			throw new ClassCastException();
	}
}
