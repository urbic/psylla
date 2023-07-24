package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.IOError;
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
	*	@param oFileName a {@code textual} representing the name of the directory being created.
	*	@throws PsyFileExistsException when the directory already exists.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file
	*	permission or other access check.
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
		throws PsyIOErrorException, PsySecurityErrorException
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
	*	@param oFileName a {@code textual} representing the name of a file or directory.
	*	@throws PsyDirectoryNotEmptyException when the directory is not empty.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file
	*	permission or other access check.
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
	*	@throws PsyDirectoryNotEmptyException TODO
	*	@throws PsyFileExistsException TODO
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file permission or
	*	other access check.
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

	public static void psySymlink(final PsyTextual oFileName1, final PsyTextual oFileName2)
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

	public static void psyHardlink(final PsyTextual oFileName1,
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

	public static void psyRenameFile(final PsyTextual oFileName1, final PsyTextual oFileName2)
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
			return PsyBoolean.of(Files.exists(getPath(oFileName)));
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
			return PsyBoolean.of(Files.readAttributes(getPath(oFileName),
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
			return PsyBoolean.of(Files.readAttributes(getPath(oFileName),
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
			return PsyBoolean.of(Files.isSameFile(
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

	public static PsyBoolean psyIsSymlink(final PsyTextual oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.of(Files.readAttributes(getPath(oFileName),
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
	*	Returns the {@code integer} representing the size (in bytes) for the file or directory with
	*	the given name.
	*
	*	@param oFileName a {@code textual} representing the file name.
	*	@return a {@code integer} representing the size (in bytes) of the file or directory.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file permission or
	*	other access check.
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
			return PsyInteger.of(Files.size(getPath(oFileName)));
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
			return PsyInteger.of(Files.readAttributes(getPath(oFileName),
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
			return PsyInteger.of(Files.readAttributes(getPath(oFileName),
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
			return PsyInteger.of(Files.readAttributes(getPath(oFileName),
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
	*	Returns a {@code name} representing the absolute name of the current directory.
	*
	*	@return a {@code name} representing the absolute name of the current directory.
	*/
	public static PsyName psyCurrentDirectory()
	{
		return new PsyName(Paths.get("").toAbsolutePath().toString());
	}

	/**
	*	Returns a {@code name} representing the absolute path to given file.
	*
	*	@param oFileName a {@code name} representing file name.
	*	@return a {@code name} representing the absolute path. directory.
	*	@throws PsyIOErrorException when an input/output error occurs.
	*/
	public static PsyName psyFileAbsolutePath(final PsyTextual oFileName)
		throws PsyIOErrorException
	{
		try
		{
			return new PsyName(Paths.get(oFileName.stringValue()).toAbsolutePath().toString());
		}
		catch(final IOError ex)
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

			return PsyInteger.of(permissions);
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
			return toPsyTObject(Files.getAttribute(getPath(oFileName),
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
					oAttribute.stringValue(), fromPsyTObject(oValue),
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

	private static PsyObject toPsyTObject(final Object obj)
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

	private static Object fromPsyTObject(final PsyObject o)
	{
		if(o instanceof PsyTextual oTextual)
			return oTextual.stringValue();
		if(o instanceof PsyInteger oInteger)
			return oInteger.longValue();
		if(o instanceof PsyBoolean oBoolean)
			return oBoolean.booleanValue();
		throw new ClassCastException();
	}

	/**
	*	Context action of the {@code changefilepermissions} operator.
	*/
	@OperatorType("changefilepermissions")
	public static final ContextAction PSY_CHANGEFILEPERMISSIONS
		=ContextAction.<PsyTextual, PsyInteger>ofBiConsumer(PsyFileSystem::psyChangeFilePermissions);

	/**
	*	Context action of the {@code copyfile} operator.
	*/
	@OperatorType("copyfile")
	public static final ContextAction PSY_COPYFILE
		=ContextAction.<PsyTextual, PsyTextual>ofBiConsumer(PsyFileSystem::psyCopyFile);

	/**
	*	Context action of the {@code createdirectory} operator.
	*/
	@OperatorType("createdirectory")
	public static final ContextAction PSY_CREATEDIRECTORY
		=ContextAction.<PsyTextual>ofConsumer(PsyFileSystem::psyCreateDirectory);

	/**
	*	Context action of the {@code currentdirectory} operator.
	*/
	@OperatorType("currentdirectory")
	public static final ContextAction PSY_CURRENTDIRECTORY
		=ContextAction.ofSupplier(PsyFileSystem::psyCurrentDirectory);

	/**
	*	Context action of the {@code deletefile} operator.
	*/
	@OperatorType("deletefile")
	public static final ContextAction PSY_DELETEFILE
		=ContextAction.<PsyTextual>ofConsumer(PsyFileSystem::psyDeleteFile);

	/**
	*	Context action of the {@code fileaccesstime} operator.
	*/
	@OperatorType("fileaccesstime")
	public static final ContextAction PSY_FILEACCESSTIME
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFileAccessTime);

	/**
	*	Context action of the {@code fileattribute} operator.
	*/
	@OperatorType("fileattribute")
	public static final ContextAction PSY_FILEATTRIBUTE
		=ContextAction.<PsyTextual, PsyTextual>ofBiFunction(PsyFileSystem::psyFileAttribute);

	/**
	*	Context action of the {@code filecreationtime} operator.
	*/
	@OperatorType("filecreationtime")
	public static final ContextAction PSY_FILECREATIONTIME
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFileCreationTime);

	/**
	*	Context action of the {@code fileexists} operator.
	*/
	@OperatorType("fileexists")
	public static final ContextAction PSY_FILEEXISTS
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFileExists);

	/**
	*	Context action of the {@code filemodifiedtime} operator.
	*/
	@OperatorType("filemodifiedtime")
	public static final ContextAction PSY_FILEMODIFIEDTIME
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFileModifiedTime);

	/**
	*	Context action of the {@code filepermissions} operator.
	*/
	@OperatorType("filepermissions")
	public static final ContextAction PSY_FILEPERMISSIONS
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFilePermissions);

	/**
	*	Context action of the {@code files} operator.
	*/
	@OperatorType("files")
	public static final ContextAction PSY_FILES
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFiles);

	/**
	*	Context action of the {@code filesize} operator.
	*/
	@OperatorType("filesize")
	public static final ContextAction PSY_FILESIZE
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyFileSize);

	/**
	*	Context action of the {@code hardlink} operator.
	*/
	@OperatorType("hardlink")
	public static final ContextAction PSY_HARDLINK
		=ContextAction.<PsyTextual, PsyTextual>ofBiConsumer(PsyFileSystem::psyHardlink);

	/**
	*	Context action of the {@code isdirectory} operator.
	*/
	@OperatorType("isdirectory")
	public static final ContextAction PSY_ISDIRECTORY
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyIsDirectory);

	/**
	*	Context action of the {@code isfile} operator.
	*/
	@OperatorType("isfile")
	public static final ContextAction PSY_ISFILE
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyIsFile);

	/**
	*	Context action of the {@code issamefile} operator.
	*/
	@OperatorType("issamefile")
	public static final ContextAction PSY_ISSAMEFILE
		=ContextAction.<PsyTextual, PsyTextual>ofBiFunction(PsyFileSystem::psyIsSameFile);

	/**
	*	Context action of the {@code issymlink} operator.
	*/
	@OperatorType("issymlink")
	public static final ContextAction PSY_ISSYMLINK
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyIsSymlink);

	/**
	*	Context action of the {@code readlink} operator.
	*/
	@OperatorType("readlink")
	public static final ContextAction PSY_READLINK
		=ContextAction.<PsyTextual>ofFunction(PsyFileSystem::psyReadLink);

	/**
	*	Context action of the {@code renamefile} operator.
	*/
	@OperatorType("renamefile")
	public static final ContextAction PSY_RENAMEFILE
		=ContextAction.<PsyTextual, PsyTextual>ofBiConsumer(PsyFileSystem::psyRenameFile);

	/**
	*	Context action of the {@code symlink} operator.
	*/
	@OperatorType("symlink")
	public static final ContextAction PSY_SYMLINK
		=ContextAction.<PsyTextual, PsyTextual>ofBiConsumer(PsyFileSystem::psySymlink);
}
