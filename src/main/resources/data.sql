INSERT INTO users(username, password, role)
		VALUES('admin', 'admin', 'ADMIN')
        ON DUPLICATE KEY UPDATE role = 'ADMIN';