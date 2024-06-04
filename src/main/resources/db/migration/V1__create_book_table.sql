-- Enable the uuid-ossp extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the enum type for genre
CREATE TYPE book_genre AS ENUM ('FICTION', 'NONFICTION', 'MYSTERY', 'FANTASY', 'SCIENCE_FICTION', 'BIOGRAPHY');

-- Create the enum type for ageGroup
CREATE TYPE age_group AS ENUM ('CHILD', 'TEEN', 'ADULT', 'SENIOR');

-- Create the enum type for language
CREATE TYPE book_language AS ENUM ('ENGLISH', 'SPANISH', 'FRENCH', 'GERMAN', 'CHINESE');

-- Create the Book table
CREATE TABLE Book (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    genre book_genre,
    ageGroup age_group,
    price DECIMAL(10, 2),
    publicationDate DATE,
    author VARCHAR(255),
    numberOfPages INT,
    characteristics TEXT,
    description TEXT,
    language book_language
);
