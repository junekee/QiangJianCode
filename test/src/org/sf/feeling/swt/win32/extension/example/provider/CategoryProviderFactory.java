/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.example.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sf.feeling.swt.win32.extension.example.page.DesktopPage;
import org.sf.feeling.swt.win32.extension.example.page.FilePage;
import org.sf.feeling.swt.win32.extension.example.page.HookPage;
import org.sf.feeling.swt.win32.extension.example.page.IconPage;
import org.sf.feeling.swt.win32.extension.example.page.LowLevelHookPage;
import org.sf.feeling.swt.win32.extension.example.page.OlePage;
import org.sf.feeling.swt.win32.extension.example.page.RegistryPage;
import org.sf.feeling.swt.win32.extension.example.page.ShellFolderPage;
import org.sf.feeling.swt.win32.extension.example.page.ShellLinkPage;
import org.sf.feeling.swt.win32.extension.example.page.SoundPage;
import org.sf.feeling.swt.win32.extension.example.page.SystemPage;
import org.sf.feeling.swt.win32.extension.example.page.WebPage;
import org.sf.feeling.swt.win32.extension.example.page.WindowPage;
import org.sf.feeling.swt.win32.extension.ole.flash.Flash;
import org.sf.feeling.swt.win32.extension.system.OSVersionInfo;

public class CategoryProviderFactory 
{

	private static CategoryProviderFactory instance = new CategoryProviderFactory( );

	protected CategoryProviderFactory( )
	{
	}

	/**
	 * 
	 * @return The unique CategoryProviderFactory instance
	 */
	public static CategoryProviderFactory getInstance( )
	{
		return instance;
	}

	private String[] getCategoryNames(){
		String[] names = new String[]{
				"System Info",
				"File System",
				"Change Icon",
				"Window Decorations",
				"Shell Folders",
				"Shell Links",
				"Active Desktop",
				"Hooks",
				"Registry",
				"Project Website" };
		List list = new ArrayList();
		list.addAll(Arrays.asList(names));
		if (OSVersionInfo.getInstance().getMajor() < 6)
		{
			list.add(list.indexOf("Hooks")+1, "Low Level Hooks");
			list.add(list.indexOf("Window Decorations") + 1, "Volume Control");
		}
		if(Flash.canCreate())
			list.add(list.indexOf("Registry"), "Ole Control");
		return (String[])list.toArray(new String[0]);
	}
	
	private Class[] getCategoryClasses(){
		Class[] classes = new Class[]{
				SystemPage.class,
				FilePage.class,
				IconPage.class,
				WindowPage.class,
				ShellFolderPage.class,
				ShellLinkPage.class,
				DesktopPage.class,
				HookPage.class,
				RegistryPage.class,
				WebPage.class,
		};
		List list = new ArrayList();
		list.addAll(Arrays.asList(classes));
		if (OSVersionInfo.getInstance().getMajor() < 6)
		{
			list.add(list.indexOf(HookPage.class)+1, LowLevelHookPage.class);
			list.add(list.indexOf(WindowPage.class) + 1, SoundPage.class);
		}
		if(Flash.canCreate())
			list.add(list.indexOf(RegistryPage.class), OlePage.class);
		return (Class[])list.toArray(new Class[0]);
	}

	/**
	 * Get CategoryProvider according to input element name
	 */
	public ICategoryProvider getCategoryProvider( )
	{
			return new CategoryProvider( getCategoryNames(),getCategoryClasses()  );
		
	}
}