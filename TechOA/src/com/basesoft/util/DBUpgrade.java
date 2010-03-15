package com.basesoft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class DBUpgrade {
	private PlatformTransactionManager transactionManager;
	private JdbcTemplate jdbcTemplate;
	private String tablename = "db_version";

	public void upgrade(final String scriptDir) {
		final String[] scripts = new File(scriptDir).list();
		Arrays.sort(scripts);

		final int dbVersion = getDBVersion(jdbcTemplate);
		TransactionTemplate tt = new TransactionTemplate(transactionManager);
		tt.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				for (String script : scripts) {
					int version = Integer.parseInt(script.substring(0, 3));
					if (version > dbVersion)
						try {
							System.out.println("正在执行脚本：" + script);
							executeSQLScript(new FileInputStream(new File(scriptDir + script)));
							executeSQLScript("UPDATE " + tablename + " SET version=" + version);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
				}
			}
		});
	}

	public int getDBVersion(JdbcTemplate jdbcTemplate) {
		int dbVersion = 0;
		try {
			dbVersion = jdbcTemplate.queryForInt("SELECT max(version) FROM " + tablename);
		} catch (Exception e) {
			jdbcTemplate.execute("create table " + tablename + " (version int NOT NULL)");
			jdbcTemplate.execute("insert into " + tablename + " values(0)");
		}
		return dbVersion;
	}

	private void executeSQLScript(InputStream resource) throws IOException,
			SQLException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
			boolean done = false;
			while (!done) {
				StringBuilder command = new StringBuilder();
				while (true) {
					String line = in.readLine();
					if (line == null) {
						done = true;
						break;
					}
					// Ignore comments and blank lines.
					if (isSQLCommandPart(line)) {
						command.append(" ").append(line.trim());
					}
					if (line.trim().endsWith(";")) {
						break;
					}
				}
				if (!done && !command.toString().equals("")) {
					// Remove last semicolon when using Oracle or DB2 to prevent
					// "invalid character error"
					// if (DbConnectionManager.getDatabaseType() ==
					// DbConnectionManager.DatabaseType.oracle
					// || DbConnectionManager.getDatabaseType() ==
					// DbConnectionManager.DatabaseType.db2) {
					// command.deleteCharAt(command.length() - 1);
					// }
					command.deleteCharAt(command.length() - 1);
					jdbcTemplate.execute(command.toString());
				}
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean isSQLCommandPart(String line) {
		line = line.trim();
		if (line.equals("")) {
			return false;
		}
		// Check to see if the line is a comment. Valid comment types:
		// "//" is HSQLDB
		// "--" is DB2 and Postgres
		// "#" is MySQL
		// "REM" is Oracle
		// "/*" is SQLServer
		return !(line.startsWith("//") || line.startsWith("--") || line.startsWith("#") || line.startsWith("REM ")
				|| line.startsWith("/*") || line.startsWith("*"));
	}

	private void executeSQLScript(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
