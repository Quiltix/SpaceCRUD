
create table if not exists spacecraft (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    model VARCHAR(100),
    launch_date DATE,
    spacecraft_status varchar(100) NOT NULL,
    specifications text,
    current_location VARCHAR(100)
);


create table if not exists missions (
    id SERIAL PRIMARY KEY,
    spacecraft_id INT NOT NULL REFERENCES spacecraft(id),
    name VARCHAR(150) NOT NULL,
    start_date TIMESTAMPTZ NOT NULL,
    end_date TIMESTAMPTZ,
    mission_status VARCHAR(50) NOT NULL,
    objectives text
);


create table if not exists crew_members (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100),
    health_status VARCHAR(50),
    birth_date DATE
);


create table if not exists mission_crew (
    mission_id INT REFERENCES missions(id),
    member_id INT REFERENCES crew_members(id),
    role_in_mission VARCHAR(50),
    join_date TIMESTAMPTZ,
    PRIMARY KEY (mission_id, member_id)
);

-- 5. Эксперименты
create table if not exists experiments (
    id SERIAL PRIMARY KEY,
    mission_id INT NOT NULL REFERENCES missions(id),
    name VARCHAR(150) NOT NULL,
    description TEXT,
    experiment_status varchar(100),
    responsible_member_id INT REFERENCES crew_members(id),
    start_time TIMESTAMPTZ,
    end_time TIMESTAMPTZ,
    results text
);
create table if not exists resource_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);
-- 6. Ресурсы (Учет текущих запасов на аппарате)
create table if not exists resources (
    id SERIAL PRIMARY KEY,
    spacecraft_id INT NOT NULL REFERENCES spacecraft(id),
    resource_type_id INT REFERENCES resource_types(id),
    current_quantity DECIMAL(12, 3) NOT NULL,
    max_capacity DECIMAL(12, 3) NOT NULL,
    unit VARCHAR(20),
    last_updated TIMESTAMPTZ
);

create table if not exists resource_logs (
    id BIGSERIAL PRIMARY KEY,
    spacecraft_id INT NOT NULL REFERENCES spacecraft(id),
    resource_id INT REFERENCES resources(id),
    quantity_change DECIMAL(12, 3),
    timestamp TIMESTAMPTZ
);