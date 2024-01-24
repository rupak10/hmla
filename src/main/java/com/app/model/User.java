package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long userId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "sur_name")
	private String surname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "status")
	private int status;
}
