package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
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
import java.util.HashSet;

/**
*	An utility class providing filesystem-related methods.
*/
@Type("filesystem")
public interface PsyFileSystem
{
	/**
	*	Context action of the {@code changefilepermissions} operator.
	*/
	@OperatorType("changefilepermissions")
	public static final ContextAction PSY_CHANGEFILEPERMISSIONS
		=ContextAction.<PsyString, PsyInteger>ofBiConsumer(PsyFileSystem::psyChangeFilePermissions);

	/**
	*	Context action of the {@code copyfile} operator.
	*/
	@OperatorType("copyfile")
	public static final ContextAction PSY_COPYFILE
		=ContextAction.<PsyString, PsyString>ofBiConsumer(PsyFileSystem::psyCopyFile);

	/**
	*	Context action of the {@code createdirectory} operator.
	*/
	@OperatorType("createdirectory")
	public static final ContextAction PSY_CREATEDIRECTORY
		=ContextAction.<PsyString>ofConsumer(PsyFileSystem::psyCreateDirectory);

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
		=ContextAction.<PsyString>ofConsumer(PsyFileSystem::psyDeleteFile);

	/**
	*	Context action of the {@code fileaccesstime} operator.
	*/
	@OperatorType("fileaccesstime")
	public static final ContextAction PSY_FILEACCESSTIME
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFileAccessTime);

	/**
	*	Context action of the {@code fileabsolutepath} operator.
	*/
	@OperatorType("fileabsolutepath")
	public static final ContextAction PSY_FILEABSOLUTEPATH
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFileAbsolutePath);

	/**
	*	Context action of the {@code fileattribute} operator.
	*/
	@OperatorType("fileattribute")
	public static final ContextAction PSY_FILEATTRIBUTE
		=ContextAction.<PsyString, PsyString>ofBiFunction(PsyFileSystem::psyFileAttribute);

	/**
	*	Context action of the {@code filecreationtime} operator.
	*/
	@OperatorType("filecreationtime")
	public static final ContextAction PSY_FILECREATIONTIME
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFileCreationTime);

	/**
	*	Context action of the {@code fileexists} operator.
	*/
	@OperatorType("fileexists")
	public static final ContextAction PSY_FILEEXISTS
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFileExists);

	/**
	*	Context action of the {@code filemodifiedtime} operator.
	*/
	@OperatorType("filemodifiedtime")
	public static final ContextAction PSY_FILEMODIFIEDTIME
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFileModifiedTime);

	/**
	*	Context action of the {@code filepermissions} operator.
	*/
	@OperatorType("filepermissions")
	public static final ContextAction PSY_FILEPERMISSIONS
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFilePermissions);

	/**
	*	Context action of the {@code files} operator.
	*/
	@OperatorType("files")
	public static final ContextAction PSY_FILES
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFiles);

	/**
	*	Context action of the {@code filesize} operator.
	*/
	@OperatorType("filesize")
	public static final ContextAction PSY_FILESIZE
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyFileSize);

	/**
	*	Context action of the {@code hardlink} operator.
	*/
	@OperatorType("hardlink")
	public static final ContextAction PSY_HARDLINK
		=ContextAction.<PsyString, PsyString>ofBiConsumer(PsyFileSystem::psyHardlink);

	/**
	*	Context action of the {@code isdirectory} operator.
	*/
	@OperatorType("isdirectory")
	public static final ContextAction PSY_ISDIRECTORY
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyIsDirectory);

	/**
	*	Context action of the {@code isfile} operator.
	*/
	@OperatorType("isfile")
	public static final ContextAction PSY_ISFILE
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyIsFile);

	/**
	*	Context action of the {@code issamefile} operator.
	*/
	@OperatorType("issamefile")
	public static final ContextAction PSY_ISSAMEFILE
		=ContextAction.<PsyString, PsyString>ofBiFunction(PsyFileSystem::psyIsSameFile);

	/**
	*	Context action of the {@code issymlink} operator.
	*/
	@OperatorType("issymlink")
	public static final ContextAction PSY_ISSYMLINK
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyIsSymlink);

	/**
	*	Context action of the {@code readlink} operator.
	*/
	@OperatorType("readlink")
	public static final ContextAction PSY_READLINK
		=ContextAction.<PsyString>ofFunction(PsyFileSystem::psyReadLink);

	/**
	*	Context action of the {@code renamefile} operator.
	*/
	@OperatorType("renamefile")
	public static final ContextAction PSY_RENAMEFILE
		=ContextAction.<PsyString, PsyString>ofBiConsumer(PsyFileSystem::psyRenameFile);

	/**
	*	Context action of the {@code symlink} operator.
	*/
	@OperatorType("symlink")
	public static final ContextAction PSY_SYMLINK
		=ContextAction.<PsyString, PsyString>ofBiConsumer(PsyFileSystem::psySymlink);

	private static Path getPath(final PsyString oFileName)
	{
		return Paths.get(oFileName.stringValue());
	}

	/**
	*	Creates a new directory with the given name.
	*
	*	@param oFileName a {@code string} representing the name of the directory being created.
	*	@throws PsyFileExistsException when the directory already exists.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file
	*		permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psyCreateDirectory(final PsyString oFileName)
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
			throw new PsyFileExistsException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/*
	public static PsyString psyCreateTempFile(final PsyString oPrefix,
			final PsyString oSuffix, PsyString oDirectory)
		throws PsyIOErrorException, PsySecurityErrorException
	{
		try
		{
			Files.createTempFile()
			//java.io.File file=java.io.File.createTempFile(oPrefix.stringValue(),
			//		oSuffix.stringValue(), new java.io.File(oDirectory.stringValue()));
			//return new PsyString(file.getPath());
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
	*	@param oFileName a {@code string} representing the name of a file or directory.
	*	@throws PsyDirectoryNotEmptyException when the directory is not empty.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file
	*		permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psyDeleteFile(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmptyException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	Copies a file to the target file.
	*
	*	@param oSourceName a {@code string} name of the file to copy.
	*	@param oTargetName a {@code string} name of the target file.
	*	@throws PsyDirectoryNotEmptyException TODO
	*	@throws PsyFileExistsException TODO
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file
	*		permission or other access check.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*	@throws PsyUnsupportedException TODO
	*/
	public static void psyCopyFile(final PsyString oSourceName, final PsyString oTargetName)
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
			throw new PsyUnsupportedException(ex);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmptyException(ex);
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	{@return the target of a symbolic link}
	*
	*	@param oFileName the name of the target file.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyString psyReadLink(final PsyString oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsyNotLinkException,
			PsySecurityErrorException
	{
		try
		{
			return new PsyString(Files.readSymbolicLink(getPath(oFileName)).toString());
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final NotLinkException ex)
		{
			throw new PsyNotLinkException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	Creates a symbolic link to a target.
	*
	*	@param oTarget the target of the symbolic link.
	*	@param oLink the path of the symbolic link to create.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psySymlink(final PsyString oTarget, final PsyString oLink)
		throws
			PsyFileAccessDeniedException,
			PsyFileExistsException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyUnsupportedException
	{
		try
		{
			Files.createSymbolicLink(getPath(oLink), getPath(oTarget));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException(ex);
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	Creates a new link (directory entry) for an existing file.
	*
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psyHardlink(final PsyString oExisting, final PsyString oLink)
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
			Files.createLink(getPath(oLink), getPath(oExisting));
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException(ex);
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException(ex);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psyRenameFile(final PsyString oFileName1, final PsyString oFileName2)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final FileAlreadyExistsException ex)
		{
			throw new PsyFileExistsException(ex);
		}
		catch(final DirectoryNotEmptyException ex)
		{
			throw new PsyDirectoryNotEmptyException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	public static PsyBoolean psyFileExists(final PsyString oFileName)
		throws PsySecurityErrorException
	{
		try
		{
			return PsyBoolean.of(Files.exists(getPath(oFileName)));
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyBoolean psyIsFile(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyBoolean psyIsDirectory(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyBoolean psyIsSameFile(final PsyString oFileName1, final PsyString oFileName2)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyBoolean psyIsSymlink(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	{@return the {@code integer} representing the size (in bytes) for the file or directory with
	*		the given name}
	*
	*	@param oFileName a {@code string} representing the file name.
	*	@throws PsyFileAccessDeniedException when the operation is prohibited due to a file permission or
	*		other access check.
	*	@throws PsyFileNotFoundException when the file or directory does not exist.
	*	@throws PsySecurityErrorException when security policy is violated.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyInteger psyFileSize(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyInteger psyFileAccessTime(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyInteger psyFileCreationTime(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyInteger psyFileModifiedTime(final PsyString oFileName)
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
			throw new PsyFileNotFoundException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	{@return a {@code string} representing the absolute name of the current directory}
	*/
	public static PsyString psyCurrentDirectory()
	{
		return new PsyString(Paths.get("").toAbsolutePath().toString());
	}

	/**
	*	{@return a {@code string} representing the absolute path to given file}
	*
	*	@param oFileName a {@code string} representing file name.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyString psyFileAbsolutePath(final PsyString oFileName)
		throws PsyIOErrorException
	{
		try
		{
			return new PsyString(Paths.get(oFileName.stringValue()).toAbsolutePath().toString());
		}
		catch(final IOError ex)
		{
			throw new PsyIOErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyInteger psyFilePermissions(final PsyString oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			final var permSet=Files.getPosixFilePermissions(getPath(oFileName));
			long permissions=0L;
			for(final var p: permSet)
				permissions|=256L>>p.ordinal();
			return PsyInteger.of(permissions);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psyChangeFilePermissions(final PsyString oFileName, final PsyInteger oPermissions)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException
	{
		try
		{
			final var permissions=oPermissions.longValue();
			final var permSet=new HashSet<PosixFilePermission>();
			final var permValues=PosixFilePermission.values();
			for(int i=0; i<=8; i++)
				if((permissions&(256L>>i))!=0L)
					permSet.add(permValues[i]);
			Files.setPosixFilePermissions(getPath(oFileName), permSet);

		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyObject psyFileAttribute(final PsyString oFileName, final PsyString oAttribute)
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
			throw new PsyTypeCheckException(ex);
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException(ex);
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
	}

	/**
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static void psyChangeFileAttribute(final PsyString oFileName, final PsyString oAttribute, final PsyString oValue)
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
			throw new PsyTypeCheckException(ex);
		}
		catch(final UnsupportedOperationException ex)
		{
			throw new PsyUnsupportedException(ex);
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyUndefinedException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
	}

	/**
	*	{@return a lazily populated stream, the elements of which are the entries in the directory}
	*	The listing is not recursive.}
	*
	*	@param oFileName the name of the directory.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public static PsyFormalStream<PsyString> psyFiles(final PsyString oFileName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsyNotDirectoryException,
			PsySecurityErrorException
	{
		try
		{
			//return new PsyStream(Files.list(getPath(oFileName)).<PsyString>map(
			//	(final var path)->new PsyString(path.toString())));
			return PsyFormalStream.<PsyString>of(
					Files.list(getPath(oFileName)).<PsyString>map(
							(final var path)->new PsyString(path.toString())));
		}
		catch(final NoSuchFileException ex)
		{
			throw new PsyFileNotFoundException(ex);
		}
		catch(final NotDirectoryException ex)
		{
			throw new PsyNotDirectoryException(ex);
		}
		catch(final AccessDeniedException ex)
		{
			throw new PsyFileAccessDeniedException(ex);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException(ex);
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException(ex);
		}
	}

	private static PsyObject toPsyTObject(final Object obj)
	{
		return switch(obj)
			{
				case String stringobj->new PsyString(stringobj);
				case Integer integerobj->PsyInteger.of(integerobj.longValue());
				case Long longobj->PsyInteger.of(longobj.longValue());
				case Boolean booleanobj->PsyBoolean.of(booleanobj);
				default->throw new ClassCastException();
			};
	}

	private static Object fromPsyTObject(final PsyObject o)
	{
		return switch(o)
			{
				case PsyString oName->oName.stringValue();
				case PsyInteger oInteger->oInteger.longValue();
				case PsyBoolean oBoolean->oBoolean.booleanValue();
				default->throw new ClassCastException();
			};
	}
}
