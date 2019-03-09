EJERCICIO DE APLICACIÓN DE HOSPITAL CON JAVAFX
-----------------------------------------------

Este programa realizado con javaFX, simula una aplicación de hospital, en la que mediante la carga de ficheros .csv, leemos la información de los pacientes del hospital.

Funcionalidades pendientes de añadir:
				* Crear clase "cita". (Tiene Paciente, fecha y razón).
				* Añadir paciente a lista de espera. (Es un mapa de Cita, observación).
					* Cuando añades alguien a lista de espera, se crea un csv de esa lista de espera, con el nombre de WaitingList[nombre_CSV].
					* Se podrán borrar y editar lista de espera al hacer doble click. (Si se hace eso, editar fichero CSV de WaitingList).
				* Crear FXML de Lista de espera. (Se verá los pacientes en lista de espera -nombre, razon, y fecha-).
				* Añadir nuevo paciente al hospital. (Agregar al csv después).
				* Al iniciar el programa (MainController) si el fichero de configuración es correcto, y se carga un hospital, cargar tambien su             WaitingList.
				* Al hacer click en listas de pacientes, que quieres hacer (borrar, editar, añadir a lista de espera/quitarlo).
				* PieChart de Lista de espera (Cuantos hay en ella, y cuantos no).
