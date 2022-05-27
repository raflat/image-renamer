package organizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class YMFileDirectoryTest {
	
	@ClassRule
	public static final TemporaryFolder dir = new TemporaryFolder();

	private static List<YMFile> testFiles = new LinkedList<>();
	private static final int NUMBER_OF_FILES = 10;
	private static final String INVALID_PATH = "invalid path";
																						
	@BeforeClass
	public static void init() throws FileNotFoundException, IOException {
		try {
			for (int i = 0; i < NUMBER_OF_FILES; i++) {
				File file = dir.newFile();
				testFiles.add(YMFile.createFromPath(file.getPath()));
			}
		} catch (IOException e) {
			 System.err.println("Error creating temporary test files.");
		}
	}
	
	@Test
	public void testCreateFromDirWithValidPath() throws NotDirectoryException {
		assertThat(YMFileDirectory.createFromDir(dir.getRoot().getPath()))
			.isInstanceOf(YMFileDirectory.class);
	}
	
	@Test
	public void testCreateFromDirWithInvalidPath() throws NotDirectoryException {
		assertThatThrownBy(() -> YMFileDirectory.createFromDir(INVALID_PATH))
			.isInstanceOf(NotDirectoryException.class)
			.hasMessage(INVALID_PATH);
	}
	
	@Test
	public void testListFiles() {
		YMFileDirectory testDir = null;
		try {
			testDir = YMFileDirectory.createFromDir(dir.getRoot().getPath());
		} catch (NotDirectoryException e) {
			System.err.println("Error creating temporary test directory.");
		}
		List<String> filePaths = testFiles.parallelStream()
						  .map(f -> f.getFile().getPath())
						  .collect(Collectors.toList());
		
		assertThat(testDir.listFiles())
			.allMatch(f -> filePaths.contains(f.getFile().getPath()));
	}
	
	

}