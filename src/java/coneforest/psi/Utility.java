package coneforest.psi;

public class Utility
{
	public static String fileNameToNative(String fileName)
	{
		return java.io.File.separatorChar=='/'? fileName: fileName.replace('/', java.io.File.separatorChar);
	}

	public static String fileNameFromNative(String fileNameNative)
	{
		return java.io.File.separatorChar=='/'? fileNameNative: fileNameNative.replace(java.io.File.separatorChar, '/');
	}
}
