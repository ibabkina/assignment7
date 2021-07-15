INSERT INTO users(user_id, username, password, role)
		VALUES('0', 'admin', 'admin', 'ADMIN')
        ON DUPLICATE KEY UPDATE role = 'ADMIN';