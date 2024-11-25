--code not in use yet
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = NOW();
   RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';


CREATE TRIGGER update_events_updated_at
BEFORE UPDATE ON Event
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_users_updated_at
BEFORE UPDATE ON Profile
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_ngos_updated_at
BEFORE UPDATE ON Ngo
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
--Upper code not in use


CREATE TABLE Profile(
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(100) NOT NULL,
    adhar_no VARCHAR(20) UNIQUE NOT NULL,
    profile_image TEXT,
    image_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Ngo(
    ngo_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    location_link VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    address VARCHAR(120),
    registered_by VARCHAR(100),
	adhar_no Varchar(20),
	founder_name VARCHAR(100) NOT NULL,
    founded_on DATE,
    category VARCHAR(60),
    website TEXT,
    profile_path TEXT,
    profile_type VARCHAR(60),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Event (
    event_id SERIAL PRIMARY KEY,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location_link TEXT,
    venue VARCHAR(150),
    fees INTEGER,
    poster_path TEXT,
    poster_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Transaction (
    transaction_id SERIAL PRIMARY KEY,
    order_id VARCHAR(100),
    payment_id VARCHAR(100),
    signature VARCHAR(100),
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE CASCADE,
    recipient_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Event_Review(
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE SET NULL,
    event_id INTEGER REFERENCES Event(event_id) ON DELETE CASCADE,
    content TEXT ,
    rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Ngo_Review(
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE SET NULL,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    reviewed_ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    content TEXT,
    rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE About_Review(
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE SET NULL,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    content TEXT ,
    rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Event_Schedule(
    schedule_id SERIAL PRIMARY KEY,
    event_id INTEGER REFERENCES Event(event_id) ON DELETE CASCADE,
    event_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    UNIQUE (event_id, event_date)
);

CREATE TABLE Event_Participant (
    participant_id SERIAL PRIMARY KEY,
    event_id INTEGER REFERENCES Event(event_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE CASCADE,
    UNIQUE(event_id, user_id)
);

CREATE TABLE ngo_field(
    field_id SERIAL PRIMARY KEY,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    file_path TEXT NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    field_name TEXT,
    field_content TEXT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);