package com.example.Challenge_LiterAlura;

import com.example.Challenge_LiterAlura.model.*;
import com.example.Challenge_LiterAlura.repository.AutorRepository;
import com.example.Challenge_LiterAlura.repository.IdiomaRepository;
import com.example.Challenge_LiterAlura.repository.LibroRepository;
import com.example.Challenge_LiterAlura.service.ConsumoAPI;
import com.example.Challenge_LiterAlura.service.ConvierteDatos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.*;

@SpringBootApplication
//@EntityScan(basePackages = "com.Challenge_Literatura.Literatura.model") // Ajusta el paquete según tu proyecto
//@EnableJpaRepositories(basePackages = "com.Challenge_Literatura.Literatura.repository") // Ajusta el paquete según tu proyecto

public class ChallengeLiterAluraApplication implements CommandLineRunner {
	private static final String URL_BASE = "https://gutendex.com/books/?search=";
	private ConsumoAPI consumoAPI = new ConsumoAPI();
	private ConvierteDatos conversor = new ConvierteDatos();
	private Scanner teclado = new Scanner(System.in);

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private IdiomaRepository idiomaRepository;


	public static void main(String[] args) {

		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Principal principal = new Principal();
		//principal.muestraElMenu(-1);
		//principal.buscarLibroPorTitulo();
		muestraElMenu(-1);
	}



	private void muestraElMenu(int opcionP) {

		var opcion = opcionP;
		while (opcion != 0) {
			var menu = """
					            Bienvenido 
					1 - Buscar libro por titulo
					2 - Listar libros registrados
					3 - Listar autores registrados
					4 - Listar autores vivos en un determinado año
					5 - Listar libros por idioma
					6 - Buscar autor registrado
					7 - Top 10 libros registrados más descargados
					
					0 - Salir
					
					Ingresa la opcion:
					""";
			System.out.println(menu);
			opcion = teclado.nextInt();
			teclado.nextLine();

			switch (opcion) {
				case 1:
					System.out.println("Ingrese el nombre del libro que desea buscar:");
					var tituloLibro = teclado.nextLine();
					buscarLibroPorTitulo(tituloLibro);
					break;
				case 2:
					listarLibrosRegistrados();
					break;
				case 3:
					listarAutoresRegistrados();
					break;
				case 4:
					listarAutoresVivosPorAnio();
					break;
				case 5:
					listarLibrosPorIdioma();
					break;
				case 6:
					buscarAutor();
					break;
				case 7:
					//top10DescargadosRegistrados();
					break;
				case 0:
					System.out.println("Cerrando la aplicación...");
					break;
				default:
					System.out.println("Opcion Invalida");
			}
		}
	}

	private void buscarAutor() {
		System.out.println("Ingrese el nombre del autor que desea buscar");
		String autorIngresado = teclado.nextLine().trim();
		autorRepository.findByNombre(autorIngresado).forEach(a ->
				System.out.println("Datos del Autor: " + a.getNombre() +
						" Fecha de nacimiento: " + a.getFechaNacimiento() +
						" Fecha de Fallecimiento" + a.getFechaFallecimiento()));
	}

	private void listarLibrosPorIdioma() {
		System.out.println("Ingrese el idioma para listar los libros:");
		String idiomaIngresado = teclado.nextLine().trim().toUpperCase();

		try {
			// Convertir la entrada del usuario al Enum correspondiente
			Idiomas idiomaEnum = Idiomas.valueOf(idiomaIngresado);

			// Filtrar libros por el idioma obtenido del Enum
			List<Libro> librosPorIdioma = libroRepository.findAll().stream()
					.filter(libro -> libro.getIdioma()!= null && Idiomas.valueOf(idiomaIngresado) == idiomaEnum)
					.toList();

			if (librosPorIdioma.isEmpty()) {
				System.out.println("No se encontraron libros en el idioma especificado.");
			} else {
				System.out.println("Libros encontrados en el idioma \"" + idiomaEnum + "\":");
				librosPorIdioma.forEach(libro -> System.out.println("- " + libro.getTitulo()));
			}
		} catch (IllegalArgumentException e) {
			System.out.println("El idioma ingresado no es válido. Por favor, ingrese un idioma válido.");
		}
	}

	private void listarAutoresVivosPorAnio() {
		System.out.println("Ingrese el año que desea buscar:");
		String anioIngresado = teclado.nextLine().trim();
		autorRepository.findAutoresByFechaAniosVividos(anioIngresado).forEach(f->
				System.out.println("Nombre Autor: "+f.getNombre()+
						"    Vivio entre los años: "+f.getFechaNacimiento()+
						" -"+f.getFechaFallecimiento()));

	}

	private void listarAutoresRegistrados() {
		autorRepository.findAll().forEach(autor -> System.out.println( "Autores registrados en la bse de datos:  "+
				autor.getNombre()));
	}

	private void listarLibrosRegistrados() {

		libroRepository.findAll().forEach(i-> System.out.println(
				"Titulo del libro: " +i.getTitulo() +
				"  Numero de Descargas: "+ i.getNumeroDescargas() +
				"  Autor: "+ i.getAutor().getNombre()+
				"  Idioma:"+ i.getIdioma()));
	}

	private Datos getDatosLibroAutorAPI(String tipo, String tituloLibro) {
		if (tipo == "libro") {
			//var tituloLibro = teclado.nextLine();
			var json = consumoAPI.obtenerDatos(ChallengeLiterAluraApplication.URL_BASE + tituloLibro.replace(" ", "+"));
			System.out.println("Json " + json);
			Datos datosLibros = conversor.obtenerDatos(json, Datos.class);
			return datosLibros;
		} else {
			var autor = teclado.nextLine();
			var json = consumoAPI.obtenerDatos(URL_BASE + autor.replace(" ", "+"));
			Datos datosLibros = conversor.obtenerDatos(json, Datos.class);
			return datosLibros;
		}

	}

	private void buscarLibroPorTitulo(String tituloLibro) {

		// Obtener datos de libros y autores desde la API
		Datos datos = getDatosLibroAutorAPI("libro", tituloLibro);

		// Buscar el libro en los datos obtenidos
		Optional<DatosLibros> libroBuscado = datos.resultados().stream()
				.filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
				.findFirst();

		// Si el libro existe en los datos obtenidos
		libroBuscado.ifPresentOrElse(libro -> {
			System.out.println("Libro Encontrado en API:  ");
			System.out.println(libro);
			//String autorLibro = getParseJson(libro.toString().replace("=", ":"));
			//System.out.println("getParseJson: " + autorLibro);

			//verificar si idioma
//			// Obtén el idioma identificado (primer idioma en la lista o null si está vacío)
//			String idiomaIdentificado = libroBuscado.get().idioma() != null && !libroBuscado.get().idioma().isEmpty()
//					? libroBuscado.get().idioma().get(0)
//					: null;

			// Verificar si el libro ya existe en la base de datos
			if (libroRepository.findByTitulo(libro.titulo()) != null &&
					!libroRepository.findByTitulo(libro.titulo()).isEmpty()) {
				System.out.println("El libro ya existe en la base de datos.");
			} else {
				System.out.println("El libro no encontrado en la base de datos.");
				Libro nuevoLibro = new Libro();
				nuevoLibro.setTitulo(libro.titulo());
				nuevoLibro.setNumeroDescargas(libro.numeroDeDescarga());


				Optional<DatosAutor> autorBuscado = libro.autor().stream().findFirst();
				autorBuscado.ifPresent(autorAPI -> {

					System.out.println("Idioma encontrado en la API");
					System.out.println("Autor Encontrado en API:  ");
					System.out.println(autorAPI);
					if ((autorRepository.findByNombre(autorAPI.nombre()) != null &&
							!autorRepository.findByNombre(autorAPI.nombre()).isEmpty())) {
						System.out.println("El autor ya existe en la base de datos.");
						Autor autorBD = autorRepository.findById(autorRepository.findIdByNombre(autorAPI.nombre())).get();
						//Autor autorBD = autorRepository.;
						nuevoLibro.setAutor(autorBD);
					} else {
					Autor nuevoAutor = new Autor();
					nuevoAutor.setNombre(autorAPI.nombre());
					nuevoAutor.setFechaFallecimiento(autorAPI.fechaDeFallecimiento());
					nuevoAutor.setFechaNacimiento(autorAPI.fechaDeNacimiento());
					autorRepository.save(nuevoAutor);
						System.out.println("El autor se crea en la base de datos.");
					nuevoLibro.setAutor(nuevoAutor);
					}

				});
				String idiomaLibro = libro.idioma().get(0);

				if (idiomaRepository.findByIdioma(idiomaLibro) != null &&
						!idiomaRepository.findByIdioma(idiomaLibro).isEmpty()) {
					System.out.println("El idioma ya existe en la base de datos.");
					//Idioma idiomaBD = idiomaRepository.findById(idiomaRepository.findByIdioma(libro.idioma().get(0)).toString()).get();
					//Autor autorBD = autorRepository.;
					//nuevoLibro.setIdioma(idiomaBD);
				} else {
					Idioma nuevoIdioma = new Idioma();
					nuevoIdioma.setIdioma(libro.idioma().get(0));
					idiomaRepository.save(nuevoIdioma);
					System.out.println("El idioma se crea en la base de datos.");
				}
				nuevoLibro.setIdioma(idiomaLibro);
				libroRepository.save(nuevoLibro);
				System.out.println("Libro guardado y relacionado con autor existente.");
			}
		}, () -> {
			// Si el libro no existe en los datos de la API
			System.out.println("Libro no encontrado.");
		});

	}

//	private Idioma findOrCreateIdioma(String idiomaNuevo) {
//		return idiomaRepository.findByIdioma(idiomaNuevo).orElseGet(() -> {
//			Idioma nuevoIdioma = new Idioma();
//			nuevoIdioma.setIdioma(idiomaNuevo);
//			return idiomaRepository.save(nuevoIdioma);
//		});
//	}

private List<Libro> getLibroAutorBD(String titulo) {
	return libroRepository.findByTitulo(titulo);
}



	private List<Autor> getAutorBD( String nombre)
	{
		return autorRepository.findByNombre(nombre);
	}

//	private String getParseJson(String jsonString){
//		String json = "...";
//		Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
//		String author0 = JsonPath.read(document, "$.authors[0].name");
//		return author0;
//	}

}
