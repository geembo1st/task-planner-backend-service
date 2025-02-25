CREATE TABLE IF NOT EXISTS boards(id SERIAL PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
                                    description VARCHAR(255),
                                    created_by BIGINT REFERENCES users (id) ON UPDATE RESTRICT ON DELETE SET NULL,
                                    created_at TIMESTAMP
);