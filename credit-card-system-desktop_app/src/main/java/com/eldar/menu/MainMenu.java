package com.eldar.menu;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.eldar.menu.dto.CreditCardDto;
import com.eldar.menu.dto.PersonDto;
import com.eldar.menu.utils.ApiFormatterDate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainMenu {

	private static final String CARDHOLDER_API_URL = "http://localhost:8080/api/v1/card-holders/";
	private static final String CREDITCARD_API_URL = "http://localhost:8080/api/v1/credit-cards/";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("\n--- Menú Principal ---");
			System.out.println("1 - Crear una persona (Card-Holder)");
			System.out.println("2 - Registrar una tarjeta de crédito");
			System.out.println("3 - Retornar información de las tarjetas asociadas de un usuario por DNI");
			System.out.println("4 - Consultar las tasas de todas las marcas por fecha");
			System.out.println("5 - Salir");
			System.out.print("Seleccione una opción: ");

			int option;
			try {
				option = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Opción no válida. Por favor, ingrese un número entre 1 y 5.");
				continue;
			}

			switch (option) {
			case 1:
				createPerson(scanner);
				break;
			case 2:
				registerCreditCard(scanner);
				break;
			case 3:
				fetchCreditCardsByDni(scanner);
				break;
			case 4:
				consultRatesByDate(scanner);
				break;
			case 5:
				System.out.println("Saliendo del programa...");
				exit = true;
				break;
			default:
				System.out.println("Opción no válida. Por favor, seleccione un número entre 1 y 5.");
				break;
			}
		}

		scanner.close();
	}

	private static void createPerson(Scanner scanner) {
		System.out.println("\n--- Crear una Persona (Card-Holder) ---");
		System.out.print("Ingrese el nombre: ");
		String firstName = scanner.nextLine();
		System.out.print("Ingrese el apellido: ");
		String lastName = scanner.nextLine();
		System.out.print("Ingrese el DNI: ");
		String dni = scanner.nextLine();
		System.out.print("Ingrese la fecha de nacimiento (dd-MM-yyyy): ");
		String dateOfBirth = scanner.nextLine();
		System.out.print("Ingrese el email: ");
		String email = scanner.nextLine();
		String validDate = null;
		try {
			validDate = ApiFormatterDate.dateBirthApiFormatAndValidation(dateOfBirth);
			System.out.println("[fecha ya formateada ]" + validDate);
			PersonDto prsDto = new PersonDto((long) 0, firstName, lastName, dni, validDate, email);

			// [1]--Serialize
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(prsDto);

			// [2]--HttpClient
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CARDHOLDER_API_URL))
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			// [3]--HttpCall (POST)
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// [4]--Http Response
			System.out.println("-----Respuesta API------");
			System.out.println(response.statusCode());
			System.out.println(response.body());
			System.out.println("------------------------");
		} catch (IllegalArgumentException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error al registrar la persona: " + e.getMessage());
		}
	}

	private static void registerCreditCard(Scanner scanner) {
		System.out.println("\n--- Registrar una Tarjeta de Crédito ---");
		System.out.print("Ingrese la marca de la tarjeta (VISA, NARA, AMEX): ");
		String brand = scanner.nextLine();
		System.out.print("Ingrese el número de la tarjeta: ");
		String cardNumber = scanner.nextLine();
		System.out.print("Ingrese la fecha de vencimiento (dd-MM-yyyy): ");
		String expirationDate = scanner.nextLine();
		System.out.print("Ingrese el nombre del titular: ");
		String cardHolderName = scanner.nextLine();
		System.out.print("Ingrese el apellido del titular: ");
		String cardHolderLastName = scanner.nextLine();
		String validDate = null;
		try {
			validDate = ApiFormatterDate.expirationDateApiFormatAndValidation(expirationDate);

			// [0]--Call Get-Person (CardHolder)--------------
			PersonDto prsDto = MainMenu.callAndGetlCardHolderByFullName(cardHolderName, cardHolderLastName);
			// -----------------------------------------------
			if (prsDto != null) {
				// ----It is not neat, technical debt------
				Random random = new Random();
				int cvv = 100 + random.nextInt(900);
				String cvvStr = String.valueOf(cvv);
				// -----------------------------------------
				CreditCardDto crdCardDto = new CreditCardDto(brand, cardNumber, validDate, cardHolderName,
						cardHolderLastName, cvvStr, prsDto.getCrdHldId());

				// [1]--Mapp json
				ObjectMapper objectMapper = new ObjectMapper();
				String requestBody = objectMapper.writeValueAsString(crdCardDto);

				// [2]----Call to save CrediCard (POST)-------------------
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CREDITCARD_API_URL))
						.header("Content-Type", "application/json")
						.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
				// [3]--HttpCall
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				// ---------------------------------------------------------------

				// [4]--Http Response
				System.out.println("-----Respuesta API------");
				System.out.println(response.statusCode());
				System.out.println(response.body());
				System.out.println("------------------------");
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error al registrar la persona: " + e.getMessage());
		}
	}

	private static PersonDto callAndGetlCardHolderByFullName(String firstName, String lastName) {
		PersonDto personDto = null;
		String url = String.format("%s%s/%s", CARDHOLDER_API_URL, firstName, lastName);
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				System.out.println("---Titular encontrado---");
				System.out.println(response.body());
				ObjectMapper objectMapper = new ObjectMapper();
				personDto = objectMapper.readValue(response.body(), PersonDto.class);
			} else if (response.statusCode() == 404) {
				System.out.println("No se encontró un Titular con el nombre y apellido proporcionados.");
			} else {
				System.out.println("Error al realizar la solicitud. Código de respuesta: " + response.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Ocurrió un error al realizar la solicitud: " + e.getMessage());
		}
		return personDto;
	}

	public static List<CreditCardDto> fetchCreditCardsByDni(String dni) {
		try {
			String sufix = "by-dni/";
			String url = String.format("%s%s%s", CREDITCARD_API_URL, sufix,dni);
			HttpClient client = HttpClient.newHttpClient();			
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(response.body(), new TypeReference<List<CreditCardDto>>() {
				});
			} else if (response.statusCode() == 404) {
				System.out.println("No se encontraron tarjetas de crédito para el DNI proporcionado.");
			} else {
				System.out.println("Error al realizar la solicitud. Código de estado: " + response.statusCode());
			}
		} catch (Exception e) {
			System.out.println("Ocurrió un error al realizar la solicitud: " + e.getMessage());
		}
		return List.of();
	}

	private static void fetchCreditCardsByDni(Scanner scanner) {
		System.out.println("\n--- Consultar Tarjetas por DNI ---");
		System.out.print("Ingrese el DNI del usuario: ");
		String dni = scanner.nextLine();

		System.out.println("Buscando tarjetas asociadas al DNI: " + dni);
		System.out.println(fetchCreditCardsByDni(dni));
	}

	private static void consultRatesByDate(Scanner scanner) {
		System.out.println("\n--- Consultar Tasas por Fecha ---");
		System.out.print("Ingrese la fecha (dd-MM-yyyy) o presione Enter para usar la fecha actual: ");
		String date = scanner.nextLine();

		if (date.isEmpty()) {
			date = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		}

		System.out.println("Consultando tasas para la fecha: " + date);
		// Aquí se haría la llamada a la API en un paso posterior
	}
}
