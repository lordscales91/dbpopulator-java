package io.github.lordscales91.dbpopulator.model;

import io.github.lordscales91.dbpopulator.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Class that describes the structure of a Database (the tables,
 * the schema and connection parameters)
 * @author Lordscales91
 */
public class DatabaseDescriptor {
	
	public static class DbTypes {
		public static final String MYSQL = "mysql";
	}

	private String dbuser;
	private String dbpassword;
	private String dbhost;
	private String dbschema;
	private String dbtype; // Used to determine the right handler (MySQL, SQLite, ...)
	@JsonIgnore // Handle the table list manually
	private List<TableDescriptor> tables;
	
	private DatabaseDescriptor() {}
	
	public static DatabaseDescriptor load() {
		return load("dbdescriptor.json");
	}

	public static DatabaseDescriptor load(String filename) {
		return load(filename, "json");
	}

	public static DatabaseDescriptor load(String filename, String format) {
		if("json".equals(format)) {
			return loadFromJson(filename);
		}
		return null;
	}

	public static DatabaseDescriptor loadFromJson(String filename) {
		DatabaseDescriptor db = null;
		ObjectMapper mp = new ObjectMapper();
		try {
			JsonNode root = mp.readTree(new File(filename));
			db = mp.treeToValue(root, DatabaseDescriptor.class);
			// now handle the table list
			if(root.has("tables")) {
				db.tables = new ArrayList<TableDescriptor>();
				ArrayNode tblArr = (ArrayNode) root.get("tables");
				Iterator<JsonNode> it = tblArr.iterator();
				while(it.hasNext()) {
					TableDescriptor tbl = TableDescriptor.fromJson(it.next());
					db.tables.add(tbl);
				}
			}
		} catch (JsonProcessingException e) {
			// Ideally exceptions should be wrapped within our own and re-thrown
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return db;
	}

	public String getDbuser() {
		return dbuser;
	}

	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getDbhost() {
		return dbhost;
	}

	public void setDbhost(String dbhost) {
		this.dbhost = dbhost;
	}

	public String getDbschema() {
		return dbschema;
	}

	public void setDbschema(String dbschema) {
		this.dbschema = dbschema;
	}

	public String getDbtype() {
		return dbtype;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public List<TableDescriptor> getTables() {
		return tables;
	}

	public void setTables(List<TableDescriptor> tables) {
		this.tables = tables;
	}
	
	@Override
	public String toString() {
		return String.format("<%s user: %s, host: %s, schema: %s, type: %s, tables: %s>",
				getClass().getSimpleName(), dbuser,
				dbhost, dbschema, dbtype, StringUtils.listToString(tables));
	}
	
}
