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

	public static Path getPath(final PsyStringy oFileName)
	{
		return Paths.get(oFileName.stringValue());
	}

	/**
	*	Creates a new directory with the given name.
	*
	*	@param oFileName a Ψ-{@code stringy} representing the name of the
	*	directory being created.
	*
	*	@throws PsyFileExistsException when the directory already exists.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static void psyCreateDirectory(final PsyStringy oFileName)
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
		catch(final FileAlreadyExistsException e)
		{
			throw new PsyFileExistsException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	/*
	public static PsyName psyCreateTempFile(final PsyStringy oPrefix,
			final PsyStringy oSuffix, PsyStringy oDirectory)
		throws PsyException
	{
		try
		{
			Files.createTempFile()
			//java.io.File file=java.io.File.createTempFile(oPrefix.stringValue(),
			//		oSuffix.stringValue(), new java.io.File(oDirectory.stringValue()));
			//return new PsyName(file.getPath());
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}
	*/

	/**
	*	Deletes a file or empty directory with a given name.
	*
	*	@param oFileName a Ψ-{@code stringy} representing the name of a file or
	*	directory.
	*
	*	@throws PsyDirectoryNotEmptyException when the directory is not empty.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static void psyDeleteFile(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final DirectoryNotEmptyException e)
		{
			throw new PsyDirectoryNotEmptyException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Copies a file to the target file.
	*
	*	@param oSourceName a Ψ-{@code stringy} representing the name of the file to copy.
	*	@param oTargetName a Ψ-{@code stringy} representing the name of the target file.
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
	public static void psyCopyFile(final PsyStringy oSourceName, final PsyStringy oTargetName)
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
		catch(final UnsupportedOperationException e)
		{
			throw new PsyUnsupportedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final DirectoryNotEmptyException e)
		{
			throw new PsyDirectoryNotEmptyException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsyFileExistsException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyName psyReadLink(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final NotLinkException e)
		{
			throw new PsyNotLinkException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static void psySymLink(final PsyStringy oFileName1, final PsyStringy oFileName2)
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
		catch(final UnsupportedOperationException e)
		{
			throw new PsyUnsupportedException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsyFileExistsException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static void psyHardLink(final PsyStringy oFileName1,
			final PsyStringy oFileName2)
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
		catch(final UnsupportedOperationException e)
		{
			throw new PsyUnsupportedException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsyFileExistsException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static void psyRenameFile(final PsyStringy oFileName1,
			final PsyStringy oFileName2)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final FileAlreadyExistsException e)
		{
			throw new PsyFileExistsException();
		}
		catch(final DirectoryNotEmptyException e)
		{
			throw new PsyDirectoryNotEmptyException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyFileExists(final PsyStringy oFileName)
		throws PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.valueOf(Files.exists(getPath(oFileName)));
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static PsyBoolean psyIsFile(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyIsDirectory(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyIsSameFile(final PsyStringy oFileName1, final PsyStringy oFileName2)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyBoolean psyIsSymLink(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Returns the Ψ-{@code integer} representing the size (in bytes) for the
	*	file or directory with the given name.
	*
	*	@param oFileName a Ψ-{@code stringy} representing the file name.
	*	@return a Ψ-{@code integer} representing the size (in bytes) of the
	*	file or directory.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static PsyInteger psyFileSize(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFileAccessTime(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFileCreationTime(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFileModifiedTime(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	Returns a Ψ-{@code name} representing the absolute name of the current
	*	directory.
	*
	*	@return a Ψ-{@code name} representing the absolute name of the current
	*	directory.
	*/
	public static PsyName psyCurrentDirectory()
	{
		return new PsyName(Paths.get("").toAbsolutePath().toString());
	}

	/**
	*	Returns a Ψ-{@code name} representing the absolute path to given file.
	*
	*	@param oFileName a Ψ-{@code name} representing file name.
	*	@return a Ψ-{@code name} representing the absolute path.
	*	directory.
	*
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static PsyName psyFileAbsolutePath(final PsyStringy oFileName)
		throws
			PsyIOErrorException
	{
		try
		{
			return new PsyName(Paths.get(oFileName.stringValue()).toAbsolutePath().toString());
		}
		catch(final java.io.IOError e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static PsyInteger psyFilePermissions(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static void psyChangeFilePermissions(final PsyStringy oFileName, final PsyInteger oPermissions)
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
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static PsyObject psyFileAttribute(final PsyStringy oFileName, final PsyStringy oAttribute)
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
		catch(final ClassCastException e)
		{
			throw new PsyTypeCheckException();
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsyUnsupportedException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyUndefinedException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static void psyChangeFileAttribute(final PsyStringy oFileName, final PsyStringy oAttribute, final PsyObject oValue)
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
		catch(final ClassCastException e)
		{
			throw new PsyTypeCheckException();
		}
		catch(final UnsupportedOperationException e)
		{
			throw new PsyUnsupportedException();
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyUndefinedException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
	}

	public static PsyStreamlike<PsyName> psyFiles(final PsyStringy oFileName)
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
		catch(final NoSuchFileException e)
		{
			throw new PsyFileNotFoundException();
		}
		catch(final NotDirectoryException e)
		{
			throw new PsyNotDirectoryException();
		}
		catch(final AccessDeniedException e)
		{
			throw new PsyFileAccessDeniedException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
		catch(final SecurityException e)
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
		if(o instanceof PsyStringy)
			return ((PsyStringy)o).stringValue();
		else if(o instanceof PsyInteger)
			return ((PsyInteger)o).longValue();
		else if(o instanceof PsyBoolean)
			return ((PsyBoolean)o).booleanValue();
		else
			throw new ClassCastException();
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity20<PsyStringy, PsyInteger>("changefilepermissions", PsyFileSystem::psyChangeFilePermissions),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("copyfile", PsyFileSystem::psyCopyFile),
			new PsyOperator.Arity10<PsyStringy>("createdirectory", PsyFileSystem::psyCreateDirectory),
			new PsyOperator.Arity01("currentdirectory", PsyFileSystem::psyCurrentDirectory),
			new PsyOperator.Arity10<PsyStringy>("deletefile", PsyFileSystem::psyDeleteFile),
			new PsyOperator.Arity11<PsyStringy>("fileaccesstime", PsyFileSystem::psyFileAccessTime),
			new PsyOperator.Arity21<PsyStringy, PsyStringy>("fileattribute", PsyFileSystem::psyFileAttribute),
			new PsyOperator.Arity11<PsyStringy>("filecreationtime", PsyFileSystem::psyFileCreationTime),
			new PsyOperator.Arity11<PsyStringy>("fileexists", PsyFileSystem::psyFileExists),
			new PsyOperator.Arity11<PsyStringy>("filemodifiedtime", PsyFileSystem::psyFileModifiedTime),
			new PsyOperator.Arity11<PsyStringy>("filepermissions", PsyFileSystem::psyFilePermissions),
			new PsyOperator.Arity11<PsyStringy>("files", PsyFileSystem::psyFiles),
			new PsyOperator.Arity11<PsyStringy>("filesize", PsyFileSystem::psyFileSize),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("hardlink", PsyFileSystem::psyHardLink),
			new PsyOperator.Arity11<PsyStringy>("isdirectory", PsyFileSystem::psyIsDirectory),
			new PsyOperator.Arity11<PsyStringy>("isfile", PsyFileSystem::psyIsFile),
			new PsyOperator.Arity21<PsyStringy, PsyStringy>("issamefile", PsyFileSystem::psyIsSameFile),
			new PsyOperator.Arity11<PsyStringy>("issymlink", PsyFileSystem::psyIsSymLink),
			new PsyOperator.Arity11<PsyStringy>("readlink", PsyFileSystem::psyReadLink),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("renamefile", PsyFileSystem::psyRenameFile),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("symlink", PsyFileSystem::psySymLink),
		};

}
