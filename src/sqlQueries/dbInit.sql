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

    INSERT INTO students (last_name, first_name, middle_name, phone, telegram, email, github) VALUES
('Иванов', 'Иван', 'Иванович', '+1234567890', '@ivanov', 'ivanov@example.com', 'ivanov'),
('Петров', 'Петр', 'Петрович', '+0987654321', '@petrov', 'petrov@example.com', 'petrov'),
('Сидоров', 'Сидр', 'Сидорович', '+1122334455', '@sidorov', 'sidorov@example.com', 'sidorov'),
('Смирнов', 'Сергей', 'Сергеевич', '+2233445566', '@smirnov', 'smirnov@example.com', 'smirnov'),
('Кузнецов', 'Константин', 'Константинович', '+3344556677', '@kuznetsov', 'kuznetsov@example.com', 'kuznetsov');