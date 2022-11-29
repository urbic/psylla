package coneforest.psylla.core;
import coneforest.psylla.*;
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
@Type("filesystem")
public class PsyFileSystem
{
	private PsyFileSystem()
	{
	}

	public static Path getPath(final PsyTextual oFileName)
	{
		return Paths.get(oFileName.stringValue());
	}

	/**
	*	Creates a new directory with the given name.
	*
	*	@param oFileName a {@code textual} representing the name of the
	*	directory being created.
	*
	*	@throws PsyFileExistsException when the directory already exists.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static void psyCreateDirectory(final PsyTextual oFileName)
		throws
			PsyFileExistsException,
			PsyFileAccessDeniedException,
			PsySecurityErrorException,
			PsyIOErrorException
	{
		try
		{
			Files.createDirectory(getPath(oFileName));
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	/*
	public static PsyName psyCreateTempFile(final PsyTextual oPrefix,
			final PsyTextual oSuffix, PsyTextual oDirectory)
		throws PsyException
	{
		try
		{
			Files.createTempFile()
			//java.io.File file=java.io.File.createTempFile(oPrefix.stringValue(),
			//		oSuffix.stringValue(), new java.io.File(oDirectory.stringValue()));
			//return new PsyName(file.getPath());
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}
	*/

	/**
	*	Deletes a file or empty directory with a given name.
	*
	*	@param oFileName a {@code textual} representing the name of a file or
	*	directory.
	*
	*	@throws PsyDirectoryNotEmptyException when the directory is not empty.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static void psyDeleteFile(final PsyTextual oFileName)
		throws
			PsyFileNotFoundException,
			PsyFileAccessDeniedException,
			PsyDirectoryNotEmptyException,
			PsySecurityErrorException,
			PsyIOErrorException
	{
		try
		{
			Files.delete(getPath(oFileName));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmptyException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Copies a file to the target file.
	*
	*	@param oSourceName a {@code textual} representing the name of the file to copy.
	*	@param oTargetName a {@code textual} representing the name of the target file.
	*
	*	@throws PsyDirectoryNotEmptyException
	*	@throws PsyFileExistsException
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*	@throws PsyUnsupportedException
	*/
	public static void psyCopyFile(final PsyTextual oSourceName, final PsyTextual oTargetName)
		throws
			PsyDirectoryNotEmptyException,
			PsyFileAccessDeniedException,
			PsyFileExistsException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyUnsupportedException
	{
		try
		{
			Files.copy(getPath(oSourceName), getPath(oTargetName));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmptyException();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyName psyReadLink(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsyNotLinkException,
			PsySecurityErrorException
	{
		try
		{
			return new PsyName(Files.readSymbolicLink(getPath(oFileName)).toString());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final NotLinkException ex)
		{
			throw new PsyNotLinkException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static void psySymLink(final PsyTextual oFileName1, final PsyTextual oFileName2)
		throws
			PsyFileAccessDeniedException,
			PsyFileExistsException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyUnsupportedException
	{
		try
		{
			Files.createSymbolicLink(getPath(oFileName2),
				getPath(oFileName1));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static void psyHardLink(final PsyTextual oFileName1,
			final PsyTextual oFileName2)
		throws
			PsyFileAccessDeniedException,
			PsyFileExistsException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyUnsupportedException
	{
		try
		{
			Files.createLink(getPath(oFileName2), getPath(oFileName1));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static void psyRenameFile(final PsyTextual oFileName1,
			final PsyTextual oFileName2)
		throws
			PsyDirectoryNotEmptyException,
			PsyFileAccessDeniedException,
			PsyFileExistsException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			Files.move(getPath(oFileName1), getPath(oFileName2));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException();
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmptyException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyFileExists(final PsyTextual oFileName)
		throws PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.valueOf(Files.exists(getPath(oFileName)));
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static PsyBoolean psyIsFile(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isRegularFile());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyIsDirectory(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isDirectory());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyIsSameFile(final PsyTextual oFileName1, final PsyTextual oFileName2)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.valueOf(Files.isSameFile(
					getPath(oFileName1), getPath(oFileName2)));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyIsSymLink(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isSymbolicLink());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Returns the {@code integer} representing the size (in bytes) for the
	*	file or directory with the given name.
	*
	*	@param oFileName a {@code textual} representing the file name.
	*
	*	@return a {@code integer} representing the size (in bytes) of the file
	*	or directory.
	*
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static PsyInteger psyFileSize(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyInteger.valueOf(Files.size(getPath(oFileName)));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFileAccessTime(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyInteger.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).lastAccessTime().toMillis());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFileCreationTime(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyInteger.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).creationTime().toMillis());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFileModifiedTime(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyInteger.valueOf(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).lastModifiedTime().toMillis());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Returns a {@code name} representing the absolute name of the current
	*	directory.
	*
	*	@return a {@code name} representing the absolute name of the current
	*	directory.
	*/
	public static PsyName psyCurrentDirectory()
	{
		return new PsyName(Paths.get("").toAbsolutePath().toString());
	}

	/**
	*	Returns a {@code name} representing the absolute path to given file.
	*
	*	@param oFileName a {@code name} representing file name.
	*
	*	@return a {@code name} representing the absolute path. directory.
	*
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static PsyName psyFileAbsolutePath(final PsyTextual oFileName)
		throws
			PsyIOErrorException
	{
		try
		{
			return new PsyName(Paths.get(oFileName.stringValue()).toAbsolutePath().toString());
		}
		catch(final java.io.IOError ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFilePermissions(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			final java.util.Set<PosixFilePermission> permSet
				=Files.getPosixFilePermissions(getPath(oFileName));
			long permissions=0L;
			for(final var p: permSet)
				permissions|=(256L>>p.ordinal());

			return PsyInteger.valueOf(permissions);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static void psyChangeFilePermissions(final PsyTextual oFileName, final PsyInteger oPermissions)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			final var permissions=oPermissions.longValue();
			final var permSet=new java.util.HashSet<PosixFilePermission>();
			final var permValues=PosixFilePermission.values();
			for(int i=0; i<=8; i++)
				if((permissions&(256L>>i))!=0L)
					permSet.add(permValues[i]);
			Files.setPosixFilePermissions(getPath(oFileName), permSet);

		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static PsyObject psyFileAttribute(final PsyTextual oFileName, final PsyTextual oAttribute)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyTypeCheckException,
			PsyUndefinedException,
			PsyUnsupportedException
	{
		try
		{
			return toPsyObject(Files.getAttribute(getPath(oFileName),
					oAttribute.stringValue(), LinkOption.NOFOLLOW_LINKS));
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheckException();
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException();
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static void psyChangeFileAttribute(final PsyTextual oFileName, final PsyTextual oAttribute, final PsyObject oValue)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyTypeCheckException,
			PsyUndefinedException,
			PsyUnsupportedException
	{
		try
		{
			Files.setAttribute(getPath(oFileName),
					oAttribute.stringValue(), fromPsyObject(oValue),
					LinkOption.NOFOLLOW_LINKS);
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheckException();
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException();
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static PsyFormalStream<PsyName> psyFiles(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsyNotDirectoryException,
			PsySecurityErrorException
	{
		try
		{
			return new PsyStream(Files.list(getPath(oFileName)).<PsyName>map(
				(final var path)->new PsyName(path.toString())));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final NotDirectoryException ex)
		{
			throw new PsyNotDirectoryException();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
	}

	private static PsyObject toPsyObject(final Object obj)
	{
		//System.out.println(obj.getClass());
		if(obj instanceof String)
			return new PsyName((String)obj);
		else if(obj instanceof Integer)
			return PsyInteger.valueOf(((Integer)obj).longValue());
		else if(obj instanceof Long)
			return PsyInteger.valueOf(((Long)obj).longValue());
		else if(obj instanceof Boolean)
			return PsyBoolean.valueOf((Boolean)obj);
		else
			throw new ClassCastException();
	}

	private static Object fromPsyObject(final PsyObject o)
	{
		//System.out.println(obj.getClass());
		if(o instanceof PsyTextual)
			return ((PsyTextual)o).stringValue();
		else if(o instanceof PsyInteger)
			return ((PsyInteger)o).longValue();
		else if(o instanceof PsyBoolean)
			return ((PsyBoolean)o).booleanValue();
		else
			throw new ClassCastException();
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity20<PsyTextual, PsyInteger>("changefilepermissions", PsyFileSystem::psyChangeFilePermissions),
			new PsyOperator.Arity20<PsyTextual, PsyTextual>("copyfile", PsyFileSystem::psyCopyFile),
			new PsyOperator.Arity10<PsyTextual>("createdirectory", PsyFileSystem::psyCreateDirectory),
			new PsyOperator.Arity01("currentdirectory", PsyFileSystem::psyCurrentDirectory),
			new PsyOperator.Arity10<PsyTextual>("deletefile", PsyFileSystem::psyDeleteFile),
			new PsyOperator.Arity11<PsyTextual>("fileaccesstime", PsyFileSystem::psyFileAccessTime),
			new PsyOperator.Arity21<PsyTextual, PsyTextual>("fileattribute", PsyFileSystem::psyFileAttribute),
			new PsyOperator.Arity11<PsyTextual>("filecreationtime", PsyFileSystem::psyFileCreationTime),
			new PsyOperator.Arity11<PsyTextual>("fileexists", PsyFileSystem::psyFileExists),
			new PsyOperator.Arity11<PsyTextual>("filemodifiedtime", PsyFileSystem::psyFileModifiedTime),
			new PsyOperator.Arity11<PsyTextual>("filepermissions", PsyFileSystem::psyFilePermissions),
			new PsyOperator.Arity11<PsyTextual>("files", PsyFileSystem::psyFiles),
			new PsyOperator.Arity11<PsyTextual>("filesize", PsyFileSystem::psyFileSize),
			new PsyOperator.Arity20<PsyTextual, PsyTextual>("hardlink", PsyFileSystem::psyHardLink),
			new PsyOperator.Arity11<PsyTextual>("isdirectory", PsyFileSystem::psyIsDirectory),
			new PsyOperator.Arity11<PsyTextual>("isfile", PsyFileSystem::psyIsFile),
			new PsyOperator.Arity21<PsyTextual, PsyTextual>("issamefile", PsyFileSystem::psyIsSameFile),
			new PsyOperator.Arity11<PsyTextual>("issymlink", PsyFileSystem::psyIsSymLink),
			new PsyOperator.Arity11<PsyTextual>("readlink", PsyFileSystem::psyReadLink),
			new PsyOperator.Arity20<PsyTextual, PsyTextual>("renamefile", PsyFileSystem::psyRenameFile),
			new PsyOperator.Arity20<PsyTextual, PsyTextual>("symlink", PsyFileSystem::psySymLink),
		};

}
