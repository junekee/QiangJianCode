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
package org.sf.feeling.swt.win32.extension.example.page;

import java.text.NumberFormat;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.extension.system.MemoryStatus;
import org.sf.feeling.swt.win32.extension.system.SystemInfo;

public class SystemPage extends SimpleTabPage
{

	public void buildUI(Composite parent)
	{
		super.buildUI(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		layout.verticalSpacing = 20;
		container.getBody().setLayout(layout);

		createTitle();
		createProcessorInfo();
		createMemoryInfo();
	}

	private void createTitle()
	{
		WidgetUtil
				.createFormText(
						container.getBody(),
						"This page displays information about your processor using ProcessorInfo class and memory information using MemoryStatus class.",
						false, false);
	}

	private void createMemoryInfo()
	{
		TableWrapData td;
		Section memorySection = WidgetUtil.getToolkit().createSection(container.getBody(),
				Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		memorySection.setLayoutData(td);
		memorySection.setText("Memory Information:");
		WidgetUtil.getToolkit().createCompositeSeparator(memorySection);
		Composite memoryClient = WidgetUtil.getToolkit().createComposite(memorySection);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		memoryClient.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		Label label = WidgetUtil.createLabel(memoryClient);
		label.setText("Memory Total:");
		GridData gd1 = new GridData();
		gd1.widthHint = 160;
		label.setLayoutData(gd1);

		Label totalLabel = WidgetUtil.createLabel(memoryClient);
		totalLabel.setText(MemoryStatus.getInstance().getTotalPhys() / (1024) + " KB");
		totalLabel.setLayoutData(gd);

		WidgetUtil.createLabel(memoryClient).setText("Memory Available:");
		final Label memoryLabel = WidgetUtil.createLabel(memoryClient);
		memoryLabel.setLayoutData(gd);
		Thread memoryThread = new Thread()
		{
			public void run()
			{
				while (!Example.DIRTY)
				{
					if (Display.getDefault() != null) Display.getDefault().asyncExec(
							new Runnable()
							{
								public void run()
								{
									if (!memoryLabel.isDisposed())
									{
										MemoryStatus.getInstance().refreshStatus();
										memoryLabel.setText(MemoryStatus.getInstance()
												.getAvailPhys()
												/ (1024) + " KB");
									}
								}
							});
					try
					{
						Thread.sleep(1000);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		};

		memoryThread.start();
		memorySection.setClient(memoryClient);
	}

	private void createProcessorInfo()
	{
		TableWrapData td;
		Section processorSection = WidgetUtil.getToolkit().createSection(container.getBody(),
				Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		processorSection.setLayoutData(td);
		processorSection.setText("Processor Information:");
		WidgetUtil.getToolkit().createCompositeSeparator(processorSection);
		Composite processorClient = WidgetUtil.getToolkit().createComposite(processorSection);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		processorClient.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		Label label = WidgetUtil.createLabel(processorClient);
		label.setText("Processor Type:");
		GridData gd1 = new GridData();
		gd1.widthHint = 160;
		label.setLayoutData(gd1);

		Label typeLabel = WidgetUtil.createLabel(processorClient);
		typeLabel.setText("" + SystemInfo.getInstance().getProcessorType());
		typeLabel.setLayoutData(gd);

		WidgetUtil.createLabel(processorClient).setText("Processor Number:");
		Label numberLabel = WidgetUtil.createLabel(processorClient);
		numberLabel.setText("" + SystemInfo.getInstance().getNumberOfProcessors());
		numberLabel.setLayoutData(gd);

		WidgetUtil.createLabel(processorClient).setText("Processor PageSize:");
		Label pageSizeLabel = WidgetUtil.createLabel(processorClient);
		pageSizeLabel.setText(format(SystemInfo.getInstance().getPageSize()));
		pageSizeLabel.setLayoutData(gd);

		WidgetUtil.createLabel(processorClient).setText("Processor Usages:");
		final Label cpuLabel = WidgetUtil.createLabel(processorClient);
		cpuLabel.setLayoutData(gd);
		Thread processorThread = new Thread()
		{
			public void run()
			{
				while (!Example.DIRTY)
				{
					if (Display.getDefault() != null) Display.getDefault().asyncExec(
							new Runnable()
							{
								public void run()
								{
									if (!cpuLabel.isDisposed()) cpuLabel.setText(""
											+ SystemInfo.getCpuUsages() + " %");
								}
							});
					try
					{
						Thread.sleep(1000);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		processorThread.start();

		RegistryKey key = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE,
				"HARDWARE\\DESCRIPTION\\SYSTEM\\CentralProcessor\\0");
		if (key.exists() && key.hasValue("VendorIdentifier"))
		{
			WidgetUtil.createLabel(processorClient).setText("Processor Vendor:");
			Label vendorLabel = WidgetUtil.createLabel(processorClient);
			vendorLabel.setText(key.getValue("VendorIdentifier").getData().toString().trim());
			vendorLabel.setLayoutData(gd);
		}
		if (key.exists() && key.hasValue("ProcessorNameString"))
		{
			WidgetUtil.createLabel(processorClient).setText("Processor Name:");
			Label nameLabel = WidgetUtil.createLabel(processorClient);
			nameLabel.setText(key.getValue("ProcessorNameString").getData().toString().trim());
			nameLabel.setLayoutData(gd);
		}
		if (key.exists() && key.hasValue("Identifier"))
		{
			WidgetUtil.createLabel(processorClient).setText("Processor Identifier:");
			Label identifierLabel = WidgetUtil.createLabel(processorClient);
			identifierLabel.setText(key.getValue("Identifier").getData().toString().trim());
			identifierLabel.setLayoutData(gd);
		}
		if (key.exists() && key.hasValue("~Mhz"))
		{
			WidgetUtil.createLabel(processorClient).setText("Processor Frequency:");
			Label frequenceLabel = WidgetUtil.createLabel(processorClient);
			frequenceLabel.setText(key.getValue("~Mhz").getData().toString().trim() + " MHz");
			frequenceLabel.setLayoutData(gd);
		}

		WidgetUtil.createLabel(processorClient).setText("Processor ID:");
		Label cpuIdLabel = WidgetUtil.createLabel(processorClient);
		cpuIdLabel.setText(SystemInfo.getCPUID());
		cpuIdLabel.setLayoutData(gd);

		processorSection.setClient(processorClient);
	}

	public String getDisplayName()
	{
		return "System Info Example";
	}

	private String format(float totalNumberOfFreeBytes)
	{
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(1);
		format.setMinimumFractionDigits(0);
		if (totalNumberOfFreeBytes < 1024) return format.format(totalNumberOfFreeBytes)
				+ " byte";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if (totalNumberOfFreeBytes < 1024) return format.format(totalNumberOfFreeBytes)
				+ " KB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if (totalNumberOfFreeBytes < 1024) return format.format(totalNumberOfFreeBytes)
				+ " MB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if (totalNumberOfFreeBytes < 1024) return format.format(totalNumberOfFreeBytes)
				+ " GB";
		return null;
	}
}
