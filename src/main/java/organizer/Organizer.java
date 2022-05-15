package organizer;

import java.util.LinkedList;

import dirchooser.DirChooser;

/**
 * Class that organizes the files from the source {@link OFileDirectory}
 * in the destination {@link StoreDirectory}.<br>
 * The files are organized in two level of directories: a first one
 * consisting of directories denoting the year of the file, the
 * second one composed of directories indicating the month of the file.
 * Furthermore, the files are enumerated from 0 to n, according to how many
 * of them are contained in the directory.
 * 
 * @author raflat
 */
public final class Organizer {
	
	private OFileDirectory srcDir;
	private StoreDirectory destDir;
	
	public static void run() {
		Organizer organizer = new Organizer();
		DirChooser.chooseAndOrganizeWith(organizer);
	}
	
	public void setSrcDir(String srcDirPath) {
		srcDir = OFileDirectory.createFromDir(srcDirPath);
	}
	
	public void setDestDir(String destDirPath) {
		destDir = StoreDirectory.createFromDir(destDirPath);
	}
	
	public void renameFiles() {
		LinkedList<String> alreadyPresentDates = destDir.containedDates();
		
		for (OFile image : srcDir.listOFiles()) {
			String imageYear = image.extractYear();
			String imageMonth = image.extractMonth();
			String imageName = imageYear + "-" + imageMonth;
					
			if (!alreadyPresentDates.contains(imageName))
				destDir.createDirectory(imageYear, imageMonth);
			
			imageName += "_" + destDir.lastImageNumber(imageYear, imageMonth);
			
			destDir.storeImage(image, imageName, imageYear, imageMonth);
		}
	}
	
}
