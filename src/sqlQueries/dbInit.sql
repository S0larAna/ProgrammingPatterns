CREATE TABLE IF NOT EXISTS students (
                                        id INTEGER PRIMARY KEY,
                                        last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    telegram VARCHAR(255),
    email VARCHAR(255),
    github VARCHAR(255)
    )