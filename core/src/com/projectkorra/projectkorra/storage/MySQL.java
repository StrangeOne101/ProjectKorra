package com.projectkorra.projectkorra.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQL extends Database {

	private String host = "localhost";
	private int port = 3306;
	private final String user;
	private String pass = "";
	private final String database;
	private String properties = "autoReconnect=true";

	public MySQL(final Logger log, final String host, final int port, final String user, final String pass, final String database, final String properties) {
		super(log, "[MySQL] ");
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.database = database;
		this.properties = properties;
	}

	public MySQL(final Logger log, final String user, final String pass, final String database) {
		super(log, "[MySQL] ");
		this.user = user;
		this.pass = pass;
		this.database = database;
	}

	@Override
	public Connection open() {
		try {
			this.log.info("Establishing MySQL Connection...");

			Class.forName("com.mysql.jdbc.Driver");

			if (this.properties != null && !this.properties.isEmpty()) {
				this.properties = "?" + this.properties;
			}

			final String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + this.properties;

			this.connection = DriverManager.getConnection(url, this.user, this.pass);
			this.printInfo("Connection established!");

			return this.connection;
		} catch (final ClassNotFoundException e) {
			this.printErr("JDBC driver not found!", true);
			return null;
		} catch (final SQLException e) {
			e.printStackTrace();
			this.printErr("MYSQL exception during connection.", true);
			return null;
		}
	}

}
