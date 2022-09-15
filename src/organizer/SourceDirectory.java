package organizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents the directory from which the files that the user wants to organize are taken.<br>
 * Once initialized, the class creates a {@link YearMonthFile} for each of the files
 * it contains.
 */
final class SourceDirectory extends Directory {

	/**
	 * Static factory method that creates and returns a {@link SrcDirectory} from a given path.
	 *
	 * @param dirPath - Path to a directory.
	 * 
	 * @return New SrcDirectory object.
	 */
	static SourceDirectory createFromDir(String dirPath) {
		return new SourceDirectory(dirPath);
	}

	/**
	 * Getter that returns a {@link List} of all the files contained in the {@link SourceDirectory} as
	 * {@link YearMonthFile} objects.
	 *
	 * @return List of files.
	 */
	List<YearMonthFile> getFiles() {
		return Arrays.asList(dir.listFiles())
					 .parallelStream()
					 .map(File::getPath)
					 .map(path -> {
						 	try {
						 		return YearMonthFile.createFromPath(path);
						 	} catch (FileNotFoundException e) {
						 		System.err.println("Ensure that the directory only contains files.");
						 		return null;}})
					 .collect(Collectors.toList());
	}

	private SourceDirectory(String path) {
		super(path);
	}

}