/*
 * Copyright (c) 2012-2013 NetEase, Inc. and other contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ucsmy.pos.utils;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

/**
 * CPU信息工具类
 * 
 * @author xieat
 * 
 */
public class CpuInfo {

	private static final String LOG_TAG = CpuInfo.class.getSimpleName();

	private long processCpu;

	private long idleCpu;

	private long totalCpu;

	private int pid;

	private long totalCpu2;

	private long processCpu2;

	private long idleCpu2;

	private String processCpuRatio = "";

	private String totalCpuRatio = "";

	public CpuInfo(){}
	public CpuInfo(Context context, int pid) {
		this.pid = pid;
	}

	/**
	 * 计算CPU信息
	 */
	public void calCpuRatioInfo() {

		DecimalFormat fomart = new DecimalFormat();
		// fomart.setGroupingUsed(false);
		fomart.setMaximumFractionDigits( 2 );
		fomart.setMinimumFractionDigits( 2 );

		readCpuStat();
		processCpuRatio = fomart.format( 100 * ((double)(processCpu - processCpu2) / ((double)(totalCpu - totalCpu2))) );
		totalCpuRatio = fomart
		        .format( 100 * ((double)((totalCpu - idleCpu) - (totalCpu2 - idleCpu2)) / (double)(totalCpu - totalCpu2)) );

		totalCpu2 = totalCpu;
		processCpu2 = processCpu;
		idleCpu2 = idleCpu;
	}

	/**
	 * read the status of CPU.
	 * 
	 * @throws FileNotFoundException
	 */
	private void readCpuStat() {
		String processPid = Integer.toString( pid );
		String cpuStatPath = "/proc/" + processPid + "/stat";
		try {
			// monitor cpu stat of certain process
			RandomAccessFile processCpuInfo = new RandomAccessFile( cpuStatPath, "r" );
			String line = "";
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.setLength( 0 );
			while( (line = processCpuInfo.readLine()) != null ) {
				stringBuffer.append( line + "\n" );
			}
			String[] tok = stringBuffer.toString().split( " " );
			processCpu = Long.parseLong( tok[13] ) + Long.parseLong( tok[14] );
			processCpuInfo.close();
		}
		catch( FileNotFoundException e ) {
			Log.e( LOG_TAG, "FileNotFoundException: " + e.getMessage() );
			e.printStackTrace();
		}
		catch( IOException e ) {
			e.printStackTrace();
		}

		try {
			// monitor total and idle cpu stat of certain process
			RandomAccessFile cpuInfo = new RandomAccessFile( "/proc/stat", "r" );
			String[] toks = cpuInfo.readLine().split( "\\s+" );
			idleCpu = Long.parseLong( toks[4] );
			totalCpu = Long.parseLong( toks[1] ) + Long.parseLong( toks[2] ) + Long.parseLong( toks[3] ) + Long.parseLong( toks[4] )
			        + Long.parseLong( toks[6] ) + Long.parseLong( toks[5] ) + Long.parseLong( toks[7] );
			cpuInfo.close();
		}
		catch( FileNotFoundException e ) {
			e.printStackTrace();
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * get CPU name.
	 * 
	 * @return CPU name
	 */
	public String getCpuName() {
		try {
			RandomAccessFile cpuStat = new RandomAccessFile( "/proc/cpuinfo", "r" );
			String[] cpu = cpuStat.readLine().split( ":" );
			cpuStat.close();
			return cpu[1];
		}
		catch( IOException e ) {
			Log.e( LOG_TAG, "IOException: " + e.getMessage() );
		}
		return "";
	}

	public String getProcessCpuRatio() {
		return processCpuRatio;
	}

	public String getTotalCpuRatio() {
		return totalCpuRatio;
	}

}
