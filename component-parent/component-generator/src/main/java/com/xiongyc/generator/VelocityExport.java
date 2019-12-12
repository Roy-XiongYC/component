package com.xiongyc.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityExport
{

	public static boolean export(String filePath, String fileName, Map<String, Object> context, String templateName)
	{

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

		ve.init();

		Template t = ve.getTemplate(templateName, "UTF-8");
		VelocityContext ctx = new VelocityContext();

		if (null != context && !context.isEmpty())
		{
			for (Entry<String, Object> row : context.entrySet())
			{
				ctx.put(row.getKey(), row.getValue());
			}
		}

		Writer sw = null;
		try
		{
			File exportFile = new File(filePath + "" + fileName);
			if (exportFile.exists())
			{
				exportFile.delete();
			}
			exportFile.createNewFile();
			sw = new FileWriter(exportFile);
			t.merge(ctx, sw);
			sw.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (sw != null)
			{
				try
				{
					sw.flush();
					sw.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}

		return true;

	}

}
