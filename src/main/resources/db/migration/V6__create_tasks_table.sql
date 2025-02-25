CREATE TABLE IF NOT EXISTS tasks(id SERIAL PRIMARY KEY,
                                title VARCHAR(200) NOT NULL,
                                description VARCHAR(300) NOT NULL,
                                due_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                board_id BIGINT REFERENCES boards (id) ON UPDATE RESTRICT ON DELETE CASCADE,
                                user_id BIGINT REFERENCES users (id) ON UPDATE RESTRICT ON DELETE SET NULL
);