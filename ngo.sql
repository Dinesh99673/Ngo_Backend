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

CREATE TABLE Profile(
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(100) NOT NULL,
    adhar_no VARCHAR(20) UNIQUE NOT NULL,
    profile_image VARCHAR(100),
    image_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Ngo(
    ngo_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    location_link VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    address VARCHAR(120),
    registered_by VARCHAR(100),
	adhar_no Varchar(20),
	founder_name VARCHAR(100) NOT NULL,
    founded_on DATE,
    category VARCHAR(60),
    website VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Event (
    event_id SERIAL PRIMARY KEY,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location_link VARCHAR(255),
    venue VARCHAR(150),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

<--not used in new structure
CREATE TABLE Donor(
    donor_id SERIAL PRIMARY KEY,
    type VARCHAR(10) CHECK (type IN ('NGO', 'User')) NOT NULL,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Transaction (
    transaction_id SERIAL PRIMARY KEY,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE CASCADE,
    recipient_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(10) CHECK (status IN ('Success', 'Failed')) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Event_Review(
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE SET NULL,
    event_id INTEGER REFERENCES Event(event_id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Ngo_Review(
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE SET NULL,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    reviewed_ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE About_Review(
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES Profile(user_id) ON DELETE SET NULL,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    rating DECIMAL(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Media (
    media_id SERIAL PRIMARY KEY,
    ngo_id INTEGER REFERENCES Ngo(ngo_id) ON DELETE CASCADE,
    event_id INTEGER REFERENCES Event(event_id) ON DELETE SET NULL,
    description VARCHAR(100),
    file_path VARCHAR(100) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);