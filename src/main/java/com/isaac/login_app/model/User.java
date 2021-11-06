package com.isaac.login_app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8024144104746499197L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank(message = "· El email es requerido")
	@Email(message = "· El email prorcionado es invalido")
	private String email;

	@Column(length = 64)
	@NotBlank(message = "· La contraseña es requerida")
	@Size(min = 7, message = "· La contraseña debe tener al menos 8 caracteres")
	private String password;

	@Column(length = 64)
	@NotBlank(message = "· Debe confirmar su contraseña")
	@Size(min = 7, message = "· La contraseña debe tener al menos 8 caracteres")
	private String confirmPassword;

	@NotBlank(message = "· El nombre es requerido")
	private String firstName;

	@NotBlank(message = "· Los apellidos son requeridos")
	private String lastName;

	// --- Constructor ---
	/**
	 * @param email
	 * @param password
	 * @param confirmPassword
	 * @param firstName
	 * @param lastName
	 */
	public User(String email, String password, String confirmPassword, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
