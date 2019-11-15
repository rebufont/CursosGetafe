package com.getafe.curso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GestorBD
{
	
	static final String CONNECTION_STRING = "jdbc:mysql://localhost/EMP";

	static final String USER = "root";
	static final String PASS = "adminadmin";

	
	public static Connection getConnection()
	{
		try {
			// En entorno web (Tomcat), es necesario iniciarlizar la clase del driver
			Class.forName("com.mysql.jdbc.Driver");

			// Conectar a la BD
			System.out.println("Connecting to database...");
			Connection cnn = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
			
			return cnn;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static boolean hacerLogin(String nombre, String password)
	{

		Connection cnn = null;
		Statement stmt = null;

		try {
			
			cnn = getConnection();
		
			// Crear la SELECT
			PreparedStatement ps = cnn.prepareStatement("select password from usuarios where usuario = ?");
			ps.setString(1, nombre);
			
			// Ejecutar SELECT
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next())
			{
				System.out.println("No existe el usuario: " + nombre);
				//throw new Exception("No existe el usuario: " + nombre);
				return false;
			}
					
			// Verificar password
			if (! rs.getString("password").equals(password))
			{
				System.out.println("Password incorrecta");
				//throw new Exception("Password incorrecta");
				return false;
			}
		
			// Devolver resultado
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	

}