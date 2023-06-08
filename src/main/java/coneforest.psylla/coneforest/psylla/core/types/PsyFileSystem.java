package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyDirectoryNotEmpty;
import coneforest.psylla.core.errors.PsyFileAccessDenied;
import coneforest.psylla.core.errors.PsyFileExists;
import coneforest.psylla.core.errors.PsyFileNotFound;
import coneforest.psylla.core.errors.PsyIOError;
import coneforest.psylla.core.errors.PsyNotDirectory;
import coneforest.psylla.core.errors.PsyNotLink;
import coneforest.psylla.core.errors.PsySecurityError;
import coneforest.psylla.core.errors.PsyTypeCheck;
import coneforest.psylla.core.errors.PsyUndefined;
import coneforest.psylla.core.errors.PsyUnsupported;
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
	*	@throws PsyFileExists when the directory already exists.
	*	@throws PsyFileAccessDenied when the operation is prohibited
	*	due to a file permission or other access check.
	*	@throws PsySecurityError when security policy is violated.
	*	@throws PsyIOError when an input/output error occurs.
	*/
	public static void psyCreateDirectory(final PsyTextual oFileName)
		throws
			PsyFileExists,
			PsyFileAccessDenied,
			PsySecurityError,
			PsyIOError
	{
		try
		{
			Files.createDirectory(getPath(oFileName));
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExists();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	/*
	public static PsyName psyCreateTempFile(final PsyTextual oPrefix,
			final PsyTextual oSuffix, PsyTextual oDirectory)
		throws PsyError
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
			throw new PsySecurityError();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}
	*/

	/**
	*	Deletes a file or empty directory with a given name.
	*
	*	@param oFileName a {@code textual} representing the name of a file or
	*	directory.
	*
	*	@throws PsyDirectoryNotEmpty when the directory is not empty.
	*	@throws PsyFileNotFound when the file or directory does not exist.
	*	@throws PsyFileAccessDenied when the operation is prohibited due to a file permission or
	*	other access check.
	*	@throws PsySecurityError when security policy is violated.
	*	@throws PsyIOError when an input/output error occurs.
	*/
	public static void psyDeleteFile(final PsyTextual oFileName)
		throws
			PsyFileNotFound,
			PsyFileAccessDenied,
			PsyDirectoryNotEmpty,
			PsySecurityError,
			PsyIOError
	{
		try
		{
			Files.delete(getPath(oFileName));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmpty();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	/**
	*	Copies a file to the target file.
	*
	*	@param oSourceName a {@code textual} representing the name of the file to copy.
	*	@param oTargetName a {@code textual} representing the name of the target file.
	*
	*	@throws PsyDirectoryNotEmpty
	*	@throws PsyFileExists
	*	@throws PsyFileNotFound when the file or directory does not exist.
	*	@throws PsyFileAccessDenied when the operation is prohibited due to a file permission or
	*	other access check.
	*	@throws PsySecurityError when security policy is violated.
	*	@throws PsyIOError when an input/output error occurs.
	*	@throws PsyUnsupported
	*/
	public static void psyCopyFile(final PsyTextual oSourceName, final PsyTextual oTargetName)
		throws
			PsyDirectoryNotEmpty,
			PsyFileAccessDenied,
			PsyFileExists,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError,
			PsyUnsupported
	{
		try
		{
			Files.copy(getPath(oSourceName), getPath(oTargetName));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupported();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmpty();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExists();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyName psyReadLink(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsyNotLink,
			PsySecurityError
	{
		try
		{
			return new PsyName(Files.readSymbolicLink(getPath(oFileName)).toString());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final NotLinkException ex)
		{
			throw new PsyNotLink();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static void psySymLink(final PsyTextual oFileName1, final PsyTextual oFileName2)
		throws
			PsyFileAccessDenied,
			PsyFileExists,
			PsyIOError,
			PsySecurityError,
			PsyUnsupported
	{
		try
		{
			Files.createSymbolicLink(getPath(oFileName2),
				getPath(oFileName1));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupported();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExists();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static void psyHardLink(final PsyTextual oFileName1,
			final PsyTextual oFileName2)
		throws
			PsyFileAccessDenied,
			PsyFileExists,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError,
			PsyUnsupported
	{
		try
		{
			Files.createLink(getPath(oFileName2), getPath(oFileName1));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupported();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExists();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static void psyRenameFile(final PsyTextual oFileName1,
			final PsyTextual oFileName2)
		throws
			PsyDirectoryNotEmpty,
			PsyFileAccessDenied,
			PsyFileExists,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			Files.move(getPath(oFileName1), getPath(oFileName2));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExists();
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmpty();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyBoolean psyFileExists(final PsyTextual oFileName)
		throws PsySecurityError
	{
		try
		{
			return PsyBoolean.of(Files.exists(getPath(oFileName)));
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
	}

	public static PsyBoolean psyIsFile(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyBoolean.of(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isRegularFile());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyBoolean psyIsDirectory(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyBoolean.of(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isDirectory());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyBoolean psyIsSameFile(final PsyTextual oFileName1, final PsyTextual oFileName2)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyBoolean.of(Files.isSameFile(
					getPath(oFileName1), getPath(oFileName2)));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyBoolean psyIsSymLink(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyBoolean.of(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isSymbolicLink());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	/**
	*	Returns the {@code integer} representing the size (in bytes) for the file or directory with
	*	the given name.
	*
	*	@param oFileName a {@code textual} representing the file name.
	*
	*	@return a {@code integer} representing the size (in bytes) of the file or directory.
	*
	*	@throws PsyFileAccessDenied when the operation is prohibited due to a file permission or
	*	other access check.
	*	@throws PsyFileNotFound when the file or directory does not exist.
	*	@throws PsySecurityError when security policy is violated.
	*	@throws PsyIOError when an input/output error occurs.
	*/
	public static PsyInteger psyFileSize(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyInteger.of(Files.size(getPath(oFileName)));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyInteger psyFileAccessTime(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyInteger.of(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).lastAccessTime().toMillis());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyInteger psyFileCreationTime(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyInteger.of(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).creationTime().toMillis());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyInteger psyFileModifiedTime(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			return PsyInteger.of(Files.readAttributes(getPath(oFileName),
					BasicFileAttributes.class).lastModifiedTime().toMillis());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
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
	*	@throws PsyIOError when an input/output error occurs.
	*/
	public static PsyName psyFileAbsolutePath(final PsyTextual oFileName)
		throws
			PsyIOError
	{
		try
		{
			return new PsyName(Paths.get(oFileName.stringValue()).toAbsolutePath().toString());
		}
		catch(final java.io.IOError ex)
		{
			throw new PsyIOError();
		}
	}

	public static PsyInteger psyFilePermissions(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
	{
		try
		{
			final java.util.Set<PosixFilePermission> permSet
				=Files.getPosixFilePermissions(getPath(oFileName));
			long permissions=0L;
			for(final var p: permSet)
				permissions|=(256L>>p.ordinal());

			return PsyInteger.of(permissions);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
	}

	public static void psyChangeFilePermissions(final PsyTextual oFileName, final PsyInteger oPermissions)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError
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
			throw new PsyFileAccessDenied();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
	}

	public static PsyObject psyFileAttribute(final PsyTextual oFileName, final PsyTextual oAttribute)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError,
			PsyTypeCheck,
			PsyUndefined,
			PsyUnsupported
	{
		try
		{
			return toPsyObject(Files.getAttribute(getPath(oFileName),
					oAttribute.stringValue(), LinkOption.NOFOLLOW_LINKS));
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheck();
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupported();
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefined();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
	}

	public static void psyChangeFileAttribute(final PsyTextual oFileName, final PsyTextual oAttribute, final PsyObject oValue)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsySecurityError,
			PsyTypeCheck,
			PsyUndefined,
			PsyUnsupported
	{
		try
		{
			Files.setAttribute(getPath(oFileName),
					oAttribute.stringValue(), fromPsyObject(oValue),
					LinkOption.NOFOLLOW_LINKS);
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheck();
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupported();
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefined();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
	}

	public static PsyFormalStream<PsyName> psyFiles(final PsyTextual oFileName)
		throws
			PsyFileAccessDenied,
			PsyFileNotFound,
			PsyIOError,
			PsyNotDirectory,
			PsySecurityError
	{
		try
		{
			return new PsyStream(Files.list(getPath(oFileName)).<PsyName>map(
				(final var path)->new PsyName(path.toString())));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFound();
		}
		catch(final NotDirectoryException ex)
		{
			throw new PsyNotDirectory();
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDenied();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
	}

	private static PsyObject toPsyObject(final Object obj)
	{
		//System.out.println(obj.getClass());
		if(obj instanceof String stringobj)
			return new PsyName(stringobj);
		if(obj instanceof Integer integerobj)
			return PsyInteger.of(integerobj.longValue());
		if(obj instanceof Long longobj)
			return PsyInteger.of(longobj.longValue());
		if(obj instanceof Boolean booleanobj)
			return PsyBoolean.of(booleanobj);
		throw new ClassCastException();
	}

	private static Object fromPsyObject(final PsyObject o)
	{
		if(o instanceof PsyTextual oTextual)
			return oTextual.stringValue();
		if(o instanceof PsyInteger oInteger)
			return oInteger.longValue();
		if(o instanceof PsyBoolean oBoolean)
			return oBoolean.booleanValue();
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
