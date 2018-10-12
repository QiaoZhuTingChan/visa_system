package com.jyd.bms.tool.zk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

public class TempFileUtils {
	private TempFileUtils()
    {
    }

    public static void addTempFile(File file)
    {
        List tempFiles = getTempFileContainer();
        tempFiles.add(file);
    }

    public static void addTempFile(List files)
    {
        List tempFiles = getTempFileContainer();
        tempFiles.addAll(files);
    }

    private static List getTempFileContainer()
    {
        Session session = Executions.getCurrent().getDesktop().getSession();
        List tempFiles = (List)session.getAttribute("_TEMP_FILES");
        if(tempFiles == null)
        {
            tempFiles = new ArrayList();
            session.setAttribute("_TEMP_FILES", tempFiles);
        }
        return tempFiles;
    }
}
