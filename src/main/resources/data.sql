INSERT INTO users(id, username, password, active, role)
		VALUES('0', 'admin', 'admin', TRUE, 'ADMIN')
        ON DUPLICATE KEY UPDATE role = 'ADMIN';