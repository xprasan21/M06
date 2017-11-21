import java.sql.*;

public class AccesoDatos {
	
	public static void main(String[] args) {
		AccesoDatos BBDD = new AccesoDatos();
		BBDD.abrirConexion();
		BBDD.obtenerDatos();
		//BBDD.insertarProyecto(4518);
		BBDD.borrarCli(10);
		//BBDD.mostrarDatos();
		//BBDD.modificarDatos();
		//BBDD.insertarDatos();
		//BBDD.borrar();
		BBDD.cerrarConexion();
	}

	Connection con;
	Statement st;
	ResultSet rs;
	int client_exist = 0;
	
	// Abrir la conexion
	public void abrirConexion() {
		try {
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost/basedas";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, userName, password);
			System.out.println("Conexión a la BD");
		} catch (Exception e) {
			System.out.println("Error en la conexión ");
		}
	}

	// Para cerrar la conexión una vez terminadas las consultas
	public void cerrarConexion() {
		try {
			con.close();
			System.out.println("Conexión cerrada.");
		} catch (SQLException e) {
			System.out.println("Error al cerrar conexión");
		}
	}

	// OBTENERDATOS
	public void obtenerDatos() {
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM empleats");
			System.out.println("Tabla abierta:");
		} catch (SQLException e) {
			System.out.println("Error al Abrir tabla ");
		}
	}

	// MOSTRAR DATOS
	public void mostrarDatos() {
		try {
			while (rs.next()) {
				String CodiEmpl = rs.getString("codi_empl");
				String NomEmpl = rs.getString("nom_empl");
				String CogEmpl = rs.getString("cognom_empl");
				String SouEmpl = rs.getString("sou");
				String NomDep = rs.getString("nom_dpt");
				String CiutatDep = rs.getString("ciutat_dpt");
				String NumProject = rs.getString("num_proj");

				System.out.println(CodiEmpl + ", " + NomEmpl + ", " + CogEmpl + "," + SouEmpl + "," + NomDep + ","
						+ CiutatDep + "," + NumProject);
			}
		} catch (Exception e) {
			System.out.println("Error al visualizar datos");
		}
	}

	public void modificarDatos() {
		try {
			st = con.createStatement();
			st.executeUpdate("Update empleats set sou=3000 where ciutat_dpt='BARCELONA'");
			System.out.println("Elemento modificado correctamente");
		} catch (SQLException e) {
			System.out.println("Error al modificar");
		}
	}

	public void insertarDatos() {
		try {
			st = con.createStatement();
			st.executeUpdate("INSERT INTO empleats (codi_empl,nom_empl,cognom_empl,sou,nom_dpt,ciutat_dpt)"
					+ " VALUES (15,'XAVI','PRATS',15000,'DIR','BARCELONA')");
			System.out.println("Elemento insertado");
		} catch (SQLException e) {
			System.out.println("Error al insertar ");
		}
	}

	public void borrar() {
		try {
			st = con.createStatement();
			st.executeUpdate("DELETE FROM empleats where sou >= 10000");
			System.out.println("Elemento Borrado");
		} catch (SQLException e) {
			System.out.println("Error al Borrar");
		}
	}

	public void insertarProyecto(int codi_Client) {
		try {
			validarCodiCli(codi_Client);
			
			if(client_exist == 1) {
				st = con.createStatement();
				st.executeUpdate("INSERT INTO projectes VALUES (28,'GESTAMP','75000','1997-04-08','1997-04-08','1997-04-08'," + codi_Client + ")");
				System.out.println("Elemento insertado");
			}
			else {
				System.out.println("Cliente inexistente");

			}
			
		} catch (SQLException e) {
			System.out.println("Error al insertarlo");
		}
		
	}
	
	public void validarCodiCli(int codi_Client) {
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clients WHERE codi_cli = " + codi_Client);
			String codiCliente = "";
				while (rs.next()) {
					codiCliente = rs.getString("codi_cli");
					System.out.println(codiCliente);
					}
				if (codiCliente == "") {
					client_exist = 0;
				}
				else {
					client_exist = 1;
				}
			}catch(SQLException e){
				
			}

	}
	
	public void borrarCli(int codi_Client) {
		try {
			st = con.createStatement();
			st.executeUpdate("DELETE FROM clients where codi_cli=" + codi_Client);
			st.executeUpdate("DELETE FROM projectes where codi_client=" + codi_Client);
			System.out.println("Borrado");
			
		} catch (SQLException e) {
			
			System.out.println("Error");
		}
	}
}
