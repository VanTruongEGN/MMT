package ont1;

import java.io.File;

public class DeleteFile {
	public static boolean deleteFile(String src) {
	    File file = new File(src);

	    if (!file.exists()) {
	        return true;
	    }
	    if(file.isFile()) {
	    	file.delete();
	    }

	    if (file.isDirectory()) {
	        File[] files = file.listFiles();
	        if (files != null) {
	            for (File f : files) {
	                if (!deleteFile(f.getAbsolutePath())) {
	                    return false;
	                }
	            }
	        }
	    }

	    return true;
	}
	public static void main(String[] args) {
		String src = "D:\\hoc tap\\NetworkComputing\\data";
		System.out.println(deleteFile(src));
	}
}
