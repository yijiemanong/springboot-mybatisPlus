package com.example.web.config;

import lombok.extern.slf4j.Slf4j;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
@Slf4j
public class Rservel {
    // R语言主程序path
	private static String R_EXE_PATH = "D:/R-3.6.1/bin/R.exe";
    /**
     * RservePath 负责使用Rserve
     * 文件内容:
     * library(Rserve)Rserve()</span>
     */
	private static String R__PATH = "D:/Rserve.R";


	public static RConnection getRConnection() throws Exception{
		try {
			return new RConnection();
		} catch (RserveException e) {
			log.warn("正在启动Rserve服务......");
			try {
				Runtime rn = Runtime.getRuntime();
				/*
				 * 不建议写成直接写成rn.exec("R_EXE_PATH R__PATH")，
				 * 如果这样学的画前面定义的R_EXE_PATH，R__PATH会提示 * 这两个变量没有用到 *
				 * 运行也许会出错，提示错误如下： * java.io.IOException: Cannot run program
				 * "D:Program": CreateProcess error=2, 系统找不到指定的文件。
				 */
				String[] commandArgs = { R_EXE_PATH, R__PATH };
				rn.exec(commandArgs);
				Thread.sleep(5000);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			return getRConnection();
		}
	}
}
